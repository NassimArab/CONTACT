package com.example.contacts.Application;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.contacts.Application.ContactRoomDatabase;
import com.example.contacts.Contact.Contact;
import com.example.contacts.Contact.ContactDao;
import com.example.contacts.Group.Group;
import com.example.contacts.Group.GroupDao;
import com.example.contacts.GroupContactJoin.GroupContactJoin;
import com.example.contacts.GroupContactJoin.GroupContactJoinDao;
import com.example.contacts.TelNumber.TelNumber;
import com.example.contacts.TelNumber.TelNumberDao;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private GroupDao groupDao;
    private GroupContactJoinDao groupContactJoinDao;
    private TelNumberDao telNumberDao;

    private LiveData<List<Contact>> mAllContact;
    private LiveData<List<Group>> mAllGroup;
    private LiveData<List<TelNumber>> mTelNumber;
    //ajouté
    private LiveData<List<Contact>> mAllContactOfAgrp;

    private LiveData<List<GroupContactJoin>> mAllJoin;

    //ajouté pour la recuperation du nom du groupe choisit
    private String nomGroupe;

    public ContactRepository(Application application,String nom) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);

        //Une instance du Dao pour les contacts
        contactDao = db.contactDao();
        mAllContact = contactDao.getAllContacts();
        telNumberDao = db.telNumberDao();
        mTelNumber = telNumberDao.getAllTelNumber();



        /**
         * Ajouter dao de la joiture
         */
        //Une instance du Dao de la joiture
        groupContactJoinDao = db.groupContactJoinDao();

        /**
         *
         * RECUPERER LE VRAI NOM DU GROUPE ICI !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        //String name=null;
        mAllContactOfAgrp =groupContactJoinDao.getContactsOfGroupByName(nom);
        mAllJoin = groupContactJoinDao.getContactsOfGroup();


        //Une instance du Dao pour les groups
        groupDao = db.groupDao();
        mAllGroup = groupDao.getAllGroups();
    }

    public ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);

        //Une instance du Dao pour les contacts
        contactDao = db.contactDao();
        mAllContact = contactDao.getAllContacts();
        telNumberDao = db.telNumberDao();
        mTelNumber = db.telNumberDao().getAllTelNumber();




        /**
         * Ajouter dao de la joiture
         */
        //Une instance du Dao de la joiture
        groupContactJoinDao = db.groupContactJoinDao();

        /**
         *
         * RECUPERER LE VRAI NOM DU GROUPE ICI !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        //String name=null;
        mAllContactOfAgrp =groupContactJoinDao.getContactsOfGroupByName(this.getNomGroupe());
        mAllJoin = groupContactJoinDao.getContactsOfGroup();


        //Une instance du Dao pour les groups
        groupDao = db.groupDao();
        mAllGroup = groupDao.getAllGroups();
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mAllContact;
    }
    public LiveData<List<TelNumber>> getAllTelNumber() {
        return mTelNumber;
    }

    public LiveData<List<Group>> getmAllGroup() {
        return mAllGroup;
    }

    //Ajouté
    public LiveData<List<Contact>> getContactsOfGroup(String name) {
        //this.setNomGroupe(name);
        return groupContactJoinDao.getContactsOfGroupByName(name);
    }

    public LiveData<List<GroupContactJoin>> getContactsOfGroup() {
        return mAllJoin;
    }



    public void insert (Contact contact) {
        new insertAsyncTask(contactDao).execute(contact);
    }



    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;


        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Ajout de la classe delete AsyncTask   (supprime de la table de joiture )
     */
    public void delete (Contact contact) { new deleteAsyncTask(groupContactJoinDao).execute(contact.getName());}

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private GroupContactJoinDao jAsyncTaskDao;

        deleteAsyncTask(GroupContactJoinDao dao) {
            jAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            jAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    /**
     * Ajout de la classe delete AsyncTask   (supprime de la table de contact )
     */
    public void deleteContact (Contact contact) { new deleteContactAsyncTask(contactDao).execute(contact);}

    private static class deleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        deleteContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    /****    Fin de deleteAsyncTask    ****/

    public void insert (Group group) {
        new insertAsyncTaskGroup(groupDao).execute(group);
    }

    private static class insertAsyncTaskGroup extends AsyncTask<Group, Void, Void> {

        private GroupDao nAsyncTaskDao;

        insertAsyncTaskGroup(GroupDao dao) {
            nAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Group... params) {
            nAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



    /**
     * Ajoutée
     */
    private static class insertAsyncTaskGroupContactJoin extends AsyncTask<GroupContactJoin, Void, Void> {

        private GroupContactJoinDao jAsyncTaskDao;

        insertAsyncTaskGroupContactJoin(GroupContactJoinDao dao) {
            jAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final GroupContactJoin... params) {
            jAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (GroupContactJoin groupContactJoin) {
        new insertAsyncTaskGroupContactJoin(groupContactJoinDao).execute(groupContactJoin);
    }



    /*
     * ajouter à la table numero*/


    public void insert (TelNumber telNumber) {
        new insertAsyncTaskTelNumber(telNumberDao).execute(telNumber);
    }



    private static class insertAsyncTaskTelNumber extends AsyncTask<TelNumber, Void, Void> {

        private TelNumberDao mAsyncTaskDao;


        insertAsyncTaskTelNumber(TelNumberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TelNumber... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Ajout de la classe delete AsyncTask   (supprime de la table de contact )
     */
    public void deleteTelNumber (TelNumber telNumber) { new deleteTelNumberAsyncTask(telNumberDao).execute(telNumber);}

    private static class deleteTelNumberAsyncTask extends AsyncTask<TelNumber, Void, Void> {

        private TelNumberDao mAsyncTaskDao;

        deleteTelNumberAsyncTask(TelNumberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TelNumber... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    /****     delete Group****/

    public void deleteGroup (Group group) { new deleteGroupAsyncTask(groupDao).execute(group);}

    private static class deleteGroupAsyncTask extends AsyncTask<Group, Void, Void> {

        private GroupDao mAsyncTaskDao;

        deleteGroupAsyncTask(GroupDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Group... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    /**
     * Ajout de la classe delete AsyncTask   (supprime de la table de joiture )
     */
    public void deleteByG (Group group) { new deletebygAsyncTask(groupContactJoinDao).execute(group.getName());}

    private static class deletebygAsyncTask extends AsyncTask<String, Void, Void> {

        private GroupContactJoinDao jAsyncTaskDao;

        deletebygAsyncTask(GroupContactJoinDao dao) {
            jAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            jAsyncTaskDao.deleteByG(params[0]);
            return null;
        }
    }
    /**
     * Ajout de la classe delete AsyncTask   (supprime de la table de contact )
     */
    public void deleteTelNumberParCont (Contact contact) { new deleteTelNumberParContAsyncTask(telNumberDao).execute(contact);}

    private static class deleteTelNumberParContAsyncTask extends AsyncTask<Contact, Void, Void> {

        private TelNumberDao mAsyncTaskDao;

        deleteTelNumberParContAsyncTask(TelNumberDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.deleteNum(params[0].getName());
            return null;
        }
    }



}