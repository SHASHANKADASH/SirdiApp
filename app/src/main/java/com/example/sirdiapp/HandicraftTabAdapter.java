package com.example.sirdiapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HandicraftTabAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public HandicraftTabAdapter(FragmentManager fm,int NumberOfTabs){
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                 HandicraftArtFragment tab1 = new HandicraftArtFragment();
                 return tab1;
            case 1:
                HandicraftCraftFragment tab2 = new HandicraftCraftFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
