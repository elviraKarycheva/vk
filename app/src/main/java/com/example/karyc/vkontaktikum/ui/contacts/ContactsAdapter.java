package com.example.karyc.vkontaktikum.ui.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karyc.vkontaktikum.databinding.ViewFriendsContactsItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    interface ContactsAdapterListener {
        void onButtonCallClick(Contact contact);
    }

    public ContactsAdapterListener listener;
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
        ClickHandler clickHandler = new ClickHandler();
        contactsHolder.binding.setOnItemClick(clickHandler);
        contactsHolder.binding.setItem(contacts.get(position));
        contactsHolder.binding.executePendingBindings();
    }

    public void setContacts(List<Contact> contactList) {
        contacts.clear();
        contacts.addAll(contactList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsHolder extends RecyclerView.ViewHolder {
        ViewFriendsContactsItemBinding binding;

        ContactsHolder(View itemView, ViewFriendsContactsItemBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }

    public class ClickHandler {
        public void buttonCall(Contact contact) {
            if (listener != null) {
                listener.onButtonCallClick(contact);
            }
        }
    }

}




