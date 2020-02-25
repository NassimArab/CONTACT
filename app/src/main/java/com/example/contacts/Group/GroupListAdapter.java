package com.example.contacts.Group;

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
import com.example.contacts.R;
import com.example.contacts.GroupContactJoin.displayContactsOfAGroup;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private Intent in;
    class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView groupNom;
        private RelativeLayout relativeGroup;



        private GroupViewHolder(View itemView) {
            super(itemView);
            groupNom = itemView.findViewById(R.id.nameGroup);
            relativeGroup = itemView.findViewById(R.id.RelativeGroup);

        }
    }

    private final LayoutInflater mInflater;
    private List<Group> mGroups; // Cached copy of groups

    public GroupListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_group, parent, false);
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {



        if (mGroups != null) {
            final Group current = mGroups.get(position);
            holder.groupNom.setText(current.getName());

            holder.relativeGroup.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent join = new Intent(mInflater.getContext(), displayContactsOfAGroup.class);
                    join.putExtra("Name_G",current.getName());
                    mInflater.getContext().startActivity(join);

                }
            });


            holder.relativeGroup.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    new AlertDialog.Builder(mInflater.getContext()).setIcon(android.R.drawable.ic_delete)
                            .setTitle("Voulez vous ")
                            .setMessage("supprimer ce groupe ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // current : contact courant
                                    ContactGroupViewModel viewModel = new ContactGroupViewModel(new Application());
                                    //ContactGroupViewModel viewModel = ViewModelProviders.of(mInflater.getContext().).get(ContactGroupViewModel.class);   //CHEEEEEEEECK !!!!!!!!
                                    // methode de suppression joiture
                                    viewModel.deletebyG(current);

                                    // methode de suppression de la table numero
                                    GroupViewModel groupViewModel = new GroupViewModel(new Application());
                                    groupViewModel.delete(current);


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
            holder.groupNom.setText("No Name");

        }

    }

    public void setWords(List<Group> groups){
        mGroups = groups;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mContacts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mGroups != null)
            return mGroups.size();
        else return 0;
    }


}

