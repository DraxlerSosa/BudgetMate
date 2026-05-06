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
import com.budgetmate.app.data.entity.CategoryEntity;
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
public final class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CategoryEntity> __insertionAdapterOfCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<CategoryEntity> __deletionAdapterOfCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<CategoryEntity> __updateAdapterOfCategoryEntity;

  public CategoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategoryEntity = new EntityInsertionAdapter<CategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `categories` (`categoryId`,`userId`,`name`,`iconEmoji`,`colourHex`,`monthlyBudgetCap`,`isDefault`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoryEntity entity) {
        statement.bindLong(1, entity.getCategoryId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getIconEmoji());
        statement.bindString(5, entity.getColourHex());
        if (entity.getMonthlyBudgetCap() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getMonthlyBudgetCap());
        }
        final int _tmp = entity.isDefault() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__deletionAdapterOfCategoryEntity = new EntityDeletionOrUpdateAdapter<CategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `categories` WHERE `categoryId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoryEntity entity) {
        statement.bindLong(1, entity.getCategoryId());
      }
    };
    this.__updateAdapterOfCategoryEntity = new EntityDeletionOrUpdateAdapter<CategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `categories` SET `categoryId` = ?,`userId` = ?,`name` = ?,`iconEmoji` = ?,`colourHex` = ?,`monthlyBudgetCap` = ?,`isDefault` = ? WHERE `categoryId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoryEntity entity) {
        statement.bindLong(1, entity.getCategoryId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getIconEmoji());
        statement.bindString(5, entity.getColourHex());
        if (entity.getMonthlyBudgetCap() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getMonthlyBudgetCap());
        }
        final int _tmp = entity.isDefault() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCategoryId());
      }
    };
  }

  @Override
  public Object insertCategory(final CategoryEntity category,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCategoryEntity.insertAndReturnId(category);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCategory(final CategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCategoryEntity.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCategory(final CategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCategoryEntity.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CategoryEntity>> observeCategoriesForUser(final int userId) {
    final String _sql = "SELECT * FROM categories WHERE userId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categories"}, new Callable<List<CategoryEntity>>() {
      @Override
      @NonNull
      public List<CategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "iconEmoji");
          final int _cursorIndexOfColourHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colourHex");
          final int _cursorIndexOfMonthlyBudgetCap = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyBudgetCap");
          final int _cursorIndexOfIsDefault = CursorUtil.getColumnIndexOrThrow(_cursor, "isDefault");
          final List<CategoryEntity> _result = new ArrayList<CategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryEntity _item;
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIconEmoji;
            _tmpIconEmoji = _cursor.getString(_cursorIndexOfIconEmoji);
            final String _tmpColourHex;
            _tmpColourHex = _cursor.getString(_cursorIndexOfColourHex);
            final Double _tmpMonthlyBudgetCap;
            if (_cursor.isNull(_cursorIndexOfMonthlyBudgetCap)) {
              _tmpMonthlyBudgetCap = null;
            } else {
              _tmpMonthlyBudgetCap = _cursor.getDouble(_cursorIndexOfMonthlyBudgetCap);
            }
            final boolean _tmpIsDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDefault);
            _tmpIsDefault = _tmp != 0;
            _item = new CategoryEntity(_tmpCategoryId,_tmpUserId,_tmpName,_tmpIconEmoji,_tmpColourHex,_tmpMonthlyBudgetCap,_tmpIsDefault);
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
  public Object getCategoriesForUser(final int userId,
      final Continuation<? super List<CategoryEntity>> $completion) {
    final String _sql = "SELECT * FROM categories WHERE userId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CategoryEntity>>() {
      @Override
      @NonNull
      public List<CategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "iconEmoji");
          final int _cursorIndexOfColourHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colourHex");
          final int _cursorIndexOfMonthlyBudgetCap = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyBudgetCap");
          final int _cursorIndexOfIsDefault = CursorUtil.getColumnIndexOrThrow(_cursor, "isDefault");
          final List<CategoryEntity> _result = new ArrayList<CategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryEntity _item;
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIconEmoji;
            _tmpIconEmoji = _cursor.getString(_cursorIndexOfIconEmoji);
            final String _tmpColourHex;
            _tmpColourHex = _cursor.getString(_cursorIndexOfColourHex);
            final Double _tmpMonthlyBudgetCap;
            if (_cursor.isNull(_cursorIndexOfMonthlyBudgetCap)) {
              _tmpMonthlyBudgetCap = null;
            } else {
              _tmpMonthlyBudgetCap = _cursor.getDouble(_cursorIndexOfMonthlyBudgetCap);
            }
            final boolean _tmpIsDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDefault);
            _tmpIsDefault = _tmp != 0;
            _item = new CategoryEntity(_tmpCategoryId,_tmpUserId,_tmpName,_tmpIconEmoji,_tmpColourHex,_tmpMonthlyBudgetCap,_tmpIsDefault);
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
  public Object getCategoryById(final int categoryId,
      final Continuation<? super CategoryEntity> $completion) {
    final String _sql = "SELECT * FROM categories WHERE categoryId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CategoryEntity>() {
      @Override
      @Nullable
      public CategoryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "iconEmoji");
          final int _cursorIndexOfColourHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colourHex");
          final int _cursorIndexOfMonthlyBudgetCap = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyBudgetCap");
          final int _cursorIndexOfIsDefault = CursorUtil.getColumnIndexOrThrow(_cursor, "isDefault");
          final CategoryEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpCategoryId;
            _tmpCategoryId = _cursor.getInt(_cursorIndexOfCategoryId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpIconEmoji;
            _tmpIconEmoji = _cursor.getString(_cursorIndexOfIconEmoji);
            final String _tmpColourHex;
            _tmpColourHex = _cursor.getString(_cursorIndexOfColourHex);
            final Double _tmpMonthlyBudgetCap;
            if (_cursor.isNull(_cursorIndexOfMonthlyBudgetCap)) {
              _tmpMonthlyBudgetCap = null;
            } else {
              _tmpMonthlyBudgetCap = _cursor.getDouble(_cursorIndexOfMonthlyBudgetCap);
            }
            final boolean _tmpIsDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDefault);
            _tmpIsDefault = _tmp != 0;
            _result = new CategoryEntity(_tmpCategoryId,_tmpUserId,_tmpName,_tmpIconEmoji,_tmpColourHex,_tmpMonthlyBudgetCap,_tmpIsDefault);
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
  public Object countByName(final int userId, final String name, final int excludeId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM categories WHERE userId = ? AND name = ? AND categoryId != ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, name);
    _argIndex = 3;
    _statement.bindLong(_argIndex, excludeId);
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
  public Object transactionCountForCategory(final int categoryId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM transactions WHERE categoryId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
