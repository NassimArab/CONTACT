package com.example.contacts.Contact;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.contacts.Contact.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)  // or OnConflictStrategy.IGNORE
    void insert(Contact contact);

/*
    @Query("DELETE FROM contact_table where contact_table.name =:name")
    void deleteContact(final String name);


    @Query("DELETE  FROM contact_table  " +
            "WHERE contact_table.name =:name")



    void deleteContact(final String name);
 */

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("SELECT * from contact_table ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();
}
