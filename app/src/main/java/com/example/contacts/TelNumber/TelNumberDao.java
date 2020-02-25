package com.example.contacts.TelNumber;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.contacts.TelNumber.TelNumber;

import java.util.List;

@Dao
public interface TelNumberDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(TelNumber number);

    @Update
    void update(TelNumber... number);

    @Delete
    void delete(TelNumber... number);

    @Query("DELETE FROM TelNumber")
    void deleteAll();

    @Query("SELECT * FROM TelNumber")
    LiveData<List<TelNumber>> getAllTelNumber();

    @Query("SELECT * FROM TelNumber WHERE contactNameNu=:name")
    LiveData<List<TelNumber>> findTelNumberForContact(final String name);


    @Query("DELETE FROM TelNumber WHERE contactNameNu=:name")
    void deleteNum(final String name);


}