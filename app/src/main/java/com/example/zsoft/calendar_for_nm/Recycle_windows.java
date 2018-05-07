package com.example.zsoft.calendar_for_nm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adolf on 14.04.2018.
 */

/*ПЕредаю дату в метод, сдесь получаю молный массив на день
* этот массив рендерю
* и отправляю в адаптер
*
1
Оля Медведева
13:00:00.000
14:00:00.000
89513115412
2017-0-6
Mon Dec 05 00:16:40 EET 2016
false
*/


public class Recycle_windows extends AppCompatActivity {

    RecyclerView rv;
    ConstraintLayout layout;

    public static  List<Object> data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_windows);

        layout=(ConstraintLayout)findViewById(R.id.layout_rec_win_id);
        layout.setBackgroundResource(new MainActivity().background_pref(this));

        rv=(RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager li=new LinearLayoutManager(this);
        rv.setLayoutManager(li);

        data=new ArrayList<>();
        data.add(new Constructor_data("ann",100,false,"17","35",
                "18","55"));
        data.add(new Constructor_data("jane",200,true,"18","55",
                "19","20"));
        data.add(new Constructor_data.Constructor_free_data("19","20",
                "20","45"));
        data.add(new Constructor_data("гена",700,false,"17","35",
                "18","55"));
        data.add(new Constructor_data("ann",100,false,"17","35",
                "18","55"));
        data.add(new Constructor_data("jane",200,true,"17","35",
                "18","55"));
        data.add(new Constructor_data("Eedfhjd Dgfkgfk",1300,true,"17","35",
                "18","55"));
        data.add(new Constructor_data.Constructor_free_data("19","20",
                "20","45"));
        data.add(new Constructor_data("гена",700,false,"17","35",
                "18","55"));
        data.add(new Constructor_data("jane",200,true,"17","35",
                "18","55"));
        data.add(new Constructor_data("Eedfhjd Dgfkgfk",1300,true,"17","35",
                "18","55"));
        data.add(new Constructor_data.Constructor_free_data("19","20",
                "20","45"));
        data.add(new Constructor_data("гена",700,false,"17","35",
                "18","55"));


        Adapter_recycle adapter=new Adapter_recycle(Recycle_windows.this);
        rv.setAdapter(adapter);
        adapter.setAdapter_recycle(data);
        adapter.notifyDataSetChanged();




    }
}
