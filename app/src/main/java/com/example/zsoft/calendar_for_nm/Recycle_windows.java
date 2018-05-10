package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public static String firstTime;
    public static String secondTime;

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
        adapter.setAdapter_recycle(set_test(dataDB,this));
        adapter.notifyDataSetChanged();

        settingsTime(this);

    }

    // создаю лайнер(для  бейсадаптера конвертирую строку с данныим)
    public List<Object> set_test(List<String> dataDB,Context context) {

        settingsTime(context);
        Log.i("DataDB",dataDB.toString());


        data =new ArrayList<>();
        ArrayList <Constructor_data> liner=new ArrayList<>();
        ArrayList<String> input=new ArrayList<>();
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
                // если дата начала ==дате конца то
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
                            if(getTimeInStr(dataDB.get(i -4),firstTime)<0&&
                                    getTimeInStr( dataDB.get(i -4),secondTime)<0){

                            data.add(new Constructor_data.Constructor_free_data(
                                    dataDB.get(i - 4),
                                    dataDB.get(i + 1)));
                            }else{

                                data.add(new Constructor_data.Constructor_free_data(
                                        dataDB.get(i - 4),
                                        secondTime));

                            }
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
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        int cmp = 0;
        try {
            Date sDate=sdf.parse(time1);
            Date s2Date=sdf.parse(time2);
             cmp=sDate.compareTo(s2Date);
            Log.i("itime_comp",String.valueOf(cmp));


        } catch (ParseException e) {
            e.printStackTrace();
        }
    return  cmp;
    }

    // из строки получаю время
    public String convtoTime(String time, String t){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String stime=null;
        try {
            Date sDate=sdf.parse(time);
            String h=String.format("%02d",sDate.getHours());
            String m=String.format("%02d",sDate.getMinutes());
            stime=h+":"+m;
            Log.i("this_time,",h+":"+m);
        } catch (ParseException e) {
            e.printStackTrace();
            stime=t;
            Log.i("this_noTime","fuck");
        }

    return stime;
    }
}
