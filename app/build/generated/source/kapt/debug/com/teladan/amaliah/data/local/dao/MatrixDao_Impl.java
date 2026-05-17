package com.teladan.amaliah.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MatrixDao_Impl implements MatrixDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<KriteriaMatrixEntity> __insertionAdapterOfKriteriaMatrixEntity;

  public MatrixDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfKriteriaMatrixEntity = new EntityInsertionAdapter<KriteriaMatrixEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `kriteria_matrix` (`id`,`praktik_vs_akademik`,`disiplin_vs_akademik`,`praktik_vs_hadir`,`akademik_vs_hadir`,`praktik_vs_disiplin`,`hadir_vs_disiplin`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final KriteriaMatrixEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getPraktik_vs_akademik());
        statement.bindDouble(3, entity.getDisiplin_vs_akademik());
        statement.bindDouble(4, entity.getPraktik_vs_hadir());
        statement.bindDouble(5, entity.getAkademik_vs_hadir());
        statement.bindDouble(6, entity.getPraktik_vs_disiplin());
        statement.bindDouble(7, entity.getHadir_vs_disiplin());
      }
    };
  }

  @Override
  public Object saveMatrix(final KriteriaMatrixEntity matrix,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfKriteriaMatrixEntity.insert(matrix);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMatrix(final Continuation<? super KriteriaMatrixEntity> $completion) {
    final String _sql = "SELECT * FROM kriteria_matrix WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<KriteriaMatrixEntity>() {
      @Override
      @Nullable
      public KriteriaMatrixEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPraktikVsAkademik = CursorUtil.getColumnIndexOrThrow(_cursor, "praktik_vs_akademik");
          final int _cursorIndexOfDisiplinVsAkademik = CursorUtil.getColumnIndexOrThrow(_cursor, "disiplin_vs_akademik");
          final int _cursorIndexOfPraktikVsHadir = CursorUtil.getColumnIndexOrThrow(_cursor, "praktik_vs_hadir");
          final int _cursorIndexOfAkademikVsHadir = CursorUtil.getColumnIndexOrThrow(_cursor, "akademik_vs_hadir");
          final int _cursorIndexOfPraktikVsDisiplin = CursorUtil.getColumnIndexOrThrow(_cursor, "praktik_vs_disiplin");
          final int _cursorIndexOfHadirVsDisiplin = CursorUtil.getColumnIndexOrThrow(_cursor, "hadir_vs_disiplin");
          final KriteriaMatrixEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpPraktik_vs_akademik;
            _tmpPraktik_vs_akademik = _cursor.getDouble(_cursorIndexOfPraktikVsAkademik);
            final double _tmpDisiplin_vs_akademik;
            _tmpDisiplin_vs_akademik = _cursor.getDouble(_cursorIndexOfDisiplinVsAkademik);
            final double _tmpPraktik_vs_hadir;
            _tmpPraktik_vs_hadir = _cursor.getDouble(_cursorIndexOfPraktikVsHadir);
            final double _tmpAkademik_vs_hadir;
            _tmpAkademik_vs_hadir = _cursor.getDouble(_cursorIndexOfAkademikVsHadir);
            final double _tmpPraktik_vs_disiplin;
            _tmpPraktik_vs_disiplin = _cursor.getDouble(_cursorIndexOfPraktikVsDisiplin);
            final double _tmpHadir_vs_disiplin;
            _tmpHadir_vs_disiplin = _cursor.getDouble(_cursorIndexOfHadirVsDisiplin);
            _result = new KriteriaMatrixEntity(_tmpId,_tmpPraktik_vs_akademik,_tmpDisiplin_vs_akademik,_tmpPraktik_vs_hadir,_tmpAkademik_vs_hadir,_tmpPraktik_vs_disiplin,_tmpHadir_vs_disiplin);
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
