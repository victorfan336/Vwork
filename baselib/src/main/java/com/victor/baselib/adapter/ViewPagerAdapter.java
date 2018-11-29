package com.victor.baselib.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanwentao
 * @Description
 * @date 2018/6/25
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] mTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mList, String[] titles) {
        super(fm);
        this.mFragmentList = mList;
        this.mTitles = titles;
    }

    public void add(Fragment fragment) {
        if (isEmpty()) {
            mFragmentList = new ArrayList<>();
        }
        mFragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        if (isEmpty()) {
            return null;
        } else {
            if (position >= mFragmentList.size()) {
                position = mFragmentList.size() - 1;
            } else if (position < 0) {
                position = 0;
            }
        }
        return  mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return isEmpty() ? 0 : mFragmentList.size();
    }

    public boolean isEmpty() {
        return mFragmentList == null;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }
}
