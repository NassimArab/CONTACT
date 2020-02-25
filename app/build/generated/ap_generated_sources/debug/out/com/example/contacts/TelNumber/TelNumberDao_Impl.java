package com.example.contacts.TelNumber;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TelNumberDao_Impl implements TelNumberDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TelNumber> __insertionAdapterOfTelNumber;

  private final EntityDeletionOrUpdateAdapter<TelNumber> __deletionAdapterOfTelNumber;

  private final EntityDeletionOrUpdateAdapter<TelNumber> __updateAdapterOfTelNumber;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNum;

  public TelNumberDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTelNumber = new EntityInsertionAdapter<TelNumber>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `TelNumber` (`category`,`contactNameNu`,`number`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TelNumber value) {
        if (value.category == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.category);
        }
        if (value.contactNameNu == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.contactNameNu);
        }
        if (value.number == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.number);
        }
      }
    };
    this.__deletionAdapterOfTelNumber = new EntityDeletionOrUpdateAdapter<TelNumber>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `TelNumber` WHERE `number` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TelNumber value) {
        if (value.number == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.number);
        }
      }
    };
    this.__updateAdapterOfTelNumber = new EntityDeletionOrUpdateAdapter<TelNumber>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `TelNumber` SET `category` = ?,`contactNameNu` = ?,`number` = ? WHERE `number` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TelNumber value) {
        if (value.category == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.category);
        }
        if (value.contactNameNu == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.contactNameNu);
        }
        if (value.number == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.number);
        }
        if (value.number == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.number);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM TelNumber";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteNum = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM TelNumber WHERE contactNameNu=?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final TelNumber number) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTelNumber.insert(number);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final TelNumber... number) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTelNumber.handleMultiple(number);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final TelNumber... number) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTelNumber.handleMultiple(number);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deleteNum(final String name) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNum.acquire();
    int _argIndex = 1;
    if (name == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, name);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteNum.release(_stmt);
    }
  }

  @Override
  public LiveData<List<TelNumber>> getAllTelNumber() {
    final String _sql = "SELECT * FROM TelNumber";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"TelNumber"}, false, new Callable<List<TelNumber>>() {
      @Override
      public List<TelNumber> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfContactNameNu = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNameNu");
          final int _cursorIndexOfNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "number");
          final List<TelNumber> _result = new ArrayList<TelNumber>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TelNumber _item;
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpContactNameNu;
            _tmpContactNameNu = _cursor.getString(_cursorIndexOfContactNameNu);
            final String _tmpNumber;
            _tmpNumber = _cursor.getString(_cursorIndexOfNumber);
            _item = new TelNumber(_tmpNumber,_tmpContactNameNu,_tmpCategory);
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
  public LiveData<List<TelNumber>> findTelNumberForContact(final String name) {
    final String _sql = "SELECT * FROM TelNumber WHERE contactNameNu=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"TelNumber"}, false, new Callable<List<TelNumber>>() {
      @Override
      public List<TelNumber> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfContactNameNu = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNameNu");
          final int _cursorIndexOfNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "number");
          final List<TelNumber> _result = new ArrayList<TelNumber>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TelNumber _item;
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpContactNameNu;
            _tmpContactNameNu = _cursor.getString(_cursorIndexOfContactNameNu);
            final String _tmpNumber;
            _tmpNumber = _cursor.getString(_cursorIndexOfNumber);
            _item = new TelNumber(_tmpNumber,_tmpContactNameNu,_tmpCategory);
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
}
