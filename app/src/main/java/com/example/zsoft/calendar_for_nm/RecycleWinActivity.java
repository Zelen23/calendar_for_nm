package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adolf on 14.04.2018.
 */


public class RecycleWinActivity extends AppCompatActivity {
    private  static String TAG="zsoft.RecycleWinActivity";
    RecyclerView rv;
    TextView rv_date;
    LinearLayout layout;
    Swipe_Adapter_recycle adapter;


    String sFt = "07:00";
    String sEn = "23:59";

    public static String firstTime;
    public static String secondTime;

    public static  List<Object> data;
    //SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_windows);

        layout=findViewById(R.id.layout_rec_win_id);
        layout.setBackgroundResource(new MainActivity().background_pref(this));
        rv_date=findViewById(R.id.recWin_date);
        rv_date.setText(new HelperData().ConvertDateFromDBX(get_day_orders()));

        rv= findViewById(R.id.recycleView);
        LinearLayoutManager li=new LinearLayoutManager(this);
        rv.setLayoutManager(li);


        List<String> dataDB=new ExecDB().l_clients_of_day(this,get_day_orders());
        adapter=new Swipe_Adapter_recycle(RecycleWinActivity.this);
        adapter.setAdapter_recycle(set_test(dataDB,this),get_day_orders());
        rv.setAdapter(adapter);

        settingsTime(this);

    }

    // создаю лайнер(для  бейсадаптера конвертирую строку с данныим)
    public List<Object> set_test(List<String> dataDB,Context context) {

        settingsTime(context);
        Log.i(TAG,dataDB.toString());

        data =new ArrayList<>();
      //  ArrayList <Constructor_data> liner=new ArrayList<>();
      //  ArrayList<String> input=new ArrayList<>();
        // если день пустой
        if (dataDB.size() <= 5) {
            data.add(new Constructor_data.Constructor_free_data(firstTime,secondTime));

        } else {
            int i = 0;
            for (String elt : dataDB) {

                if (i % 6 == 0) {
                    if (i == 0) {

                // если первая строчка-первое время- равно началу дня то с начала дня первая запись

                        if (dataDB.get(1).equals(firstTime)) {
                            data.add(new Constructor_data(
                                    dataDB.get(i),
                                    dataDB.get(i + 1),
                                    dataDB.get(i + 2),
                                    dataDB.get(i + 3),
                                    dataDB.get(i + 4),
                                    Boolean.parseBoolean(dataDB.get(i + 5))));

                // если первая запись не равна началу дня
                        } else {

                // если время начала дня старше чем первое вреся записи то пустую строку не делаю
                            // если первая запись раньше чем начало дня,то вывожу первую запись
                            // если первая запись позже начала дня то от начала дня до первой записи
                            if(getTimeInStr(firstTime,dataDB.get(i + 1))<0)
                            data.add(new Constructor_data.Constructor_free_data(firstTime, dataDB.get(i + 1)));

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
                        //i-5
                        if (dataDB.get(i + 1).equals(dataDB.get(i - 4))) {
                // если конец записи = началу следующей
                            data.add(new Constructor_data(
                                    dataDB.get(i),
                                    dataDB.get(i + 1),
                                    dataDB.get(i + 2),
                                    dataDB.get(i + 3),
                                    dataDB.get(i + 4),
                                    Boolean.parseBoolean(dataDB.get(i + 5))));

                        } else {
                // если между записями окно
                /*проверяю последнюю дату в окне*/

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
            if(dataDB.get(dataDB.size() - 4).equals(secondTime)){

            }else

        // если последнее время записи != последнему времени в дне то пустую строчку
        // если последнее время записи старше чем последнее время то нет последней строчки
                if(getTimeInStr( dataDB.get(i -4),secondTime)<0)
                data.add(new Constructor_data.Constructor_free_data(
                        dataDB.get(i - 4),
                        secondTime));
        }

        Log.i(TAG,""+data.size());

return data;
    }

    //  получаю дату
    public  String get_day_orders(){
        Intent intent=getIntent();

        int idat[]=intent.getIntArrayExtra("date_for_db");
        Log.i(TAG,idat[0]+"-"+idat[1]+"-"+idat[2]);
        return idat[0]+"-"+idat[1]+"-"+idat[2];

    }

    // получаю строки времени из настоек
    public  void settingsTime(Context context){

        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        String t1=sharedPreferences.getString("t1","07:30");
        String t2=sharedPreferences.getString("t2","23:59");

        firstTime=convtoTime(t1,sFt);
        secondTime= convtoTime(t2,sEn);
        //  если эти стоки время и t1>t2 то sFt и sEn принимают значения из настроек
        // если ощибка то пишу время по дефолту

    }


    public  int getTimeInStr(String time1,String time2){
        // получаю сторку парсю из нее время
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        int cmp = 0;
        try {
            Date sDate=sdf.parse(time1);
            Date s2Date=sdf.parse(time2);
             cmp=sDate.compareTo(s2Date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    return  cmp;
    }

    // из строки получаю время
    public String convtoTime(String time, String t){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String stime;
        try {
            Date sDate=sdf.parse(time);
            @SuppressLint("DefaultLocale")
            String h=String.format("%02d",sDate.getHours());
            @SuppressLint("DefaultLocale")
            String m=String.format("%02d",sDate.getMinutes());
            stime=h+":"+m;
            Log.i(TAG+" this_time,",h+":"+m);
        } catch (ParseException e) {
            e.printStackTrace();
            stime=t;
            Log.i(TAG+" this_noTime","fuck");
        }
    return stime;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Destroy");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter.restoreStates(savedInstanceState);
        }
    }
}
