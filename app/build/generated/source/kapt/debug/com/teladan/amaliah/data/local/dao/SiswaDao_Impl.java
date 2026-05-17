package com.teladan.amaliah.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.teladan.amaliah.data.local.entity.SiswaEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SiswaDao_Impl implements SiswaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SiswaEntity> __insertionAdapterOfSiswaEntity;

  private final EntityDeletionOrUpdateAdapter<SiswaEntity> __updateAdapterOfSiswaEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSiswa;

  public SiswaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSiswaEntity = new EntityInsertionAdapter<SiswaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `siswa_table` (`id`,`nis`,`nama`,`jurusan`,`tingkat_kelas`,`tahun_ajaran`,`nilai_rapor`,`nilai_teori`,`nilai_lab`,`nilai_pkl`,`persentase_hadir`,`jam_terlambat`,`poin_pelanggaran`,`skor_sikap`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SiswaEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNis() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNis());
        }
        if (entity.getNama() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNama());
        }
        if (entity.getJurusan() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getJurusan());
        }
        if (entity.getTingkat_kelas() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTingkat_kelas());
        }
        if (entity.getTahun_ajaran() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTahun_ajaran());
        }
        statement.bindDouble(7, entity.getNilai_rapor());
        statement.bindDouble(8, entity.getNilai_teori());
        statement.bindDouble(9, entity.getNilai_lab());
        statement.bindDouble(10, entity.getNilai_pkl());
        statement.bindDouble(11, entity.getPersentase_hadir());
        statement.bindDouble(12, entity.getJam_terlambat());
        statement.bindDouble(13, entity.getPoin_pelanggaran());
        statement.bindDouble(14, entity.getSkor_sikap());
      }
    };
    this.__updateAdapterOfSiswaEntity = new EntityDeletionOrUpdateAdapter<SiswaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `siswa_table` SET `id` = ?,`nis` = ?,`nama` = ?,`jurusan` = ?,`tingkat_kelas` = ?,`tahun_ajaran` = ?,`nilai_rapor` = ?,`nilai_teori` = ?,`nilai_lab` = ?,`nilai_pkl` = ?,`persentase_hadir` = ?,`jam_terlambat` = ?,`poin_pelanggaran` = ?,`skor_sikap` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SiswaEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNis() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNis());
        }
        if (entity.getNama() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNama());
        }
        if (entity.getJurusan() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getJurusan());
        }
        if (entity.getTingkat_kelas() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTingkat_kelas());
        }
        if (entity.getTahun_ajaran() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTahun_ajaran());
        }
        statement.bindDouble(7, entity.getNilai_rapor());
        statement.bindDouble(8, entity.getNilai_teori());
        statement.bindDouble(9, entity.getNilai_lab());
        statement.bindDouble(10, entity.getNilai_pkl());
        statement.bindDouble(11, entity.getPersentase_hadir());
        statement.bindDouble(12, entity.getJam_terlambat());
        statement.bindDouble(13, entity.getPoin_pelanggaran());
        statement.bindDouble(14, entity.getSkor_sikap());
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteSiswa = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM siswa_table WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSiswa(final SiswaEntity siswa, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSiswaEntity.insertAndReturnId(siswa);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSiswa(final SiswaEntity siswa, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSiswaEntity.handle(siswa);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSiswa(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSiswa.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteSiswa.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllSiswa(final Continuation<? super List<SiswaEntity>> $completion) {
    final String _sql = "SELECT * FROM siswa_table ORDER BY nama ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SiswaEntity>>() {
      @Override
      @NonNull
      public List<SiswaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNis = CursorUtil.getColumnIndexOrThrow(_cursor, "nis");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfJurusan = CursorUtil.getColumnIndexOrThrow(_cursor, "jurusan");
          final int _cursorIndexOfTingkatKelas = CursorUtil.getColumnIndexOrThrow(_cursor, "tingkat_kelas");
          final int _cursorIndexOfTahunAjaran = CursorUtil.getColumnIndexOrThrow(_cursor, "tahun_ajaran");
          final int _cursorIndexOfNilaiRapor = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_rapor");
          final int _cursorIndexOfNilaiTeori = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_teori");
          final int _cursorIndexOfNilaiLab = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_lab");
          final int _cursorIndexOfNilaiPkl = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_pkl");
          final int _cursorIndexOfPersentaseHadir = CursorUtil.getColumnIndexOrThrow(_cursor, "persentase_hadir");
          final int _cursorIndexOfJamTerlambat = CursorUtil.getColumnIndexOrThrow(_cursor, "jam_terlambat");
          final int _cursorIndexOfPoinPelanggaran = CursorUtil.getColumnIndexOrThrow(_cursor, "poin_pelanggaran");
          final int _cursorIndexOfSkorSikap = CursorUtil.getColumnIndexOrThrow(_cursor, "skor_sikap");
          final List<SiswaEntity> _result = new ArrayList<SiswaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SiswaEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNis;
            if (_cursor.isNull(_cursorIndexOfNis)) {
              _tmpNis = null;
            } else {
              _tmpNis = _cursor.getString(_cursorIndexOfNis);
            }
            final String _tmpNama;
            if (_cursor.isNull(_cursorIndexOfNama)) {
              _tmpNama = null;
            } else {
              _tmpNama = _cursor.getString(_cursorIndexOfNama);
            }
            final String _tmpJurusan;
            if (_cursor.isNull(_cursorIndexOfJurusan)) {
              _tmpJurusan = null;
            } else {
              _tmpJurusan = _cursor.getString(_cursorIndexOfJurusan);
            }
            final String _tmpTingkat_kelas;
            if (_cursor.isNull(_cursorIndexOfTingkatKelas)) {
              _tmpTingkat_kelas = null;
            } else {
              _tmpTingkat_kelas = _cursor.getString(_cursorIndexOfTingkatKelas);
            }
            final String _tmpTahun_ajaran;
            if (_cursor.isNull(_cursorIndexOfTahunAjaran)) {
              _tmpTahun_ajaran = null;
            } else {
              _tmpTahun_ajaran = _cursor.getString(_cursorIndexOfTahunAjaran);
            }
            final double _tmpNilai_rapor;
            _tmpNilai_rapor = _cursor.getDouble(_cursorIndexOfNilaiRapor);
            final double _tmpNilai_teori;
            _tmpNilai_teori = _cursor.getDouble(_cursorIndexOfNilaiTeori);
            final double _tmpNilai_lab;
            _tmpNilai_lab = _cursor.getDouble(_cursorIndexOfNilaiLab);
            final double _tmpNilai_pkl;
            _tmpNilai_pkl = _cursor.getDouble(_cursorIndexOfNilaiPkl);
            final double _tmpPersentase_hadir;
            _tmpPersentase_hadir = _cursor.getDouble(_cursorIndexOfPersentaseHadir);
            final double _tmpJam_terlambat;
            _tmpJam_terlambat = _cursor.getDouble(_cursorIndexOfJamTerlambat);
            final double _tmpPoin_pelanggaran;
            _tmpPoin_pelanggaran = _cursor.getDouble(_cursorIndexOfPoinPelanggaran);
            final double _tmpSkor_sikap;
            _tmpSkor_sikap = _cursor.getDouble(_cursorIndexOfSkorSikap);
            _item = new SiswaEntity(_tmpId,_tmpNis,_tmpNama,_tmpJurusan,_tmpTingkat_kelas,_tmpTahun_ajaran,_tmpNilai_rapor,_tmpNilai_teori,_tmpNilai_lab,_tmpNilai_pkl,_tmpPersentase_hadir,_tmpJam_terlambat,_tmpPoin_pelanggaran,_tmpSkor_sikap);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSiswaById(final int id, final Continuation<? super SiswaEntity> $completion) {
    final String _sql = "SELECT * FROM siswa_table WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SiswaEntity>() {
      @Override
      @Nullable
      public SiswaEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNis = CursorUtil.getColumnIndexOrThrow(_cursor, "nis");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfJurusan = CursorUtil.getColumnIndexOrThrow(_cursor, "jurusan");
          final int _cursorIndexOfTingkatKelas = CursorUtil.getColumnIndexOrThrow(_cursor, "tingkat_kelas");
          final int _cursorIndexOfTahunAjaran = CursorUtil.getColumnIndexOrThrow(_cursor, "tahun_ajaran");
          final int _cursorIndexOfNilaiRapor = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_rapor");
          final int _cursorIndexOfNilaiTeori = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_teori");
          final int _cursorIndexOfNilaiLab = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_lab");
          final int _cursorIndexOfNilaiPkl = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_pkl");
          final int _cursorIndexOfPersentaseHadir = CursorUtil.getColumnIndexOrThrow(_cursor, "persentase_hadir");
          final int _cursorIndexOfJamTerlambat = CursorUtil.getColumnIndexOrThrow(_cursor, "jam_terlambat");
          final int _cursorIndexOfPoinPelanggaran = CursorUtil.getColumnIndexOrThrow(_cursor, "poin_pelanggaran");
          final int _cursorIndexOfSkorSikap = CursorUtil.getColumnIndexOrThrow(_cursor, "skor_sikap");
          final SiswaEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNis;
            if (_cursor.isNull(_cursorIndexOfNis)) {
              _tmpNis = null;
            } else {
              _tmpNis = _cursor.getString(_cursorIndexOfNis);
            }
            final String _tmpNama;
            if (_cursor.isNull(_cursorIndexOfNama)) {
              _tmpNama = null;
            } else {
              _tmpNama = _cursor.getString(_cursorIndexOfNama);
            }
            final String _tmpJurusan;
            if (_cursor.isNull(_cursorIndexOfJurusan)) {
              _tmpJurusan = null;
            } else {
              _tmpJurusan = _cursor.getString(_cursorIndexOfJurusan);
            }
            final String _tmpTingkat_kelas;
            if (_cursor.isNull(_cursorIndexOfTingkatKelas)) {
              _tmpTingkat_kelas = null;
            } else {
              _tmpTingkat_kelas = _cursor.getString(_cursorIndexOfTingkatKelas);
            }
            final String _tmpTahun_ajaran;
            if (_cursor.isNull(_cursorIndexOfTahunAjaran)) {
              _tmpTahun_ajaran = null;
            } else {
              _tmpTahun_ajaran = _cursor.getString(_cursorIndexOfTahunAjaran);
            }
            final double _tmpNilai_rapor;
            _tmpNilai_rapor = _cursor.getDouble(_cursorIndexOfNilaiRapor);
            final double _tmpNilai_teori;
            _tmpNilai_teori = _cursor.getDouble(_cursorIndexOfNilaiTeori);
            final double _tmpNilai_lab;
            _tmpNilai_lab = _cursor.getDouble(_cursorIndexOfNilaiLab);
            final double _tmpNilai_pkl;
            _tmpNilai_pkl = _cursor.getDouble(_cursorIndexOfNilaiPkl);
            final double _tmpPersentase_hadir;
            _tmpPersentase_hadir = _cursor.getDouble(_cursorIndexOfPersentaseHadir);
            final double _tmpJam_terlambat;
            _tmpJam_terlambat = _cursor.getDouble(_cursorIndexOfJamTerlambat);
            final double _tmpPoin_pelanggaran;
            _tmpPoin_pelanggaran = _cursor.getDouble(_cursorIndexOfPoinPelanggaran);
            final double _tmpSkor_sikap;
            _tmpSkor_sikap = _cursor.getDouble(_cursorIndexOfSkorSikap);
            _result = new SiswaEntity(_tmpId,_tmpNis,_tmpNama,_tmpJurusan,_tmpTingkat_kelas,_tmpTahun_ajaran,_tmpNilai_rapor,_tmpNilai_teori,_tmpNilai_lab,_tmpNilai_pkl,_tmpPersentase_hadir,_tmpJam_terlambat,_tmpPoin_pelanggaran,_tmpSkor_sikap);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Integer> getCountSiswa() {
    final String _sql = "SELECT COUNT(*) FROM siswa_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"siswa_table"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<SiswaEntity>> getAllSiswaLive() {
    final String _sql = "SELECT * FROM siswa_table ORDER BY nama ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"siswa_table"}, false, new Callable<List<SiswaEntity>>() {
      @Override
      @Nullable
      public List<SiswaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNis = CursorUtil.getColumnIndexOrThrow(_cursor, "nis");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfJurusan = CursorUtil.getColumnIndexOrThrow(_cursor, "jurusan");
          final int _cursorIndexOfTingkatKelas = CursorUtil.getColumnIndexOrThrow(_cursor, "tingkat_kelas");
          final int _cursorIndexOfTahunAjaran = CursorUtil.getColumnIndexOrThrow(_cursor, "tahun_ajaran");
          final int _cursorIndexOfNilaiRapor = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_rapor");
          final int _cursorIndexOfNilaiTeori = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_teori");
          final int _cursorIndexOfNilaiLab = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_lab");
          final int _cursorIndexOfNilaiPkl = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_pkl");
          final int _cursorIndexOfPersentaseHadir = CursorUtil.getColumnIndexOrThrow(_cursor, "persentase_hadir");
          final int _cursorIndexOfJamTerlambat = CursorUtil.getColumnIndexOrThrow(_cursor, "jam_terlambat");
          final int _cursorIndexOfPoinPelanggaran = CursorUtil.getColumnIndexOrThrow(_cursor, "poin_pelanggaran");
          final int _cursorIndexOfSkorSikap = CursorUtil.getColumnIndexOrThrow(_cursor, "skor_sikap");
          final List<SiswaEntity> _result = new ArrayList<SiswaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SiswaEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNis;
            if (_cursor.isNull(_cursorIndexOfNis)) {
              _tmpNis = null;
            } else {
              _tmpNis = _cursor.getString(_cursorIndexOfNis);
            }
            final String _tmpNama;
            if (_cursor.isNull(_cursorIndexOfNama)) {
              _tmpNama = null;
            } else {
              _tmpNama = _cursor.getString(_cursorIndexOfNama);
            }
            final String _tmpJurusan;
            if (_cursor.isNull(_cursorIndexOfJurusan)) {
              _tmpJurusan = null;
            } else {
              _tmpJurusan = _cursor.getString(_cursorIndexOfJurusan);
            }
            final String _tmpTingkat_kelas;
            if (_cursor.isNull(_cursorIndexOfTingkatKelas)) {
              _tmpTingkat_kelas = null;
            } else {
              _tmpTingkat_kelas = _cursor.getString(_cursorIndexOfTingkatKelas);
            }
            final String _tmpTahun_ajaran;
            if (_cursor.isNull(_cursorIndexOfTahunAjaran)) {
              _tmpTahun_ajaran = null;
            } else {
              _tmpTahun_ajaran = _cursor.getString(_cursorIndexOfTahunAjaran);
            }
            final double _tmpNilai_rapor;
            _tmpNilai_rapor = _cursor.getDouble(_cursorIndexOfNilaiRapor);
            final double _tmpNilai_teori;
            _tmpNilai_teori = _cursor.getDouble(_cursorIndexOfNilaiTeori);
            final double _tmpNilai_lab;
            _tmpNilai_lab = _cursor.getDouble(_cursorIndexOfNilaiLab);
            final double _tmpNilai_pkl;
            _tmpNilai_pkl = _cursor.getDouble(_cursorIndexOfNilaiPkl);
            final double _tmpPersentase_hadir;
            _tmpPersentase_hadir = _cursor.getDouble(_cursorIndexOfPersentaseHadir);
            final double _tmpJam_terlambat;
            _tmpJam_terlambat = _cursor.getDouble(_cursorIndexOfJamTerlambat);
            final double _tmpPoin_pelanggaran;
            _tmpPoin_pelanggaran = _cursor.getDouble(_cursorIndexOfPoinPelanggaran);
            final double _tmpSkor_sikap;
            _tmpSkor_sikap = _cursor.getDouble(_cursorIndexOfSkorSikap);
            _item = new SiswaEntity(_tmpId,_tmpNis,_tmpNama,_tmpJurusan,_tmpTingkat_kelas,_tmpTahun_ajaran,_tmpNilai_rapor,_tmpNilai_teori,_tmpNilai_lab,_tmpNilai_pkl,_tmpPersentase_hadir,_tmpJam_terlambat,_tmpPoin_pelanggaran,_tmpSkor_sikap);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSiswaByJurusanAndKelas(final String jurusan, final String kelas,
      final Continuation<? super List<SiswaEntity>> $completion) {
    final String _sql = "SELECT * FROM siswa_table WHERE jurusan = ? AND tingkat_kelas = ? ORDER BY nama ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (jurusan == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, jurusan);
    }
    _argIndex = 2;
    if (kelas == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, kelas);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SiswaEntity>>() {
      @Override
      @NonNull
      public List<SiswaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNis = CursorUtil.getColumnIndexOrThrow(_cursor, "nis");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfJurusan = CursorUtil.getColumnIndexOrThrow(_cursor, "jurusan");
          final int _cursorIndexOfTingkatKelas = CursorUtil.getColumnIndexOrThrow(_cursor, "tingkat_kelas");
          final int _cursorIndexOfTahunAjaran = CursorUtil.getColumnIndexOrThrow(_cursor, "tahun_ajaran");
          final int _cursorIndexOfNilaiRapor = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_rapor");
          final int _cursorIndexOfNilaiTeori = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_teori");
          final int _cursorIndexOfNilaiLab = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_lab");
          final int _cursorIndexOfNilaiPkl = CursorUtil.getColumnIndexOrThrow(_cursor, "nilai_pkl");
          final int _cursorIndexOfPersentaseHadir = CursorUtil.getColumnIndexOrThrow(_cursor, "persentase_hadir");
          final int _cursorIndexOfJamTerlambat = CursorUtil.getColumnIndexOrThrow(_cursor, "jam_terlambat");
          final int _cursorIndexOfPoinPelanggaran = CursorUtil.getColumnIndexOrThrow(_cursor, "poin_pelanggaran");
          final int _cursorIndexOfSkorSikap = CursorUtil.getColumnIndexOrThrow(_cursor, "skor_sikap");
          final List<SiswaEntity> _result = new ArrayList<SiswaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SiswaEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpNis;
            if (_cursor.isNull(_cursorIndexOfNis)) {
              _tmpNis = null;
            } else {
              _tmpNis = _cursor.getString(_cursorIndexOfNis);
            }
            final String _tmpNama;
            if (_cursor.isNull(_cursorIndexOfNama)) {
              _tmpNama = null;
            } else {
              _tmpNama = _cursor.getString(_cursorIndexOfNama);
            }
            final String _tmpJurusan;
            if (_cursor.isNull(_cursorIndexOfJurusan)) {
              _tmpJurusan = null;
            } else {
              _tmpJurusan = _cursor.getString(_cursorIndexOfJurusan);
            }
            final String _tmpTingkat_kelas;
            if (_cursor.isNull(_cursorIndexOfTingkatKelas)) {
              _tmpTingkat_kelas = null;
            } else {
              _tmpTingkat_kelas = _cursor.getString(_cursorIndexOfTingkatKelas);
            }
            final String _tmpTahun_ajaran;
            if (_cursor.isNull(_cursorIndexOfTahunAjaran)) {
              _tmpTahun_ajaran = null;
            } else {
              _tmpTahun_ajaran = _cursor.getString(_cursorIndexOfTahunAjaran);
            }
            final double _tmpNilai_rapor;
            _tmpNilai_rapor = _cursor.getDouble(_cursorIndexOfNilaiRapor);
            final double _tmpNilai_teori;
            _tmpNilai_teori = _cursor.getDouble(_cursorIndexOfNilaiTeori);
            final double _tmpNilai_lab;
            _tmpNilai_lab = _cursor.getDouble(_cursorIndexOfNilaiLab);
            final double _tmpNilai_pkl;
            _tmpNilai_pkl = _cursor.getDouble(_cursorIndexOfNilaiPkl);
            final double _tmpPersentase_hadir;
            _tmpPersentase_hadir = _cursor.getDouble(_cursorIndexOfPersentaseHadir);
            final double _tmpJam_terlambat;
            _tmpJam_terlambat = _cursor.getDouble(_cursorIndexOfJamTerlambat);
            final double _tmpPoin_pelanggaran;
            _tmpPoin_pelanggaran = _cursor.getDouble(_cursorIndexOfPoinPelanggaran);
            final double _tmpSkor_sikap;
            _tmpSkor_sikap = _cursor.getDouble(_cursorIndexOfSkorSikap);
            _item = new SiswaEntity(_tmpId,_tmpNis,_tmpNama,_tmpJurusan,_tmpTingkat_kelas,_tmpTahun_ajaran,_tmpNilai_rapor,_tmpNilai_teori,_tmpNilai_lab,_tmpNilai_pkl,_tmpPersentase_hadir,_tmpJam_terlambat,_tmpPoin_pelanggaran,_tmpSkor_sikap);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalSiswa(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM siswa_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
