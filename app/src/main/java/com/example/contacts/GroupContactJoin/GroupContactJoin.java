package com.example.contacts.GroupContactJoin;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.example.contacts.Contact.Contact;
import com.example.contacts.Group.Group;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Group_Contact_join",
        primaryKeys =  {"groupName","contactName"},
        foreignKeys = {
                @ForeignKey(entity = Contact.class,
                        parentColumns = "name",
                        childColumns = "contactName",
                        onDelete = CASCADE),

                @ForeignKey(entity = Group.class,
                        parentColumns = "name",
                        childColumns = "groupName",
                        onDelete = CASCADE)
        }
        ,  indices = {@Index(value = {"contactName"})})

public class GroupContactJoin {


    @NonNull
    public String groupName;
    @NonNull
    public  String contactName;


    public GroupContactJoin(String groupName, String contactName) {
        this.groupName = groupName;
        this.contactName = contactName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


}