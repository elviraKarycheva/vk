package com.example.karyc.vkontaktikum.ui.friends;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.karyc.vkontaktikum.R;

public class FriendsPagerAdapter extends FragmentPagerAdapter {
    Context context;
    public FriendsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FriendsFragment friendsFragment = new FriendsFragment();
                friendsFragment.showOnline = false;
                return friendsFragment;
            case 1:
                FriendsFragment onlineFriendsFragment = new FriendsFragment();
                onlineFriendsFragment.showOnline = true;
                return onlineFriendsFragment;
        }
        return null;

    }

    @Override
    public int getCount() {
        return (2);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.friends_screen_all_friends);
            case 1:
                return context.getString(R.string.friends_screen_online_friends);
            default:
                return null;
        }
    }
}
