package com.example.contacts.Contact;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class Address {


    @PrimaryKey
    @NonNull
    public String street;
    public String city;
    @ColumnInfo(name = "code_postal") public int codePost;

    public Address(String street, String city, int codePost) {
        this.street = street;
        this.city = city;
        this.codePost = codePost;
    }

    public int getCodePost() {
        return codePost;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String toString() {
        return codePost+"";
    }


}
