package com.example.contacts.Contact;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contacts.Application.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    public ContactRepository mRepository;
    public LiveData<List<Contact>> mAllContacts;

    public ContactViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    public void insert(Contact contact) {
        mRepository.insert(contact);
    }

    /**
     * Ajout de la methode de suppression d'un contact
     *
     *  public void delete(Contact contact){
     *         mRepository.delete(contact);
     *     }
     */



}