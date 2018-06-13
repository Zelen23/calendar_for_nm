package com.example.zsoft.calendar_for_nm;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by AZelinskiy on 13.06.2018.
 */

public class Adapter_FragmentPage extends FragmentStatePagerAdapter {



    public Adapter_FragmentPage(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new Fragment_search();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
            return "Search";



    }
}
