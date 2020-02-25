package com.example.contacts.Application;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.contacts.Contact.*;
import com.example.contacts.Contact.ContactDao;
import com.example.contacts.Group.Group;
import com.example.contacts.Group.GroupDao;
import com.example.contacts.GroupContactJoin.GroupContactJoin;
import com.example.contacts.GroupContactJoin.GroupContactJoinDao;
import com.example.contacts.TelNumber.TelNumber;
import com.example.contacts.TelNumber.TelNumberDao;


@Database(entities = {Contact.class, Group.class, GroupContactJoin.class, TelNumber.class}, version = 10 , exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract GroupDao groupDao();
    public abstract GroupContactJoinDao groupContactJoinDao();
    public abstract TelNumberDao telNumberDao();

    private static volatile ContactRoomDatabase INSTANCE;

    static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class, "contact_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        /**
         * des objets pour les differents DAO
         */
        private final ContactDao mDao;
        private final GroupDao nDao;
        private final GroupContactJoinDao joinDao;
        private final TelNumberDao numberDao;


        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
            nDao = db.groupDao();
            joinDao = db.groupContactJoinDao();
            numberDao = db.telNumberDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            mDao.deleteAll();
            nDao.deleteAll();
            joinDao.deleteAll();
            numberDao.deleteAll();

            //Address adr = new Address ("15 rue gantois", "Lille", 59000);
            Address adr2 = new Address ("Paul langevin", "Lille", 59650);
            TelNumber num2 = new TelNumber("0555555555","Nassim","Fix");
            TelNumber num3 = new TelNumber("0666666666","Nassim","Fix");

            Contact Nassim = new Contact("Nassim", adr2);
            mDao.insert(Nassim);

            numberDao.insert(num2);
            numberDao.insert(num3);


            //TelNumber num = new TelNumber("066666666","Fix");
            //Contact contact = new Contact("Lina", adr);
            //mDao.insert(contact);

            //contact = new Contact("Mohamed",adr);
            //mDao.insert(contact);
            Group groupF =new Group("Famille");
            Group groupG = new Group("Amis");
            nDao.insert(groupF);
            nDao.insert(groupG);
            GroupContactJoin nas = new GroupContactJoin("Famille","Nassim");
            GroupContactJoin nass = new GroupContactJoin("Amis","Nassim");
            joinDao.insert(nas);
            joinDao.insert(nass);
            return null;
        }
    }

}