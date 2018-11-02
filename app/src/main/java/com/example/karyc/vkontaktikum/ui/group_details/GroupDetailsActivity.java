package com.example.karyc.vkontaktikum.ui.group_details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.Group;
import com.example.karyc.vkontaktikum.databinding.ActivityGroupDetailsBinding;

public class GroupDetailsActivity extends AppCompatActivity {
    ActivityGroupDetailsBinding binding;
    private GroupDetailsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_details);
        viewModel = ViewModelProviders.of(this).get(GroupDetailsViewModel.class);
        viewModel.getGroupDetailsLiveData().observe(this, new Observer<Group>() {
            @Override
            public void onChanged(@Nullable Group group) {
                if (group == null) {
                    return;
                }
            }
        });
    }
}
