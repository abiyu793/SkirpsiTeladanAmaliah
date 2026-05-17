package com.teladan.amaliah.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.teladan.amaliah.data.local.dao.AdminDao;
import com.teladan.amaliah.data.local.dao.AdminDao_Impl;
import com.teladan.amaliah.data.local.dao.MatrixDao;
import com.teladan.amaliah.data.local.dao.MatrixDao_Impl;
import com.teladan.amaliah.data.local.dao.SiswaDao;
import com.teladan.amaliah.data.local.dao.SiswaDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AdminDao _adminDao;

  private volatile SiswaDao _siswaDao;

  private volatile MatrixDao _matrixDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `admin_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `password` TEXT NOT NULL, `nama_lengkap` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `siswa_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nis` TEXT NOT NULL, `nama` TEXT NOT NULL, `jurusan` TEXT NOT NULL, `tingkat_kelas` TEXT NOT NULL, `tahun_ajaran` TEXT NOT NULL, `nilai_rapor` REAL NOT NULL, `nilai_teori` REAL NOT NULL, `nilai_lab` REAL NOT NULL, `nilai_pkl` REAL NOT NULL, `persentase_hadir` REAL NOT NULL, `jam_terlambat` REAL NOT NULL, `poin_pelanggaran` REAL NOT NULL, `skor_sikap` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `kriteria_matrix` (`id` INTEGER NOT NULL, `praktik_vs_akademik` REAL NOT NULL, `disiplin_vs_akademik` REAL NOT NULL, `praktik_vs_hadir` REAL NOT NULL, `akademik_vs_hadir` REAL NOT NULL, `praktik_vs_disiplin` REAL NOT NULL, `hadir_vs_disiplin` REAL NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '799f5a43100a7ae7c869a0d34c25087d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `admin_table`");
        db.execSQL("DROP TABLE IF EXISTS `siswa_table`");
        db.execSQL("DROP TABLE IF EXISTS `kriteria_matrix`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsAdminTable = new HashMap<String, TableInfo.Column>(4);
        _columnsAdminTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminTable.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminTable.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminTable.put("nama_lengkap", new TableInfo.Column("nama_lengkap", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAdminTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAdminTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAdminTable = new TableInfo("admin_table", _columnsAdminTable, _foreignKeysAdminTable, _indicesAdminTable);
        final TableInfo _existingAdminTable = TableInfo.read(db, "admin_table");
        if (!_infoAdminTable.equals(_existingAdminTable)) {
          return new RoomOpenHelper.ValidationResult(false, "admin_table(com.teladan.amaliah.data.local.entity.Admin).\n"
                  + " Expected:\n" + _infoAdminTable + "\n"
                  + " Found:\n" + _existingAdminTable);
        }
        final HashMap<String, TableInfo.Column> _columnsSiswaTable = new HashMap<String, TableInfo.Column>(14);
        _columnsSiswaTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("nis", new TableInfo.Column("nis", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("nama", new TableInfo.Column("nama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("jurusan", new TableInfo.Column("jurusan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("tingkat_kelas", new TableInfo.Column("tingkat_kelas", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("tahun_ajaran", new TableInfo.Column("tahun_ajaran", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("nilai_rapor", new TableInfo.Column("nilai_rapor", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("nilai_teori", new TableInfo.Column("nilai_teori", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("nilai_lab", new TableInfo.Column("nilai_lab", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("nilai_pkl", new TableInfo.Column("nilai_pkl", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("persentase_hadir", new TableInfo.Column("persentase_hadir", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("jam_terlambat", new TableInfo.Column("jam_terlambat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("poin_pelanggaran", new TableInfo.Column("poin_pelanggaran", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSiswaTable.put("skor_sikap", new TableInfo.Column("skor_sikap", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSiswaTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSiswaTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSiswaTable = new TableInfo("siswa_table", _columnsSiswaTable, _foreignKeysSiswaTable, _indicesSiswaTable);
        final TableInfo _existingSiswaTable = TableInfo.read(db, "siswa_table");
        if (!_infoSiswaTable.equals(_existingSiswaTable)) {
          return new RoomOpenHelper.ValidationResult(false, "siswa_table(com.teladan.amaliah.data.local.entity.SiswaEntity).\n"
                  + " Expected:\n" + _infoSiswaTable + "\n"
                  + " Found:\n" + _existingSiswaTable);
        }
        final HashMap<String, TableInfo.Column> _columnsKriteriaMatrix = new HashMap<String, TableInfo.Column>(7);
        _columnsKriteriaMatrix.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKriteriaMatrix.put("praktik_vs_akademik", new TableInfo.Column("praktik_vs_akademik", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKriteriaMatrix.put("disiplin_vs_akademik", new TableInfo.Column("disiplin_vs_akademik", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKriteriaMatrix.put("praktik_vs_hadir", new TableInfo.Column("praktik_vs_hadir", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKriteriaMatrix.put("akademik_vs_hadir", new TableInfo.Column("akademik_vs_hadir", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKriteriaMatrix.put("praktik_vs_disiplin", new TableInfo.Column("praktik_vs_disiplin", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKriteriaMatrix.put("hadir_vs_disiplin", new TableInfo.Column("hadir_vs_disiplin", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysKriteriaMatrix = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesKriteriaMatrix = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoKriteriaMatrix = new TableInfo("kriteria_matrix", _columnsKriteriaMatrix, _foreignKeysKriteriaMatrix, _indicesKriteriaMatrix);
        final TableInfo _existingKriteriaMatrix = TableInfo.read(db, "kriteria_matrix");
        if (!_infoKriteriaMatrix.equals(_existingKriteriaMatrix)) {
          return new RoomOpenHelper.ValidationResult(false, "kriteria_matrix(com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity).\n"
                  + " Expected:\n" + _infoKriteriaMatrix + "\n"
                  + " Found:\n" + _existingKriteriaMatrix);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "799f5a43100a7ae7c869a0d34c25087d", "dbc90bede3d0afa899d03188c5c50fa3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "admin_table","siswa_table","kriteria_matrix");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `admin_table`");
      _db.execSQL("DELETE FROM `siswa_table`");
      _db.execSQL("DELETE FROM `kriteria_matrix`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AdminDao.class, AdminDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SiswaDao.class, SiswaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MatrixDao.class, MatrixDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AdminDao adminDao() {
    if (_adminDao != null) {
      return _adminDao;
    } else {
      synchronized(this) {
        if(_adminDao == null) {
          _adminDao = new AdminDao_Impl(this);
        }
        return _adminDao;
      }
    }
  }

  @Override
  public SiswaDao siswaDao() {
    if (_siswaDao != null) {
      return _siswaDao;
    } else {
      synchronized(this) {
        if(_siswaDao == null) {
          _siswaDao = new SiswaDao_Impl(this);
        }
        return _siswaDao;
      }
    }
  }

  @Override
  public MatrixDao matrixDao() {
    if (_matrixDao != null) {
      return _matrixDao;
    } else {
      synchronized(this) {
        if(_matrixDao == null) {
          _matrixDao = new MatrixDao_Impl(this);
        }
        return _matrixDao;
      }
    }
  }
}
