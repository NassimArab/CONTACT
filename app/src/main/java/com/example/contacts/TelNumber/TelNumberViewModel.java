package com.example.contacts.TelNumber;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contacts.Application.ContactRepository;
import com.example.contacts.Contact.Contact;

import java.util.List;

public class TelNumberViewModel  extends AndroidViewModel {
    private ContactRepository mRepository;
    public LiveData<List<TelNumber>> mTelNumber;

    public TelNumberViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mTelNumber = mRepository.getAllTelNumber();
    }

    public LiveData<List<TelNumber>> getAllTelNumber() {
        return mTelNumber;
    }

    public void insert(TelNumber telNumber) {
        mRepository.insert(telNumber);
    }
    public void deleteCon(Contact contact) {
        mRepository.deleteTelNumberParCont(contact);
    }
}