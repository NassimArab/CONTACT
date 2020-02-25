package com.example.contacts.Group;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "group_table")
public class Group {

    @NonNull
    public String getName() {
        return name;
    }

    public Group(@NonNull String name) {
        this.name = name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @PrimaryKey
    @NonNull
    private String name;
}
