package com.example.contacts.GroupContactJoin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.Contact.Contact;
import com.example.contacts.R;
import com.example.contacts.TelNumber.TelNumber;

import java.util.ArrayList;
import java.util.List;

public class ContactsOfAGroupAdapter extends RecyclerView.Adapter<ContactsOfAGroupAdapter.ContactOfGroupViewHolder> {

    String groupChoix;
    class ContactOfGroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView contactItemView;
        private final TextView numberItemView;




        private ContactOfGroupViewHolder(View itemView) {
            super(itemView);
            contactItemView = itemView.findViewById(R.id.nameView);
            numberItemView = itemView.findViewById(R.id.numberView);


        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> mContacts; // Cached copy of contacts
    private List<GroupContactJoin> Join;
    private List<TelNumber> Number;

    public ContactsOfAGroupAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ContactOfGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ContactOfGroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactOfGroupViewHolder holder, int position) {

        if (mContacts != null) {

            Contact current = mContacts.get(position);
            holder.contactItemView.setText(current.getName());
            //holder.numberItemView.setText(current.getNumber().getNumber());


            /*
             * Many Number
             * */

            List<String> NumberOfContact = new ArrayList<String>();
            if(!Number.isEmpty()) {
                //recuperer la liste des groupes et les envoient vers detail view pour les afficher
                for (TelNumber n : Number) {
                    if (n.getContactNameNu().equals(current.getName()))

                        NumberOfContact.add(n.getNumber());
                }

                String champeNumbre = "";
                for (int m = 0; m<NumberOfContact.size();m++){
                    champeNumbre = NumberOfContact.get(m) + "\n";
                }

                holder.numberItemView.setText(champeNumbre);
            }




        } else {
            // Covers the case of data not being ready yet.
            holder.contactItemView.setText("No Name");
            holder.numberItemView.setText("No Number");

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