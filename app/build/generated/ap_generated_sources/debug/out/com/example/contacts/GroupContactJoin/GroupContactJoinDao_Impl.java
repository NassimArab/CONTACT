package com.example.contacts.GroupContactJoin;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.contacts.Contact.Address;
import com.example.contacts.Contact.Contact;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class GroupContactJoinDao_Impl implements GroupContactJoinDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GroupContactJoin> __insertionAdapterOfGroupContactJoin;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByG;

  public GroupContactJoinDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGroupContactJoin = new EntityInsertionAdapter<GroupContactJoin>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Group_Contact_join` (`groupName`,`contactName`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GroupContactJoin value) {
        if (value.groupName == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.groupName);
        }
        if (value.contactName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.contactName);
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM Group_Contact_join  WHERE Group_Contact_join.contactName IN (SELECT name FROM contact_table  WHERE contact_table.name =?)";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Group_Contact_join";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByG = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  FROM Group_Contact_join  WHERE Group_Contact_join.groupName IN (SELECT name FROM group_table  WHERE group_table.name =?)";
        return _query;
      }
    };
  }

  @Override
  public void insert(final GroupContactJoin groupContactJoin) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfGroupContactJoin.insert(groupContactJoin);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final String name) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
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
      __preparedStmtOfDelete.release(_stmt);
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
  public void deleteByG(final String nameG) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByG.acquire();
    int _argIndex = 1;
    if (nameG == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, nameG);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByG.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Contact>> getContactsOfGroupByName(final String name) {
    final String _sql = "SELECT * FROM contact_table INNER JOIN Group_Contact_join ON contact_table.name = Group_Contact_join.contactName WHERE Group_Contact_join.groupName =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"contact_table","Group_Contact_join"}, false, new Callable<List<Contact>>() {
      @Override
      public List<Contact> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStreet = CursorUtil.getColumnIndexOrThrow(_cursor, "street");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfCodePost = CursorUtil.getColumnIndexOrThrow(_cursor, "code_postal");
          final List<Contact> _result = new ArrayList<Contact>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Contact _item;
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Address _tmpAddress;
            if (! (_cursor.isNull(_cursorIndexOfStreet) && _cursor.isNull(_cursorIndexOfCity) && _cursor.isNull(_cursorIndexOfCodePost))) {
              final String _tmpStreet;
              _tmpStreet = _cursor.getString(_cursorIndexOfStreet);
              final String _tmpCity;
              _tmpCity = _cursor.getString(_cursorIndexOfCity);
              final int _tmpCodePost;
              _tmpCodePost = _cursor.getInt(_cursorIndexOfCodePost);
              _tmpAddress = new Address(_tmpStreet,_tmpCity,_tmpCodePost);
            }  else  {
              _tmpAddress = null;
            }
            _item = new Contact(_tmpName,_tmpAddress);
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
  public LiveData<List<GroupContactJoin>> getContactsOfGroup() {
    final String _sql = "SELECT * FROM contact_table INNER JOIN Group_Contact_join ON contact_table.name = Group_Contact_join.contactName ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"contact_table","Group_Contact_join"}, false, new Callable<List<GroupContactJoin>>() {
      @Override
      public List<GroupContactJoin> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGroupName = CursorUtil.getColumnIndexOrThrow(_cursor, "groupName");
          final int _cursorIndexOfContactName = CursorUtil.getColumnIndexOrThrow(_cursor, "contactName");
          final List<GroupContactJoin> _result = new ArrayList<GroupContactJoin>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final GroupContactJoin _item;
            final String _tmpGroupName;
            _tmpGroupName = _cursor.getString(_cursorIndexOfGroupName);
            final String _tmpContactName;
            _tmpContactName = _cursor.getString(_cursorIndexOfContactName);
            _item = new GroupContactJoin(_tmpGroupName,_tmpContactName);
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
