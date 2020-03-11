package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by AZelinskiy on 13.06.2018.
 */

public class Adapter_FragmentPage extends FragmentStatePagerAdapter {


    Context context;

    public Adapter_FragmentPage(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment_search();

            case 1:
                return new Fragment_Top();
        }

        return new Fragment_search();
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
    String title = null;

       
        /* get localized string */

        switch (position) {
            case 0:
               
                title= context.getResources().getString(R.string.serch_1tab);
                break;

            case 1:
                title=context.getResources().getString(R.string.serch_2tab);
                break;
        }

return title;

    }
}
