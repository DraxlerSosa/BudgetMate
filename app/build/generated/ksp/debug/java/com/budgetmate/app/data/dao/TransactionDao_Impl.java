package com.budgetmate.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.budgetmate.app.data.entity.TransactionEntity;
import java.lang.Class;
import java.lang.Double;
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
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransactionEntity> __insertionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __deletionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __updateAdapterOfTransactionEntity;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactionEntity = new EntityInsertionAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transactions` (`transactionId`,`userId`,`categoryId`,`amount`,`date`,`startTime`,`endTime`,`description`,`photoUri`,`type`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getTransactionId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getCategoryId());
        statement.bindDouble(4, entity.getAmount());
        statement.bindString(5, entity.getDate());
        statement.bindString(6, entity.getStartTime());
        statement.bindString(7, entity.getEndTime());
        if (entity.getDescription() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDescription());
        }
        if (entity.getPhotoUri() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPhotoUri());
        }
        statement.bindString(10, entity.getType());
      }
    };
    this.__deletionAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `transactions` WHERE `transactionId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getTransactionId());
      }
    };
    this.__updateAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `transactionId` = ?,`userId` = ?,`categoryId` = ?,`amount` = ?,`date` = ?,`startTime` = ?,`endTime` = ?,`description` = ?,`photoUri` = ?,`type` = ? WHERE `transactionId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getTransactionId());
        statement.bindLong(2, entity.getUserId());
        statement.bindLong(3, entity.getCategoryId());
        statement.bindDouble(4, entity.getAmount());
        statement.bindString(5, entity.getDate());
        statement.bindString(6, entity.getStartTime());
        statement.bindString(7, entity.getEndTime());
        if (entity.getDescription() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDescription());
        }
        if (entity.getPhotoUri() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getPhotoUri());
        }
        statement.bindString(10, entity.getType());
        statement.bindLong(11, entity.getTransactionId());
      }
    };
  }

  @Override
  public Object insertTransaction(final TransactionEntity transaction,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTransactionEntity.insertAndReturnId(transaction);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTransaction(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTransaction(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTransactionById(final int id,
      final Continuation<? super TransactionEntity> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE transactionId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TransactionEntity>() {
      @Override
      @Nullable
      public TransactionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final TransactionEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpTransactionId;
            _tmpTransactionId = _cursor.getInt(_cursorIndexOfTransactionId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _result = new TransactionEntity(_tmpTransactionId,_tmpUserId,_tmpCategoryId,_tmpAmount,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpDescription,_tmpPhotoUri,_tmpType);
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
  public Flow<List<TransactionEntity>> observeTransactionsByDateRange(final int userId,
      final String startDate, final String endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM transactions\n"
            + "        WHERE userId = ? AND date BETWEEN ? AND ?\n"
            + "        ORDER BY date DESC, startTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, startDate);
    _argIndex = 3;
    _statement.bindString(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final int _tmpTransactionId;
            _tmpTransactionId = _cursor.getInt(_cursorIndexOfTransactionId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new TransactionEntity(_tmpTransactionId,_tmpUserId,_tmpCategoryId,_tmpAmount,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpDescription,_tmpPhotoUri,_tmpType);
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
  public Flow<List<CategoryTotal>> observeCategoryTotals(final int userId, final String startDate,
      final String endDate) {
    final String _sql = "\n"
            + "        SELECT t.categoryId, c.name AS categoryName, c.iconEmoji, c.colourHex,\n"
            + "               SUM(t.amount) AS total\n"
            + "        FROM transactions t\n"
            + "        INNER JOIN categories c ON t.categoryId = c.categoryId\n"
            + "        WHERE t.userId = ? AND t.date BETWEEN ? AND ?\n"
            + "        AND t.type = 'EXPENSE'\n"
            + "        GROUP BY t.categoryId ORDER BY total DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, startDate);
    _argIndex = 3;
    _statement.bindString(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions",
        "categories"}, new Callable<List<CategoryTotal>>() {
      @Override
      @NonNull
      public List<CategoryTotal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategoryId = 0;
          final int _cursorIndexOfCategoryName = 1;
          final int _cursorIndexOfIconEmoji = 2;
          final int _cursorIndexOfColourHex = 3;
          final int _cursorIndexOfTotal = 4;
          final List<CategoryTotal> _result = new ArrayList<CategoryTotal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryTotal _item;
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final String _tmpCategoryName;
            _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            final String _tmpIconEmoji;
            _tmpIconEmoji = _cursor.getString(_cursorIndexOfIconEmoji);
            final String _tmpColourHex;
            _tmpColourHex = _cursor.getString(_cursorIndexOfColourHex);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            _item = new CategoryTotal(_tmpCategoryId,_tmpCategoryName,_tmpIconEmoji,_tmpColourHex,_tmpTotal);
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
  public Flow<Double> observeMonthlyTotal(final int userId, final String year, final String month) {
    final String _sql = "\n"
            + "        SELECT COALESCE(SUM(amount), 0.0) FROM transactions\n"
            + "        WHERE userId = ?\n"
            + "        AND strftime('%Y', date) = ?\n"
            + "        AND strftime('%m', date) = ?\n"
            + "        AND type = 'EXPENSE'\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, year);
    _argIndex = 3;
    _statement.bindString(_argIndex, month);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
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
  public Object getTransactionCount(final int userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM transactions WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
  public Object getRecentTransactionDates(final int userId,
      final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT date FROM transactions WHERE userId = ? ORDER BY date DESC LIMIT 30";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
