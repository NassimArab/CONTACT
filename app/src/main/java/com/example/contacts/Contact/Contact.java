package com.example.contacts.Contact;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@Entity(tableName = "contact_table")

public class Contact {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;


    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Embedded public Address address;

    public Contact(@NonNull String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String getName(){return this.name;}




}