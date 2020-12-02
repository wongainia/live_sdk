package com.lib.showfield.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by upingu
 * Date  2020/4/7 10:55
 * <p>
 * Description
 **/
public class FmPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public FmPagerAdapter(@NonNull FragmentManager fm, int behavior,
                          List<Fragment> fragments, List<String> titles) {
        super(fm, behavior);
        mFragments = fragments;
        mTitles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
