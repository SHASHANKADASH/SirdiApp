package com.example.sirdiapp.Handicraft;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HandicraftTabAdapter extends FragmentStatePagerAdapter {

    final int mNoOfTabs;

    public HandicraftTabAdapter(FragmentManager fm,int NumberOfTabs){
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        //on pressing each tab go to fragment
        switch(position){
            case 0:
                return new HandicraftArtFragment();
            case 1:
                return new HandicraftCraftFragment();
            default:
                return null;
        }
    }

    //getting no of tabs
    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
