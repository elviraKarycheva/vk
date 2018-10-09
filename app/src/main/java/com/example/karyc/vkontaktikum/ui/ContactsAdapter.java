package com.example.karyc.vkontaktikum.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.Groups;
import com.example.karyc.vkontaktikum.databinding.ViewFriendsContactsItemBinding;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ContactsActivity contactsActivity;
    private final ArrayList<Contact> contacts = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)
                parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewFriendsContactsItemBinding binding = ViewFriendsContactsItemBinding.inflate(inflater);
        return new ContactsHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContactsHolder contactsHolder = (ContactsHolder) holder;
        contactsHolder.binding.setItem(contacts.get(position));
        contactsHolder.binding.executePendingBindings();
    }

    public void setContacts(ArrayList<Contact> contactArrayList) {
        contacts.clear();
        contacts.addAll(contactArrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsHolder extends RecyclerView.ViewHolder {
        ViewFriendsContactsItemBinding binding;

        public ContactsHolder(View itemView, ViewFriendsContactsItemBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}


