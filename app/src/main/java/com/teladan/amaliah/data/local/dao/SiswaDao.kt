package com.teladan.amaliah.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.teladan.amaliah.data.local.entity.SiswaEntity

@Dao
interface SiswaDao {

    // GET ALL SISWA
    @Query("SELECT * FROM siswa_table ORDER BY nama ASC")
    suspend fun getAllSiswa(): List<SiswaEntity>

    // GET SISWA BY ID (untuk edit)
    @Query("SELECT * FROM siswa_table WHERE id = :id LIMIT 1")
    suspend fun getSiswaById(id: Int): SiswaEntity?

    // INSERT SISWA (kembalikan ID)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSiswa(siswa: SiswaEntity): Long

    // UPDATE SISWA
    @Update
    suspend fun updateSiswa(siswa: SiswaEntity)

    // DELETE SISWA (opsional)
    @Query("DELETE FROM siswa_table WHERE id = :id")
    suspend fun deleteSiswa(id: Int)

    // LIVE DATA untuk Real-time Dashboard (jumlah siswa)
    @Query("SELECT COUNT(*) FROM siswa_table")
    fun getCountSiswa(): LiveData<Int>

    @Query("SELECT * FROM siswa_table ORDER BY nama ASC")
    fun getAllSiswaLive(): LiveData<List<SiswaEntity>>

    // FILTER berdasarkan jurusan dan kelas (untuk ranking)
    @Query("SELECT * FROM siswa_table WHERE jurusan = :jurusan AND tingkat_kelas = :kelas ORDER BY nama ASC")
    suspend fun getSiswaByJurusanAndKelas(jurusan: String, kelas: String): List<SiswaEntity>

    // GET TOTAL SISWA (tanpa LiveData, untuk keperluan tertentu)
    @Query("SELECT COUNT(*) FROM siswa_table")
    suspend fun getTotalSiswa(): Int
}