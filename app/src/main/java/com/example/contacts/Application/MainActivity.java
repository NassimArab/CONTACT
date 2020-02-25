package com.example.contacts.Application;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contacts.Contact.AddContact;
import com.example.contacts.Group.displayGroup;
import com.example.contacts.R;


public class MainActivity extends AppCompatActivity{


    private Button contact ;
    private Button group ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }

    public void accContact (View view ){
        Intent contactIntent = new Intent(this, AddContact.class);
        startActivity(contactIntent);
    }

    public void accGroup (View view ){
        Intent groupIntent = new Intent(this, displayGroup.class);
        startActivity(groupIntent);
    }




}
