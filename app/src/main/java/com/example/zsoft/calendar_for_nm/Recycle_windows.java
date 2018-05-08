package com.example.zsoft.calendar_for_nm;

import android.content.Context;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
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

    String sFt = "07:00";
    String sEn = "23:59";


    public static  List<Object> data;
    SharedPreferences sharedPreferences;

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



        List<String> dataDB=new ExexDB().l_clients_of_day(this,get_day_orders());
        Adapter_recycle adapter=new Adapter_recycle(Recycle_windows.this);
        rv.setAdapter(adapter);
        adapter.setAdapter_recycle(set_test(dataDB));
        adapter.notifyDataSetChanged();

        settingsTime(sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this));
    }

    // создаю лайнер(для  бейсадаптера конвертирую строку с данныим)
    public List<Object> set_test(List<String> dataDB) {

        Log.i("DataDB",dataDB.toString());


        data =new ArrayList<>();
        ArrayList <Constructor_data> liner=new ArrayList<>();
        ArrayList<String> input=new ArrayList<>();
        // если день пустой
        if (dataDB.size() <= 5) {
            data.add(new Constructor_data.Constructor_free_data(sFt,sEn));

        } else {
            int i = 0;
            for (String elt : dataDB) {

                if (i % 6 == 0) {
                    if (i == 0) {
// если первая строчка-первое время- равно началу дня то с начала дня первая запись
                        if (dataDB.get(1).equals(sFt)) {
                            data.add(new Constructor_data(
                                    dataDB.get(i),
                                    dataDB.get(i + 1),
                                    dataDB.get(i + 2),
                                    dataDB.get(i + 3),
                                    dataDB.get(i + 4),
                                    Boolean.parseBoolean(dataDB.get(i + 5))));
// если первая запись не равна началу дня
                        } else {
                            // если первая запись раньше чем начало дня,то вывожу первую запись
                            // если первая запись позже начала дня то от начала дня до первой записи
                            data.add(new Constructor_data.Constructor_free_data(sFt, dataDB.get(i + 1)));
                            data.add(new Constructor_data(
                                    dataDB.get(i),
                                    dataDB.get(i + 1),
                                    dataDB.get(i + 2),
                                    dataDB.get(i + 3),
                                    dataDB.get(i + 4),
                                    Boolean.parseBoolean(dataDB.get(i + 5))));
                        }
                    }

                    if (i > 0) {

                        // если дата начала ==дате конца то
                        //i-5
                        if (dataDB.get(i + 1).equals(dataDB.get(i - 4))) {
                            data.add(new Constructor_data(
                                    dataDB.get(i),
                                    dataDB.get(i + 1),
                                    dataDB.get(i + 2),
                                    dataDB.get(i + 3),
                                    dataDB.get(i + 4),
                                    Boolean.parseBoolean(dataDB.get(i + 5))));

                        } else {
                            data.add(new Constructor_data.Constructor_free_data(
                                    dataDB.get(i - 4),
                                    dataDB.get(i + 1)));
                            data.add(new Constructor_data(
                                    dataDB.get(i),
                                    dataDB.get(i + 1),
                                    dataDB.get(i + 2),
                                    dataDB.get(i + 3),
                                    dataDB.get(i + 4),
                                    Boolean.parseBoolean(dataDB.get(i + 5))));
                        }
                    }
                }
                i++;
            }

            // нсли последнее время в записи равно последнему в дне
            if(dataDB.get(dataDB.size() - 4).equals(sEn)){

            }else
                data.add(new Constructor_data.Constructor_free_data(
                        dataDB.get(i - 4),
                        sEn));
        }


        Log.i("DATA",""+data.size());

return data;
    }

    //  получаю дату
    public  String get_day_orders(){
        Intent intent=getIntent();

        int idat[]=intent.getIntArrayExtra("date_for_db");
        Log.i("RecWin_data",idat[0]+"-"+idat[1]+"-"+idat[2]);
        return idat[0]+"-"+idat[1]+"-"+idat[2];

    }

    // получаю строки времени из настоек
    public  void settingsTime(SharedPreferences sharedPreferences){
        String t1=sharedPreferences.getString("t1","07:30");
        String t2=sharedPreferences.getString("t2","23:59");
        convtoTime(t1);
        convtoTime(t2);

        //  если эти стоки время и t1>t2 то sFt и sEn принимают значения из настроек
        // если ощибка то пишу время по дефолту

    }

    // из строки получаю время
    public void convtoTime(String time){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        try {
            Date sDate=sdf.parse(time);
            String h=String.format("%02d",sDate.getHours());
            String m=String.format("%02d",sDate.getMinutes());
            Log.i("this_time,",h+":"+m);
        } catch (ParseException e) {
            e.printStackTrace();

            Log.i("this_noTime","fuck");
        }


    }
}
