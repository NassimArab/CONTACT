package com.example.contacts.Group;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.contacts.Group.Group;

import java.util.List;
@Dao
public interface GroupDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Group group);

    @Query("DELETE FROM group_table")
    void deleteAll();

    @Delete
    void delete(Group groupe);

    @Query("SELECT * from group_table ORDER BY name ASC")
    LiveData<List<Group>> getAllGroups();
}
