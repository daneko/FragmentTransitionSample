package com.example.fragmenttransitions.details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragmenttransitions.ResourceLoader;

/**
 */
class DetailFragmentAdapter extends FragmentPagerAdapter {

    public DetailFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DetailChildFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return ResourceLoader.RESOUCE_COUNT;
    }
}
