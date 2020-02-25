package com.example.contacts.Contact;

import android.content.Intent;

import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.text.TextUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.contacts.Group.Group;
import com.example.contacts.Group.GroupListAdapter;
import com.example.contacts.Group.GroupViewModel;
import com.example.contacts.R;

import java.util.ArrayList;
import java.util.List;

public class NewContactActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditContactView;
    private EditText mEditNumberView;
    private EditText mEditCategoryView;
    private EditText mEditstreetView;
    private EditText mEditcpView;
    private EditText mEditcityView;
    private String mGroup;
    private String mNumber;
    private String mCategory;
    private ArrayList<String> listeG;
    private ArrayList<String> listeN;
    private ArrayList<String> listeC;
    private TextView Groupe_existe;
    private TextView Contact_existe;
    private Button addGroup ;
    private Button addNumber ;

    private Spinner sp;
    private GroupViewModel groupViewModel;



    public void addGroupButt(View view){

        if(!listeG.contains(mGroup)) {
            listeG.add(mGroup);
            Toast.makeText(
                    getApplicationContext(),

                    mGroup + " est bien ajouté ",
                    Toast.LENGTH_LONG).show();
            Groupe_existe.setText(listeG.toString());

        }else{
            Toast.makeText(
                    getApplicationContext(),

                    mGroup + " Existe déja ",
                    Toast.LENGTH_LONG).show();

        }
    }

        /*
        * Sauvegarder pendant la rotation de l'apllication*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("listeG",listeG);
        outState.putStringArrayList("listeN",listeN);
        outState.putStringArrayList("listeC",listeC);
    }

    public void addNumberButt(View view){

        mNumber = mEditNumberView.getText().toString();
        mCategory = mEditCategoryView.getText().toString();
        if(!listeN.contains(mNumber)) {

            if(mCategory.isEmpty()){
                listeC.add("No Category");}
            else{
                listeC.add(mCategory);}


            listeN.add(mNumber);
            Toast.makeText(
                    getApplicationContext(),

                    mNumber + " Add success ",
                    Toast.LENGTH_LONG).show();
            Contact_existe.setText("Numbers : " + listeN.toString()+ "\n ____ \n Categories : " + listeC.toString());

        }else{
            Toast.makeText(
                    getApplicationContext(),

                    mNumber + " Existe déja ",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mGroup = adapterView.getItemAtPosition(i).toString();



    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mGroup = "rien";
    }


    private void createSpinners(ArrayList<String> listToUse) {
        ArrayAdapter<String> adp1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listToUse);
        sp.setAdapter(adp1);

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        mEditContactView = findViewById(R.id.edit_contact);
        mEditNumberView = findViewById(R.id.edit_number);
        mEditCategoryView = findViewById(R.id.edit_Category);
        mEditstreetView = findViewById(R.id.edit_street);
        mEditcpView = findViewById(R.id.edit_cp);
        mEditcityView = findViewById(R.id.edit_city);
        addGroup = findViewById(R.id.group_add);
        addNumber = findViewById(R.id.number_add);
        Groupe_existe = findViewById(R.id.group_existe);
        Contact_existe = findViewById(R.id.contact_existe);

        listeG = new ArrayList<String>();
        listeN = new ArrayList<String>();
        listeC = new ArrayList<String>();
        //Spinner !
        //GroupDao group




        sp=  (Spinner) findViewById(R.id.spinnerR);
            sp.setPrompt("Choisissez le groupe: ");
        final ArrayList<String> groupName = new ArrayList<>();

        final GroupListAdapter adapter = new GroupListAdapter(this);

        groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
       groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable final List<Group> words) {
                // Update the cached copy of the words in the adapter.

                adapter.setWords(words);


                for(Group g : words ){
                    groupName.add(g.getName());

                }

                createSpinners(groupName);
            }

        });
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,groupName);//ArrayAdapter.createFromResource(this,R.array.Groupe,android.R.layout.simple_spinner_item);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adap);
        sp.setOnItemSelectedListener(this);




        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent contactIntent = new Intent();
                //Intent nameIntent = new Intent();

                if (TextUtils.isEmpty(mEditContactView.getText()) && TextUtils.isEmpty(mEditNumberView.getText())) {
                    setResult(RESULT_CANCELED, contactIntent);
                    //setResult(RESULT_CANCELED, nameIntent);

                } else {



                    String word = mEditContactView.getText().toString();
                    String street = mEditstreetView.getText().toString();
                    String city = mEditcityView.getText().toString();
                    int cp = Integer.parseInt(mEditcpView.getText().toString());

                    //add





                    contactIntent.putExtra("name", word);


                    String[] tabN=new  String[listeN.size()];
                    String[] tabC=new  String[listeC.size()];

                    for(int i=0;i<tabN.length;i++){
                        tabN[i]=listeN.get(i);
                        tabC[i]=listeC.get(i);
                    }

                    contactIntent.putExtra("numberListe", tabN);
                    contactIntent.putExtra("categoriesListe", tabC);



                    String[] tab=new  String[listeG.size()];
                    for(int i=0;i<tab.length;i++){
                        tab[i]=listeG.get(i);
                    }
                    contactIntent.putExtra("groupliste", tab);
                    contactIntent.putExtra("street", street);
                    contactIntent.putExtra("city", city);
                    contactIntent.putExtra("codePost", cp);






                    setResult(RESULT_OK, contactIntent);


                }
                finish();
            }
        });




        //rotation

        if(savedInstanceState != null){
            listeG = savedInstanceState.getStringArrayList("listeG");
            listeN = savedInstanceState.getStringArrayList("listeN");
            listeC = savedInstanceState.getStringArrayList("listeC");
            Groupe_existe.setText(listeG.toString());
            Contact_existe.setText("Numbers : " + listeN.toString()+ "\n ____ \n  Categories : " + listeC.toString());
        }



    }





}