package com.budgetmate.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.budgetmate.app.data.entity.BadgeEntity;
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
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BadgeDao_Impl implements BadgeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BadgeEntity> __insertionAdapterOfBadgeEntity;

  public BadgeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBadgeEntity = new EntityInsertionAdapter<BadgeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `badges` (`badgeId`,`userId`,`badgeKey`,`badgeName`,`badgeEmoji`,`earnedDate`,`xpReward`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BadgeEntity entity) {
        statement.bindLong(1, entity.getBadgeId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getBadgeKey());
        statement.bindString(4, entity.getBadgeName());
        statement.bindString(5, entity.getBadgeEmoji());
        statement.bindString(6, entity.getEarnedDate());
        statement.bindLong(7, entity.getXpReward());
      }
    };
  }

  @Override
  public Object insertBadge(final BadgeEntity badge, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBadgeEntity.insertAndReturnId(badge);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BadgeEntity>> observeBadgesForUser(final int userId) {
    final String _sql = "SELECT * FROM badges WHERE userId = ? ORDER BY earnedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"badges"}, new Callable<List<BadgeEntity>>() {
      @Override
      @NonNull
      public List<BadgeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBadgeId = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfBadgeKey = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeKey");
          final int _cursorIndexOfBadgeName = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeName");
          final int _cursorIndexOfBadgeEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "badgeEmoji");
          final int _cursorIndexOfEarnedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "earnedDate");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final List<BadgeEntity> _result = new ArrayList<BadgeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BadgeEntity _item;
            final int _tmpBadgeId;
            _tmpBadgeId = _cursor.getInt(_cursorIndexOfBadgeId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpBadgeKey;
            _tmpBadgeKey = _cursor.getString(_cursorIndexOfBadgeKey);
            final String _tmpBadgeName;
            _tmpBadgeName = _cursor.getString(_cursorIndexOfBadgeName);
            final String _tmpBadgeEmoji;
            _tmpBadgeEmoji = _cursor.getString(_cursorIndexOfBadgeEmoji);
            final String _tmpEarnedDate;
            _tmpEarnedDate = _cursor.getString(_cursorIndexOfEarnedDate);
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            _item = new BadgeEntity(_tmpBadgeId,_tmpUserId,_tmpBadgeKey,_tmpBadgeName,_tmpBadgeEmoji,_tmpEarnedDate,_tmpXpReward);
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
  public Object hasBadge(final int userId, final String key,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM badges WHERE userId = ? AND badgeKey = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, key);
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
  public Flow<Integer> observeBadgeCount(final int userId) {
    final String _sql = "SELECT COUNT(*) FROM badges WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"badges"}, new Callable<Integer>() {
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
