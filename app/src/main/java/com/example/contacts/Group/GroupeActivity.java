package com.example.contacts.Group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.contacts.R;

public class GroupeActivity extends AppCompatActivity {

    private Button bt1 ;
    private EditText inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);
        inp = findViewById(R.id.edit_name_group);


    }



    public void ajouter(View view){



    //


        Intent groupIntent = new Intent();
        //Intent nameIntent = new Intent();

        if (TextUtils.isEmpty(inp.getText())) {


            setResult(RESULT_CANCELED, groupIntent);
            //setResult(RESULT_CANCELED, nameIntent);

        } else {


            String newGroup = inp.getText().toString();

            groupIntent.putExtra("name", newGroup);






            setResult(RESULT_OK, groupIntent);
            //setResult(RESULT_OK, nameIntent);

        }
        finish();

    }
}
