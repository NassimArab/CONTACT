package com.example.contacts.GroupContactJoin;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;


import com.example.contacts.Contact.Contact;
import com.example.contacts.GroupContactJoin.GroupContactJoin;

import java.util.List;

@Dao
public interface GroupContactJoinDao {
    @Insert
    void insert(GroupContactJoin groupContactJoin);
/*
    @Query("DELETE Group_Contact_join FROM contact_table " +
            "INNER JOIN Group_Contact_join" +
            "ON contact_table.name = Group_Contact_join.contactName " +
            "WHERE contact_table.name =:name")

*/

    @Query("DELETE  FROM Group_Contact_join  " +
            "WHERE Group_Contact_join.contactName IN " +
            "(SELECT name FROM contact_table  " +
            "WHERE contact_table.name =:name)")
    void delete(final String name);

  /*  @Query("SELECT * FROM group_table " +
            "INNER JOIN Group_Contact_join "+
            "ON group_table.name = Group_Contact_join.groupName " +
            "WHERE Group_Contact_join.groupName =:name ")
    LiveData<List<Group>> getGroupforContact(final String name);*/

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM contact_table " +
            "INNER JOIN Group_Contact_join "+
            "ON contact_table.name = Group_Contact_join.contactName " +
            "WHERE Group_Contact_join.groupName =:name")
    LiveData<List<Contact>> getContactsOfGroupByName(final String name);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM contact_table " +
            "INNER JOIN Group_Contact_join "+
            "ON contact_table.name = Group_Contact_join.contactName ")
    LiveData<List<GroupContactJoin>> getContactsOfGroup();

    @Query("DELETE FROM Group_Contact_join")
    void deleteAll();


    @Query("DELETE  FROM Group_Contact_join  " +
            "WHERE Group_Contact_join.groupName IN " +
            "(SELECT name FROM group_table  " +
            "WHERE group_table.name =:nameG)")
    void deleteByG(final String nameG);

    /*
     * select * from contact table where contact_table.name = Group_Contact_join.contactname AND Group_Contact_join.name =:name
     * */

}