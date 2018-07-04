package com.example.zsoft.calendar_for_nm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;



/**
 * Created by AZelinskiy on 13.06.2018.
 */

public class Menu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        TabLayout tableLayout=findViewById(R.id.tabLay);
        ViewPager pager=findViewById(R.id.viewPage);


        Adapter_FragmentPage adapter_fragmentPage=new Adapter_FragmentPage(getSupportFragmentManager());

        pager.setAdapter(adapter_fragmentPage);

        tableLayout.setupWithViewPager(pager);

    }
}
