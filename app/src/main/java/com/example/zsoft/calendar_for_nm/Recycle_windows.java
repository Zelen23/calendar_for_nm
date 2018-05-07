package com.example.zsoft.calendar_for_nm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView rv_date;
    LinearLayout layout;
    String dateDB;

    public static  List<Object> data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_windows);

        layout=(LinearLayout)findViewById(R.id.layout_rec_win_id);
        layout.setBackgroundResource(new MainActivity().background_pref(this));
        rv_date=(TextView)findViewById(R.id.recWin_date);
        rv_date.setText(get_day_orders());

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




        Adapter_recycle adapter=new Adapter_recycle(Recycle_windows.this);
        rv.setAdapter(adapter);
        adapter.setAdapter_recycle(data);
        adapter.notifyDataSetChanged();

    }

    //  получаю дату
    public  String get_day_orders(){
        Intent intent=getIntent();

        int idat[]=intent.getIntArrayExtra("date_for_db");
        Log.i("RecWin_data",idat[0]+"-"+idat[1]+"-"+idat[2]);
        return idat[0]+"-"+idat[1]+"-"+idat[2];

    }
}
