package com.example.contacts.GroupContactJoin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contacts.R;

public class ContactsOfAGroupActivity extends AppCompatActivity {
    private TextView grp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_group);

        //récuperer le nom du groupe choisit
        grp = findViewById(R.id.nameGroup);


    }


    public void affectedContactsDuGr(View view){


        Intent intent = new Intent();

        if (TextUtils.isEmpty(grp.getText())) {

            setResult(RESULT_CANCELED, intent);
            //setResult(RESULT_CANCELED, nameIntent);

        } else {


            String nomGroup = grp.getText().toString();

            intent.putExtra("grpChoisit", nomGroup);

            setResult(RESULT_OK, intent);
            //setResult(RESULT_OK, nameIntent);
            /**
             *
             * a verifier
             */
            //startActivity(intent);
        }
        finish();

    }
}