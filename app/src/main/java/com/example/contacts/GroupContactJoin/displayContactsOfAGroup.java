package com.example.contacts.GroupContactJoin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts.Contact.Address;
import com.example.contacts.Contact.Contact;
import com.example.contacts.Contact.ContactViewModel;
import com.example.contacts.Group.Group;
import com.example.contacts.Group.GroupViewModel;
import com.example.contacts.R;
import com.example.contacts.TelNumber.TelNumber;
import com.example.contacts.TelNumber.TelNumberViewModel;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;




public class displayContactsOfAGroup extends AppCompatActivity  {
    private ContactViewModel contactViewModel;
    private ContactGroupViewModel contactGroupViewModel;
    private GroupViewModel groupViewModel;
    private TelNumberViewModel telNumberViewModel;
    private  TextView grouTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contactsofgroup);



        RecyclerView recyclerView   = findViewById(R.id.recyclerviewC);
        final ContactsOfAGroupAdapter adapter = new ContactsOfAGroupAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        grouTitle = findViewById(R.id.groupName);
        // contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        contactGroupViewModel = ViewModelProviders.of(this).get(ContactGroupViewModel.class);

        grouTitle.setText(getIntent().getStringExtra("Name_G"));
        contactGroupViewModel.getmAllContactsOfAGroup(getIntent().getStringExtra("Name_G")).observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> words) {
                // Update the cached copy of the words in the adapter.
                if(words.isEmpty()){
                    Toast.makeText(
                            getApplicationContext(),

                            "la liste est vide ",
                            Toast.LENGTH_LONG).show();
                }
                adapter.setContact(words);
            }
        });

        telNumberViewModel= ViewModelProviders.of(this).get(TelNumberViewModel.class);
        telNumberViewModel.getAllTelNumber().observe(this, new Observer<List<TelNumber>>() {
            @Override
            public void onChanged(@Nullable final List<TelNumber> numb) {
                // Update the cached copy of the words in the adapter.
                adapter.setNumber(numb);
            }
        });


        groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);


        contactGroupViewModel.getmAllContactsOfAJoin().observe(this, new Observer<List<GroupContactJoin>>() {
            @Override
            public void onChanged(@Nullable final List<GroupContactJoin> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setJoin(words);
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            //Contact word = new Contact(data.getStringExtra(NewContactActivity.EXTRA_REPLY),data.getStringExtra(NewContactActivity.EXTRA_REPLY));
            //valeur par defaut a verifier ******************
            /*
             * insert Contact*/
            Address adr = new Address(data.getStringExtra("street"),data.getStringExtra("city"),data.getIntExtra("codePost",0));
            TelNumber num = new TelNumber(data.getStringExtra("number"), data.getStringExtra("name") ,/*data.getStringExtra("category")*/ "category");
            Contact word = new Contact(data.getStringExtra("name"), adr);
            contactViewModel.insert(word);




            /*
             * insert Group*/


            Group wordGr = new Group(data.getStringExtra("group"));

            Toast.makeText(
                    getApplicationContext(),

                    wordGr.getName(),
                    Toast.LENGTH_LONG).show();

            groupViewModel.insert(wordGr);


            /*
             * insert jointeur*/

            GroupContactJoin groupContactJoin = new GroupContactJoin(wordGr.getName(),word.getName());
            contactGroupViewModel.insert(groupContactJoin);



        } else {
            Toast.makeText(
                    getApplicationContext(),

                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }









    }


}