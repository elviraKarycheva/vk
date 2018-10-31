package com.example.karyc.vkontaktikum.ui.friends;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.databinding.ActivityFriendsBinding;

public class FriendsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFriendsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_friends);

        ViewPager pager = findViewById(R.id.pager);
        FriendsPagerAdapter pagerAdapter = new FriendsPagerAdapter(getSupportFragmentManager(), this);
        binding.pager.setAdapter(pagerAdapter);

        binding.tablayout.setupWithViewPager(pager);
    }
}