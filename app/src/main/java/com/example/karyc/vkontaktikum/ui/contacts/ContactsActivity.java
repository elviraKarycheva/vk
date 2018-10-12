package com.example.karyc.vkontaktikum.ui.contacts;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.databinding.ActivityContactsBinding;
import com.example.karyc.vkontaktikum.ui.MarginItemDecoration;

import java.util.List;

public class ContactsActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;
    ContactsAdapter adapter = new ContactsAdapter();
    ContactsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContactsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts);
        viewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        viewModel.getContactsLiveData().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                if (contacts == null){
                    return;
                }

                adapter.setContacts(contacts);
            }
        });


        binding.recyclerviewContact.setAdapter(adapter);
        binding.recyclerviewContact.setHasFixedSize(true);
        adapter.listener = this;

        int dimenSide = (int) getResources().getDimension(R.dimen.margin_side);
        int dimenTop = (int) getResources().getDimension(R.dimen.margin_top);
        binding.recyclerviewContact.addItemDecoration(new MarginItemDecoration(dimenSide, dimenTop));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerviewContact.setLayoutManager(mLayoutManager);

        getPermissionToReadUserContacts();

    }

    public void getPermissionToReadUserContacts() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        READ_CONTACTS_PERMISSIONS_REQUEST);
            }
        } else {
            requestData();
        }
    }

    private void requestData() {
        viewModel.loadData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
                requestData();
            } else {
                onBackPressed();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onButtonCallClick(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contact.getPhone()));
        startActivity(intent);
    }
}
