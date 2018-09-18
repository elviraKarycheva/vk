package com.example.karyc.vkontaktikum.ui.friends;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FriendsPagerAdapter extends FragmentPagerAdapter {
    public FriendsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllFriendsFragment();
            case 1:
                return new OnlineFriendsFragment();
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
                return "All friends";
            case 1:
                return "Online";
            default:
                return null;
        }
    }
}