package com.example.contacts.Group;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contacts.Application.ContactRepository;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {

    private ContactRepository mRepository;
    private LiveData<List<Group>> mAllGroups;

    public GroupViewModel (Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllGroups = mRepository.getmAllGroup();
    }

    public LiveData<List<Group>> getAllGroups() {
        return mAllGroups;
    }

    public void insert(Group group) {
        mRepository.insert(group);
    }
    public void delete(Group group) {
        mRepository.deleteGroup(group);
    }
}
