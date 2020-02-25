package com.example.contacts.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.contacts.R;

public class DetailContact extends AppCompatActivity {
        private  TextView nameDet;
        private  TextView numberDet;
        private  TextView streetDet;
        private  TextView cityDet;
        private  TextView cpDet;
        private  TextView grouDet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        nameDet = findViewById(R.id.nameDet);
        numberDet = findViewById(R.id.NumeroDet);
        streetDet = findViewById(R.id.StreetDet);
        cityDet = findViewById(R.id.CityDet);
        cpDet = findViewById(R.id.CPDet);
        grouDet = findViewById(R.id.GroupDet);

        Intent intent = getIntent();

        String n = "erreur 404";
        if(intent.hasExtra("Detail")){
            nameDet.setText(intent.getStringExtra("Contact_Name"));

            String champeNumber = " \n ";
            String[] ListeNumber = intent.getStringArrayExtra("Contact_Numbers");
            String[] ListeCategory = intent.getStringArrayExtra("Category_Numbers");
        for(int i = 0; i<ListeNumber.length;i++){
                champeNumber += i+ " : " + ListeNumber[i] + " : " + ListeCategory[i] + " \n ";
            }

            numberDet.setText(champeNumber);

            streetDet.setText(intent.getStringExtra("Contact_Street"));
            cityDet.setText(intent.getStringExtra("Contact_City"));
            cpDet.setText(intent.getStringExtra("Contact_CP"));
            // grouDet.setText(intent.getStringExtra("Contact_Groups"));
            String champeGroup = "";

            String[] ListeGroup = intent.getStringArrayExtra("Contact_Groups");
            if(ListeGroup != null) {
                for (int i = 0; i < ListeGroup.length; i++) {
                    champeGroup += ListeGroup[i] + " \n ";
                }
            }

            grouDet.setText(champeGroup);

        }else{
            nameDet.setText(n);
        }

    }
}