package com.budgetmate.app.data.database;

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
import com.budgetmate.app.data.dao.BadgeDao;
import com.budgetmate.app.data.dao.BadgeDao_Impl;
import com.budgetmate.app.data.dao.CategoryDao;
import com.budgetmate.app.data.dao.CategoryDao_Impl;
import com.budgetmate.app.data.dao.TransactionDao;
import com.budgetmate.app.data.dao.TransactionDao_Impl;
import com.budgetmate.app.data.dao.UserDao;
import com.budgetmate.app.data.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile CategoryDao _categoryDao;

  private volatile TransactionDao _transactionDao;

  private volatile BadgeDao _badgeDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`userId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, `displayName` TEXT NOT NULL, `xpTotal` INTEGER NOT NULL, `streak` INTEGER NOT NULL, `lastLogDate` TEXT NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_users_username` ON `users` (`username`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`categoryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `name` TEXT NOT NULL, `iconEmoji` TEXT NOT NULL, `colourHex` TEXT NOT NULL, `monthlyBudgetCap` REAL, `isDefault` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_categories_userId` ON `categories` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`transactionId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `categoryId` INTEGER NOT NULL, `amount` REAL NOT NULL, `date` TEXT NOT NULL, `startTime` TEXT NOT NULL, `endTime` TEXT NOT NULL, `description` TEXT, `photoUri` TEXT, `type` TEXT NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`categoryId`) REFERENCES `categories`(`categoryId`) ON UPDATE NO ACTION ON DELETE SET DEFAULT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_userId` ON `transactions` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_categoryId` ON `transactions` (`categoryId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_date` ON `transactions` (`date`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `badges` (`badgeId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `badgeKey` TEXT NOT NULL, `badgeName` TEXT NOT NULL, `badgeEmoji` TEXT NOT NULL, `earnedDate` TEXT NOT NULL, `xpReward` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_badges_userId` ON `badges` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4058dab610cb69a0656fc2828cfca4a6')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `transactions`");
        db.execSQL("DROP TABLE IF EXISTS `badges`");
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
        db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(7);
        _columnsUsers.put("userId", new TableInfo.Column("userId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("passwordHash", new TableInfo.Column("passwordHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("displayName", new TableInfo.Column("displayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("xpTotal", new TableInfo.Column("xpTotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("streak", new TableInfo.Column("streak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("lastLogDate", new TableInfo.Column("lastLogDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(1);
        _indicesUsers.add(new TableInfo.Index("index_users_username", true, Arrays.asList("username"), Arrays.asList("ASC")));
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.budgetmate.app.data.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(7);
        _columnsCategories.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("iconEmoji", new TableInfo.Column("iconEmoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("colourHex", new TableInfo.Column("colourHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("monthlyBudgetCap", new TableInfo.Column("monthlyBudgetCap", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("isDefault", new TableInfo.Column("isDefault", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCategories.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("userId")));
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(1);
        _indicesCategories.add(new TableInfo.Index("index_categories_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.budgetmate.app.data.entity.CategoryEntity).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsTransactions = new HashMap<String, TableInfo.Column>(10);
        _columnsTransactions.put("transactionId", new TableInfo.Column("transactionId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("startTime", new TableInfo.Column("startTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("endTime", new TableInfo.Column("endTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransactions = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysTransactions.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("userId")));
        _foreignKeysTransactions.add(new TableInfo.ForeignKey("categories", "SET DEFAULT", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("categoryId")));
        final HashSet<TableInfo.Index> _indicesTransactions = new HashSet<TableInfo.Index>(3);
        _indicesTransactions.add(new TableInfo.Index("index_transactions_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesTransactions.add(new TableInfo.Index("index_transactions_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        _indicesTransactions.add(new TableInfo.Index("index_transactions_date", false, Arrays.asList("date"), Arrays.asList("ASC")));
        final TableInfo _infoTransactions = new TableInfo("transactions", _columnsTransactions, _foreignKeysTransactions, _indicesTransactions);
        final TableInfo _existingTransactions = TableInfo.read(db, "transactions");
        if (!_infoTransactions.equals(_existingTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "transactions(com.budgetmate.app.data.entity.TransactionEntity).\n"
                  + " Expected:\n" + _infoTransactions + "\n"
                  + " Found:\n" + _existingTransactions);
        }
        final HashMap<String, TableInfo.Column> _columnsBadges = new HashMap<String, TableInfo.Column>(7);
        _columnsBadges.put("badgeId", new TableInfo.Column("badgeId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("badgeKey", new TableInfo.Column("badgeKey", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("badgeName", new TableInfo.Column("badgeName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("badgeEmoji", new TableInfo.Column("badgeEmoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("earnedDate", new TableInfo.Column("earnedDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBadges.put("xpReward", new TableInfo.Column("xpReward", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBadges = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysBadges.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("userId")));
        final HashSet<TableInfo.Index> _indicesBadges = new HashSet<TableInfo.Index>(1);
        _indicesBadges.add(new TableInfo.Index("index_badges_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoBadges = new TableInfo("badges", _columnsBadges, _foreignKeysBadges, _indicesBadges);
        final TableInfo _existingBadges = TableInfo.read(db, "badges");
        if (!_infoBadges.equals(_existingBadges)) {
          return new RoomOpenHelper.ValidationResult(false, "badges(com.budgetmate.app.data.entity.BadgeEntity).\n"
                  + " Expected:\n" + _infoBadges + "\n"
                  + " Found:\n" + _existingBadges);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "4058dab610cb69a0656fc2828cfca4a6", "b0e6f47372741d6164cf4f89be05db8c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","categories","transactions","badges");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `transactions`");
      _db.execSQL("DELETE FROM `badges`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
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
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TransactionDao.class, TransactionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BadgeDao.class, BadgeDao_Impl.getRequiredConverters());
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
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public TransactionDao transactionDao() {
    if (_transactionDao != null) {
      return _transactionDao;
    } else {
      synchronized(this) {
        if(_transactionDao == null) {
          _transactionDao = new TransactionDao_Impl(this);
        }
        return _transactionDao;
      }
    }
  }

  @Override
  public BadgeDao badgeDao() {
    if (_badgeDao != null) {
      return _badgeDao;
    } else {
      synchronized(this) {
        if(_badgeDao == null) {
          _badgeDao = new BadgeDao_Impl(this);
        }
        return _badgeDao;
      }
    }
  }
}
