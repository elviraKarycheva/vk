package com.example.karyc.vkontaktikum.ui.friends;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.karyc.vkontaktikum.R;

public class FriendsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new FriendsPagerAdapter(getSupportFragmentManager()));

        FriendsPagerAdapter pagerAdapter = new FriendsPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(pager);
    }
}