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
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.budgetmate.app.data.entity.UserEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserEntity> __insertionAdapterOfUserEntity;

  private final EntityDeletionOrUpdateAdapter<UserEntity> __updateAdapterOfUserEntity;

  private final SharedSQLiteStatement __preparedStmtOfAddXp;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStreak;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `users` (`userId`,`username`,`passwordHash`,`displayName`,`xpTotal`,`streak`,`lastLogDate`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindString(2, entity.getUsername());
        statement.bindString(3, entity.getPasswordHash());
        statement.bindString(4, entity.getDisplayName());
        statement.bindLong(5, entity.getXpTotal());
        statement.bindLong(6, entity.getStreak());
        statement.bindString(7, entity.getLastLogDate());
      }
    };
    this.__updateAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `userId` = ?,`username` = ?,`passwordHash` = ?,`displayName` = ?,`xpTotal` = ?,`streak` = ?,`lastLogDate` = ? WHERE `userId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindLong(1, entity.getUserId());
        statement.bindString(2, entity.getUsername());
        statement.bindString(3, entity.getPasswordHash());
        statement.bindString(4, entity.getDisplayName());
        statement.bindLong(5, entity.getXpTotal());
        statement.bindLong(6, entity.getStreak());
        statement.bindString(7, entity.getLastLogDate());
        statement.bindLong(8, entity.getUserId());
      }
    };
    this.__preparedStmtOfAddXp = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET xpTotal = xpTotal + ? WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStreak = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET streak = ?, lastLogDate = ? WHERE userId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final UserEntity user, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfUserEntity.insertAndReturnId(user);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final UserEntity user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserEntity.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addXp(final int userId, final int xp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddXp.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, xp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfAddXp.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStreak(final int userId, final int streak, final String date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStreak.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, streak);
        _argIndex = 2;
        _stmt.bindString(_argIndex, date);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfUpdateStreak.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserByUsername(final String username,
      final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE username = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, username);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfXpTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "xpTotal");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfLastLogDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLogDate");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final int _tmpXpTotal;
            _tmpXpTotal = _cursor.getInt(_cursorIndexOfXpTotal);
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final String _tmpLastLogDate;
            _tmpLastLogDate = _cursor.getString(_cursorIndexOfLastLogDate);
            _result = new UserEntity(_tmpUserId,_tmpUsername,_tmpPasswordHash,_tmpDisplayName,_tmpXpTotal,_tmpStreak,_tmpLastLogDate);
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
  public Object getUserById(final int userId, final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE userId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfXpTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "xpTotal");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfLastLogDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLogDate");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final int _tmpXpTotal;
            _tmpXpTotal = _cursor.getInt(_cursorIndexOfXpTotal);
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final String _tmpLastLogDate;
            _tmpLastLogDate = _cursor.getString(_cursorIndexOfLastLogDate);
            _result = new UserEntity(_tmpUserId,_tmpUsername,_tmpPasswordHash,_tmpDisplayName,_tmpXpTotal,_tmpStreak,_tmpLastLogDate);
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
  public Object login(final String username, final String passwordHash,
      final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE username = ? AND passwordHash = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, username);
    _argIndex = 2;
    _statement.bindString(_argIndex, passwordHash);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserEntity>() {
      @Override
      @Nullable
      public UserEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfXpTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "xpTotal");
          final int _cursorIndexOfStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "streak");
          final int _cursorIndexOfLastLogDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLogDate");
          final UserEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final int _tmpXpTotal;
            _tmpXpTotal = _cursor.getInt(_cursorIndexOfXpTotal);
            final int _tmpStreak;
            _tmpStreak = _cursor.getInt(_cursorIndexOfStreak);
            final String _tmpLastLogDate;
            _tmpLastLogDate = _cursor.getString(_cursorIndexOfLastLogDate);
            _result = new UserEntity(_tmpUserId,_tmpUsername,_tmpPasswordHash,_tmpDisplayName,_tmpXpTotal,_tmpStreak,_tmpLastLogDate);
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
  public Flow<Integer> observeXp(final int userId) {
    final String _sql = "SELECT xpTotal FROM users WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _result;
          if (_cursor.moveToFirst()) {
            _result = _cursor.getInt(0);
          } else {
            _result = 0;
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
  public Flow<Integer> observeStreak(final int userId) {
    final String _sql = "SELECT streak FROM users WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _result;
          if (_cursor.moveToFirst()) {
            _result = _cursor.getInt(0);
          } else {
            _result = 0;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
