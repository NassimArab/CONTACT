package com.example.contacts.Contact;


import android.content.Intent;
import android.os.Bundle;
import com.example.contacts.GroupContactJoin.ContactGroupViewModel;
import com.example.contacts.Group.Group;
import com.example.contacts.GroupContactJoin.GroupContactJoin;
import com.example.contacts.Group.GroupViewModel;
import com.example.contacts.R;
import com.example.contacts.TelNumber.TelNumber;
import com.example.contacts.TelNumber.TelNumberViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;


public class AddContact extends AppCompatActivity  {
    private ContactViewModel contactViewModel;
    private ContactGroupViewModel contactGroupViewModel;
    private GroupViewModel groupViewModel;
    public TelNumberViewModel telNumberViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddContact.this, NewContactActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerviewC);
        final ContactListAdapter adapter = new ContactListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setContact(words);
            }
        });

        telNumberViewModel = ViewModelProviders.of(this).get(TelNumberViewModel.class);

        telNumberViewModel.getAllTelNumber().observe(this, new Observer<List<TelNumber>>() {
            @Override
            public void onChanged(@Nullable final List<TelNumber> numb) {
                // Update the cached copy of the words in the adapter.
                adapter.setNumber(numb);
            }
        });


        groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);


        contactGroupViewModel = ViewModelProviders.of(this).get(ContactGroupViewModel.class);

        contactGroupViewModel.getmAllContactsOfAJoin().observe(this, new Observer<List<GroupContactJoin>>() {
            @Override
            public void onChanged(@Nullable final List<GroupContactJoin> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setJoin(words);
            }
        });








        //Spinner !
/*
        Spinner sp = findViewById(R.id.spinnerR);
        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this,R.array.Groupe,android.R.layout.simple_spinner_item);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adap);
        sp.setOnItemSelectedListener(this);*/
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

            Contact word = new Contact(data.getStringExtra("name"), adr);


            contactViewModel.insert(word);




            /*
             * insert Group*/

            String [] listeN = data.getStringArrayExtra("numberListe");
            String [] listeC = data.getStringArrayExtra("categoriesListe");
            for (int i =0; i<listeN.length;i++ ){

                TelNumber num = new TelNumber(listeN[i], data.getStringExtra("name") , listeC[i]);
                telNumberViewModel.insert(num);

            }

            /*
             * insert jointeur*/

            String [] listeG = data.getStringArrayExtra("groupliste");


            for (int i =0; i<listeG.length;i++ ){

                Group jwordGr = new Group(listeG[i]);
                GroupContactJoin groupContactJoin = new GroupContactJoin(jwordGr.getName(),word.getName());
                contactGroupViewModel.insert(groupContactJoin);

            }



        } else {
            Toast.makeText(
                    getApplicationContext(),

                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }









    }


/*


    //Spinner Methods onItemSelected / onNothingSelected
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();

        if (text.equals("Ajouter")){
            Intent groupeIntent = new Intent(this,GroupeActivity.class);
            startActivity(groupeIntent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}