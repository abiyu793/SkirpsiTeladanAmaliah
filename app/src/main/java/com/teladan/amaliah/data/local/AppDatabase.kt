package com.teladan.amaliah.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.teladan.amaliah.data.local.dao.AdminDao
import com.teladan.amaliah.data.local.dao.MatrixDao
import com.teladan.amaliah.data.local.dao.SiswaDao
import com.teladan.amaliah.data.local.entity.Admin
import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity
import com.teladan.amaliah.data.local.entity.SiswaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Admin::class, SiswaEntity::class, KriteriaMatrixEntity::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun adminDao(): AdminDao
    abstract fun siswaDao(): SiswaDao
    abstract fun matrixDao(): MatrixDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "spk_database"
                )
                    .addMigrations(MIGRATION_4_5, MIGRATION_5_6)
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val adminDao = database.adminDao()
                        adminDao.insertAdmin(
                            Admin(
                                username = "admin",
                                password = "123",
                                nama_lengkap = "Administrator"
                            )
                        )
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Migrate old jurusan names to new nomenclature automatically
                db.execSQL("UPDATE siswa_table SET jurusan = 'LPFKK' WHERE jurusan = 'Farmasi'")
                db.execSQL("UPDATE siswa_table SET jurusan = 'LPKPC' WHERE jurusan = 'Keperawatan'")
                db.execSQL("UPDATE siswa_table SET jurusan = 'LPLM' WHERE jurusan = 'TLM'")
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE siswa_table ADD COLUMN skor_akhir REAL NOT NULL DEFAULT 0.0")
            }
        }

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE siswa_table ADD COLUMN is_dirty INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE siswa_table ADD COLUMN is_deleted INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}