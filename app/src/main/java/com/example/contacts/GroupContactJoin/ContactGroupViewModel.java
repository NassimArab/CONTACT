package com.example.contacts.GroupContactJoin;



import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.contacts.Group.Group;
import com.example.contacts.Contact.Contact;
import com.example.contacts.Application.ContactRepository;
import com.example.contacts.TelNumber.TelNumber;

import java.util.List;

public class ContactGroupViewModel extends AndroidViewModel{
    private ContactRepository mRepository;
    private LiveData<List<Contact>> mAllContactsofAGroup;
    private LiveData<List<GroupContactJoin>> mAllContactsofAJoin;
    private String nomGrp;


    public ContactGroupViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application,nomGrp);
        /**
         * to chage after with real name
         */
        //String name="Lina";
        mAllContactsofAGroup = mRepository.getContactsOfGroup(nomGrp);
        mAllContactsofAJoin = mRepository.getContactsOfGroup();
    }

    public void setNomGrp(String nomGrp) {
        this.nomGrp = nomGrp;
    }

    public LiveData<List<Contact>> getmAllContactsOfAGroup(String name) {
        //this.nomGrp=name;
        //setNomGrp(name);
        return mRepository.getContactsOfGroup(name);
    }

    public LiveData<List<GroupContactJoin>> getmAllContactsOfAJoin() {
        return mAllContactsofAJoin;
    }
    /**
     * should we keep the same insert , and not use a insert og Group contact !!!!!!!!!!!!!!!!!!!
     * @param groupContactJoin
     */
    public void insert(GroupContactJoin groupContactJoin) {
        mRepository.insert(groupContactJoin);
    }


    /**
     * la methode de suppression d'un contact de la table de joiture
     * @param contact
     */
    public void delete(Contact contact ) {

        mRepository.delete(contact);
    }

    public void deleteContact(Contact contact ) {
        mRepository.deleteContact(contact);
    }

    public void deleteTelNumber(TelNumber telNumber ) {
        mRepository.deleteTelNumber(telNumber);
    }

    public void deletebyG(Group group ) {   mRepository.deleteByG(group);
    }



}