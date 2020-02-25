package com.example.contacts.Application;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.contacts.Contact.ContactDao;
import com.example.contacts.Contact.ContactDao_Impl;
import com.example.contacts.Group.GroupDao;
import com.example.contacts.Group.GroupDao_Impl;
import com.example.contacts.GroupContactJoin.GroupContactJoinDao;
import com.example.contacts.GroupContactJoin.GroupContactJoinDao_Impl;
import com.example.contacts.TelNumber.TelNumberDao;
import com.example.contacts.TelNumber.TelNumberDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ContactRoomDatabase_Impl extends ContactRoomDatabase {
  private volatile ContactDao _contactDao;

  private volatile GroupDao _groupDao;

  private volatile GroupContactJoinDao _groupContactJoinDao;

  private volatile TelNumberDao _telNumberDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(10) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `contact_table` (`name` TEXT NOT NULL, `street` TEXT, `city` TEXT, `code_postal` INTEGER, PRIMARY KEY(`name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `group_table` (`name` TEXT NOT NULL, PRIMARY KEY(`name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Group_Contact_join` (`groupName` TEXT NOT NULL, `contactName` TEXT NOT NULL, PRIMARY KEY(`groupName`, `contactName`), FOREIGN KEY(`contactName`) REFERENCES `contact_table`(`name`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`groupName`) REFERENCES `group_table`(`name`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_Group_Contact_join_contactName` ON `Group_Contact_join` (`contactName`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TelNumber` (`category` TEXT, `contactNameNu` TEXT, `number` TEXT NOT NULL, PRIMARY KEY(`number`), FOREIGN KEY(`contactNameNu`) REFERENCES `contact_table`(`name`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_TelNumber_contactNameNu` ON `TelNumber` (`contactNameNu`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '68af627eaa684d4f6fcfccba0089c4e3')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `contact_table`");
        _db.execSQL("DROP TABLE IF EXISTS `group_table`");
        _db.execSQL("DROP TABLE IF EXISTS `Group_Contact_join`");
        _db.execSQL("DROP TABLE IF EXISTS `TelNumber`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsContactTable = new HashMap<String, TableInfo.Column>(4);
        _columnsContactTable.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContactTable.put("street", new TableInfo.Column("street", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContactTable.put("city", new TableInfo.Column("city", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContactTable.put("code_postal", new TableInfo.Column("code_postal", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContactTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesContactTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContactTable = new TableInfo("contact_table", _columnsContactTable, _foreignKeysContactTable, _indicesContactTable);
        final TableInfo _existingContactTable = TableInfo.read(_db, "contact_table");
        if (! _infoContactTable.equals(_existingContactTable)) {
          return new RoomOpenHelper.ValidationResult(false, "contact_table(com.example.contacts.Contact.Contact).\n"
                  + " Expected:\n" + _infoContactTable + "\n"
                  + " Found:\n" + _existingContactTable);
        }
        final HashMap<String, TableInfo.Column> _columnsGroupTable = new HashMap<String, TableInfo.Column>(1);
        _columnsGroupTable.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGroupTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGroupTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGroupTable = new TableInfo("group_table", _columnsGroupTable, _foreignKeysGroupTable, _indicesGroupTable);
        final TableInfo _existingGroupTable = TableInfo.read(_db, "group_table");
        if (! _infoGroupTable.equals(_existingGroupTable)) {
          return new RoomOpenHelper.ValidationResult(false, "group_table(com.example.contacts.Group.Group).\n"
                  + " Expected:\n" + _infoGroupTable + "\n"
                  + " Found:\n" + _existingGroupTable);
        }
        final HashMap<String, TableInfo.Column> _columnsGroupContactJoin = new HashMap<String, TableInfo.Column>(2);
        _columnsGroupContactJoin.put("groupName", new TableInfo.Column("groupName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGroupContactJoin.put("contactName", new TableInfo.Column("contactName", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGroupContactJoin = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysGroupContactJoin.add(new TableInfo.ForeignKey("contact_table", "CASCADE", "NO ACTION",Arrays.asList("contactName"), Arrays.asList("name")));
        _foreignKeysGroupContactJoin.add(new TableInfo.ForeignKey("group_table", "CASCADE", "NO ACTION",Arrays.asList("groupName"), Arrays.asList("name")));
        final HashSet<TableInfo.Index> _indicesGroupContactJoin = new HashSet<TableInfo.Index>(1);
        _indicesGroupContactJoin.add(new TableInfo.Index("index_Group_Contact_join_contactName", false, Arrays.asList("contactName")));
        final TableInfo _infoGroupContactJoin = new TableInfo("Group_Contact_join", _columnsGroupContactJoin, _foreignKeysGroupContactJoin, _indicesGroupContactJoin);
        final TableInfo _existingGroupContactJoin = TableInfo.read(_db, "Group_Contact_join");
        if (! _infoGroupContactJoin.equals(_existingGroupContactJoin)) {
          return new RoomOpenHelper.ValidationResult(false, "Group_Contact_join(com.example.contacts.GroupContactJoin.GroupContactJoin).\n"
                  + " Expected:\n" + _infoGroupContactJoin + "\n"
                  + " Found:\n" + _existingGroupContactJoin);
        }
        final HashMap<String, TableInfo.Column> _columnsTelNumber = new HashMap<String, TableInfo.Column>(3);
        _columnsTelNumber.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelNumber.put("contactNameNu", new TableInfo.Column("contactNameNu", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTelNumber.put("number", new TableInfo.Column("number", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTelNumber = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTelNumber.add(new TableInfo.ForeignKey("contact_table", "CASCADE", "NO ACTION",Arrays.asList("contactNameNu"), Arrays.asList("name")));
        final HashSet<TableInfo.Index> _indicesTelNumber = new HashSet<TableInfo.Index>(1);
        _indicesTelNumber.add(new TableInfo.Index("index_TelNumber_contactNameNu", false, Arrays.asList("contactNameNu")));
        final TableInfo _infoTelNumber = new TableInfo("TelNumber", _columnsTelNumber, _foreignKeysTelNumber, _indicesTelNumber);
        final TableInfo _existingTelNumber = TableInfo.read(_db, "TelNumber");
        if (! _infoTelNumber.equals(_existingTelNumber)) {
          return new RoomOpenHelper.ValidationResult(false, "TelNumber(com.example.contacts.TelNumber.TelNumber).\n"
                  + " Expected:\n" + _infoTelNumber + "\n"
                  + " Found:\n" + _existingTelNumber);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "68af627eaa684d4f6fcfccba0089c4e3", "fbc7870107e7df2945027f2d4a8f53e9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "contact_table","group_table","Group_Contact_join","TelNumber");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `contact_table`");
      _db.execSQL("DELETE FROM `group_table`");
      _db.execSQL("DELETE FROM `Group_Contact_join`");
      _db.execSQL("DELETE FROM `TelNumber`");
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
  public ContactDao contactDao() {
    if (_contactDao != null) {
      return _contactDao;
    } else {
      synchronized(this) {
        if(_contactDao == null) {
          _contactDao = new ContactDao_Impl(this);
        }
        return _contactDao;
      }
    }
  }

  @Override
  public GroupDao groupDao() {
    if (_groupDao != null) {
      return _groupDao;
    } else {
      synchronized(this) {
        if(_groupDao == null) {
          _groupDao = new GroupDao_Impl(this);
        }
        return _groupDao;
      }
    }
  }

  @Override
  public GroupContactJoinDao groupContactJoinDao() {
    if (_groupContactJoinDao != null) {
      return _groupContactJoinDao;
    } else {
      synchronized(this) {
        if(_groupContactJoinDao == null) {
          _groupContactJoinDao = new GroupContactJoinDao_Impl(this);
        }
        return _groupContactJoinDao;
      }
    }
  }

  @Override
  public TelNumberDao telNumberDao() {
    if (_telNumberDao != null) {
      return _telNumberDao;
    } else {
      synchronized(this) {
        if(_telNumberDao == null) {
          _telNumberDao = new TelNumberDao_Impl(this);
        }
        return _telNumberDao;
      }
    }
  }
}
