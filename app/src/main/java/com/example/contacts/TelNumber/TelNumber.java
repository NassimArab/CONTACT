package com.example.contacts.TelNumber;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.contacts.Contact.Contact;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Contact.class,
        parentColumns = "name",
        childColumns = "contactNameNu",
        onDelete = CASCADE),
        indices = {@Index(value = {"contactNameNu"})})
public class TelNumber {

    public String category;
    public String contactNameNu;

    @PrimaryKey
    @NonNull
    public String number;




    public  TelNumber( String number,String contactNameNu, String category)  {
        this.number = number;
        this.category = category;
        this.contactNameNu = contactNameNu;
    }

    public String getContactNameNu() {
        return contactNameNu;
    }

    public String getNumber() {
        return number;
    }

    public String getCategory() {
        return category;
    }
}