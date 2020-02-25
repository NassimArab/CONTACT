package com.example.contacts.Contact;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.GroupContactJoin.ContactGroupViewModel;
import com.example.contacts.GroupContactJoin.GroupContactJoin;
import com.example.contacts.R;
import com.example.contacts.TelNumber.TelNumber;
import com.example.contacts.TelNumber.TelNumberViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView contactItemView;
        private final TextView numberItemView;
        //private final TextView streetItemView;
        //private final TextView cityItemView;
        // private final TextView cpItemView;
        //     private final TextView grouItemView;
        private final RelativeLayout relativeContact;
        private ContactViewHolder(View itemView) {
            super(itemView);
            contactItemView = itemView.findViewById(R.id.nameView);
            numberItemView = itemView.findViewById(R.id.numberView);
            //  streetItemView = itemView.findViewById(R.id.streetView);
            // cityItemView = itemView.findViewById(R.id.cityView);
            // cpItemView = itemView.findViewById(R.id.cpView);
            //  grouItemView = itemView.findViewById(R.id.grView);
            relativeContact = itemView.findViewById(R.id.RelativeContact);

        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> mContacts; // Cached copy of contacts
    private List<GroupContactJoin> Join;
    private List<TelNumber> Number;
    private List<String> Categories;

    public ContactListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        if (mContacts != null) {

            final Contact current = mContacts.get(position);
            holder.contactItemView.setText(current.getName());

            String listeN = "";
            for (TelNumber tn : Number ){
                if(current.getName().equals(tn.getContactNameNu())){
                    listeN += tn.getNumber() + " \n";
                }

            }

            holder.numberItemView.setText(listeN);

            //holder.streetItemView.setText(current.getAddress().getStreet());
            // holder.cityItemView.setText(current.getAddress().getCity());
            // holder.cpItemView.setText(current.getAddress().toString());

          /*  if(!Join.isEmpty()) {

                for (GroupContactJoin j : Join) {
                    if (j.getContactName().equals(current.getName()))
                        holder.grouItemView.setText(j.getGroupName());
                }

            }else{
                holder.grouItemView.setText("Sans Groupe");
            }
*/

            holder.relativeContact.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent joinIn = new Intent(mInflater.getContext(), DetailContact.class);
                    joinIn.putExtra("Detail",true);
                    joinIn.putExtra("Contact_Name",current.getName());
                    //joinIn.putExtra("Contact_Numbre",current.getNumber().getNumber());

                    joinIn.putExtra("Contact_Category",current.getName());
                    joinIn.putExtra("Contact_Street",current.getAddress().getStreet());
                    joinIn.putExtra("Contact_City",current.getAddress().getCity());
                    joinIn.putExtra("Contact_CP",""+current.getAddress().getCodePost());


                    /*
                     * Many Groups
                     * */
                    List<String> groupOfContact = new ArrayList<String>();
                    if(!Join.isEmpty()) {
                        //recuperer la liste des groupes et les envoient vers detail view pour les afficher
                        for (GroupContactJoin j : Join) {
                            if (j.getContactName().equals(current.getName()))

                                groupOfContact.add(j.getGroupName());
                        }

                        String[] tab = new String[groupOfContact.size()];
                        for (int m = 0; m<tab.length;m++){
                            tab[m] = groupOfContact.get(m);
                        }
                        joinIn.putExtra("Contact_Groups",tab);

                    }

                    /*
                     * Many Number
                     * */

                    List<String> NumberOfContact = new ArrayList<String>();
                    List<String> CategoryOfContact = new ArrayList<String>();
                    if(!Number.isEmpty()) {
                        //recuperer la liste des groupes et les envoient vers detail view pour les afficher
                        for (TelNumber n : Number) {
                            if (n.getContactNameNu().equals(current.getName())){
                                NumberOfContact.add(n.getNumber());
                                CategoryOfContact.add(n.getCategory());}

                        }

                        String[] tabN = new String[NumberOfContact.size()];
                        String[] tabC = new String[CategoryOfContact.size()];
                        for (int m = 0; m<tabN.length;m++){
                            tabN[m] = NumberOfContact.get(m);
                            tabC[m] = CategoryOfContact.get(m);
                        }
                        joinIn.putExtra("Contact_Numbers",tabN);
                        joinIn.putExtra("Category_Numbers",tabC);

                    }



                    mInflater.getContext().startActivity(joinIn);




                }
            });

            holder.relativeContact.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    new AlertDialog.Builder(mInflater.getContext()).setIcon(android.R.drawable.ic_delete)
                            .setTitle("Voulez vous ")
                            .setMessage("supprimer ce contact ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // current : contact courant
                                    ContactGroupViewModel viewModel = new ContactGroupViewModel(new Application());
                                    //ContactGroupViewModel viewModel = ViewModelProviders.of(mInflater.getContext().).get(ContactGroupViewModel.class);   //CHEEEEEEEECK !!!!!!!!
                                    // methode de suppression joiture
                                    viewModel.delete(current);
                                    // methode de suppression de la table contact
                                    ContactGroupViewModel contactViewModel = new ContactGroupViewModel(new Application());
                                    contactViewModel.deleteContact(current);

                                    TelNumberViewModel numeroViewModel = new TelNumberViewModel(new Application());
                                    numeroViewModel.deleteCon(current);


                                    /*
                                    *  //ajouter la pour supprimer un contact avec son numero dans la table TelNumber
                                    *
                                    * */

                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Non",null)
                            .show();
                    return true;
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.contactItemView.setText("No Name");
            holder.numberItemView.setText("No Number");
            //  holder.streetItemView.setText("No Street");
            //   holder.cityItemView.setText("No City");
            //   holder.cpItemView.setText("No CP");
            //   holder.grouItemView.setText("No Groupe");
        }
    }

    public void setContact(List<Contact> Contacts){
        mContacts = Contacts;
        notifyDataSetChanged();
    }

    public void setJoin(List<GroupContactJoin> join){
        Join = join;
        notifyDataSetChanged();
    }

    public void setNumber(List<TelNumber> numbers){
        Number = numbers;
        notifyDataSetChanged();
    }
    // getItemCount() is called many times, and when it is first called,
    // mContacts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mContacts != null)
            return mContacts.size();
        else return 0;
    }


}