package com.example.karyc.vkontaktikum.ui.groups;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.Group;
import com.example.karyc.vkontaktikum.databinding.ActivityGroupsBinding;
import com.example.karyc.vkontaktikum.ui.group_details.GroupDetailsActivity;
import com.example.karyc.vkontaktikum.ui.MarginItemDecoration;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity implements GroupsAdapter.GroupsAdapterListener{
    private static final String ID_GROUP = "idGroup";
    private GroupsAdapter adapter = new GroupsAdapter();
    ActivityGroupsBinding binding;
    private GroupsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_groups);
        viewModel = ViewModelProviders.of(this).get(GroupsViewModel.class);
        viewModel.getGroupsLiveData().observe(this, new Observer<ArrayList<Group>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Group> groups) {
                if (groups == null) {
                    return;
                }
                adapter.setGroups(groups);
                binding.swipeGroups.setRefreshing(false);
            }
        });

        RecyclerView recyclerView = binding.recyclerviewGroups;
        binding.recyclerviewGroups.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.listener = this;

        int dimenSide = (int) getResources().getDimension(R.dimen.margin_side);
        int dimenTop = (int) getResources().getDimension(R.dimen.margin_top);
        recyclerView.addItemDecoration(new MarginItemDecoration(dimenSide, dimenTop));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        viewModel.loadData();

        binding.swipeGroups.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               viewModel.loadData();
            }
        });
    }

    @Override
    public void onButtonLeaveGroup(final long id) {
        String title = this.getString(R.string.title_group_alert_dialog);
        String leaveGroup = this.getString(R.string.button_leave_group_alert_dialog);
        String buttonCancel = this.getString(R.string.cancel_alert_dialog);

        AlertDialog.Builder ad = new AlertDialog.Builder(GroupsActivity.this);
        ad.setTitle(title);
        ad.setPositiveButton(leaveGroup, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(GroupsActivity.this, getString(R.string.success_leave_group_ad_toast),
                        Toast.LENGTH_LONG).show();
                viewModel.onDeleteGroup(id);
            }
        });
        ad.setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        ad.setCancelable(true);
        ad.show();
    }

    public void onGroupGetDetails (long id) {
        Intent intent = new Intent(this, GroupDetailsActivity.class);
        intent.putExtra(ID_GROUP, id);
        startActivity(intent);
    }
}