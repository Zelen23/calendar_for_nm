package com.example.zsoft.calendar_for_nm;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // переписать на фрагменты

    GridView grView_cld;
    TextView l_date, l_year;
    ImageButton b_set;
    ConstraintLayout layout;
    RecyclerView recyclerViewMain;

    public static int today;
    public static int mns;
    public static int year;
    public static String mns_name;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grView_cld=(GridView)findViewById(R.id.gridView);
        b_set=(ImageButton)findViewById(R.id.settings);
        recyclerViewMain=(RecyclerView)findViewById(R.id.recycleMain);

        layout=(ConstraintLayout)findViewById(R.id.layout_id);
        layout.setBackgroundResource(background_pref(this));

// кнопка настроек

        b_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),preference.class);
                startActivity(intent);
            }
        });

        final String [] day=day();
        today=Integer.parseInt(day[0]);
        mns=Integer.parseInt(day[1]);
        year=Integer.parseInt(day[3]);
        mns_name=day[4];


//  дата в лейбл
        set_date_to_label(mns,today,year);

// Прикутил слушатель на грид
        final GestureDetector gestureDetector=new GestureDetector(new GestureListener() );

//проверка разрешения нужно добавить обход для >23
        int permission= ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission== PackageManager.PERMISSION_GRANTED){

// клик по лейблу
            click_label(day);

// показываю календарь
            grView_cld.setAdapter(ads(mns,year));
            grView_cld.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return event.getAction()==MotionEvent.ACTION_MOVE;
                }
            });
        }else{
// прошу разрешения
            ActivityCompat.requestPermissions(this ,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    ,1);
        }
    }

//  делать адаптер
    @NonNull
    private custom_grid_adapter ads(int mns, int year){

        bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();
        final List<String> list_date=creat_mass.grv(mns,year);
// цветной массие
        final int [] mass_pict= creat_mass.convert_mass_for_render(this,
                creat_mass.grv(mns,year),mns,year);
        // готовые массивы 1-с датами и пробелами 2- с цветами

        return new custom_grid_adapter(this, list_date
                ,mass_pict);

    }

// клик по лейблу даты возвращает на текущуу страницу
    private void click_label(final String []day){

        l_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_date_to_label(
                        Integer.parseInt(day[1]),
                        Integer.parseInt(day[0]),
                        Integer.parseInt(day[3])
                );
                today = Integer.parseInt(day[0]);
                mns = Integer.parseInt(day[1]);
                year = Integer.parseInt(day[3]);
                mns_name = day[4];

                grView_cld.setAdapter(ads(mns,year)
                        //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));

                        // new custom_grid_adapter(MainActivity.this, list_date, mass_pict)
                );
            }
        });


    }

// шапка дата и год
    void set_date_to_label(int month,int day,int th_syear){

        List<String> l_month=new ArrayList<String>();
        l_month.add("Январь");
        l_month.add("Февраль");
        l_month.add("Март");
        l_month.add("Апрель");
        l_month.add("Май");
        l_month.add("Июнь");
        l_month.add("Июль");
        l_month.add("Август");
        l_month.add("Сентябрь");
        l_month.add("Октябрь");
        l_month.add("Ноябрь");
        l_month.add("Декабрь");


        l_date=(TextView)findViewById(R.id.date_label);
        l_year=(TextView)findViewById(R.id.year_label);
        if (day==0){
            l_date.setText(l_month.get(month));


        }else

        l_date.setText(l_month.get(month)+" "+day);
        l_year.setText(""+th_syear);


    }

// Разбор текущей даты для проверок
    String[] day() {
    //month-day
        GregorianCalendar cld_m = new GregorianCalendar();
        String day = cld_m.getTime().toString();
        String[] getday = day.split(" ", 5);
        int mn = cld_m.get(Calendar.MONTH);
        String[] show_day = new String[5];
        show_day[0] = String.valueOf(Integer.parseInt(getday[2]));
        show_day[1] = String.valueOf(mn);
        show_day[2] = String.valueOf(getday[4]);
        show_day[3] = String.valueOf(cld_m.get(Calendar.YEAR));
        show_day[4] = getday[1];


        Log.i("today**", getday[2]);
        Log.i("mns_name", getday[1]);
        Log.i("**", getday[0]);
        Log.i("int_mns*", String.valueOf(mn));


        return show_day;
    }

// ставлю фон активити
    public int background_pref(Context context){
         int[] back={
                R.drawable.gradient_1,
                R.drawable.gradient_2,
                R.drawable.gradient_3};
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        String index_background=sharedPreferences.getString("background","0");
       return back[Integer.parseInt(index_background)];
    }
//перезапуск активити
    public  void refreshMain(){
        Intent intent=new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);
        Log.i("Main","refresh");

    }

// размерность грида
    private void adjustGridView(){
        grView_cld.setNumColumns(7);
        grView_cld.setColumnWidth(30);
    }

//permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    // yes
                    // status=true;
                    refreshMain();
                    Toast.makeText(this, "Permission granted "
                            , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permission denied to " +
                                    "read your External storage"
                            , Toast.LENGTH_SHORT).show();
                }
                return;
        }
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    // слушатель жестов
    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        final bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();

        // получаю дату по позиции клика в слушателе
        private  int [] dat_of_pos(int position){
            int [] idate;
            try{
                final int iday=Integer.parseInt(creat_mass.grv(mns,year).get(position).toString());
                idate=new int[3];
                idate[0]=year;
                idate[1]=mns;
                idate[2]=iday;
            }
            catch(NumberFormatException ex){
                Log.i("number_format",ex.getMessage());
                idate=new int[1];
            }

            return idate;
        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            grView_cld.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   int date_db[]=dat_of_pos(position);

                   if (date_db.length>1) {
                       final String s_date = date_db[0] + "-" + date_db[1] + "-" + date_db[2];
                       set_date_to_label(date_db[1], date_db[2], date_db[0]);
                       Log.i("_data", s_date);
                   }
                }
            });


            return super.onSingleTapUp(e);


        }
/*
        @Override
        public void onLongPress(MotionEvent e) {
            grView_cld.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView,
                                               View view, int i, long l) {
                    //Log.i("long_clck",String.valueOf(""));
                    int date_db[] = dat_of_pos(i);

                    if(date_db.length>1){
                        Intent intent = new Intent(MainActivity.this,
                                Recycle_windows.class);
                        intent.putExtra("date_for_db", date_db);
                        startActivity(intent);
                    }
                    return false;
                }
            });

            super.onLongPress(e);
        }

*/
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //если в момент скролла то не слшушаю долгий клик или прицепить другой клик(двойной)
            Log.i("tap","tap");
            grView_cld.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int date_db[] = dat_of_pos(position);

                    if(date_db.length>1){
                        Intent intent = new Intent(MainActivity.this,
                                Recycle_windows.class);
                        intent.putExtra("date_for_db", date_db);
                        startActivity(intent);
                    }
                }
            });


            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            bild_mass_for_adapter day_in_this_month=new bild_mass_for_adapter();
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) >
                    SWIPE_THRESHOLD_VELOCITY) {
                if(mns<11) {
                    mns++;
                }else{
                    mns=0;
                    year++;

                }

                set_date_to_label(mns,0,year);
                grView_cld.setAdapter(
                        ads(mns,year)
/*
                        //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                        new custom_grid_adapter(MainActivity.this,
                                creat_mass.grv(mns,year),
                                creat_mass.convert_mass_for_render(MainActivity.this
                                        ,creat_mass.grv(mns,year)
                                        ,mns
                                        ,year))
                                        */);

                return false; // справа налево

            }  else if (e2.getX() - e1.getX() >
                    SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(mns>0){
                    mns--;
                }else{
                    mns=11;
                    year--;
                }
                final bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();
                set_date_to_label(mns,0,year);
                grView_cld.setAdapter(
                        ads(mns,year)
/*
                        //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                        new custom_grid_adapter(MainActivity.this,
                                creat_mass.grv(mns,year),
                                creat_mass.convert_mass_for_render(MainActivity.this
                                        ,creat_mass.grv(mns,year)
                                        ,mns
                                        ,year))
                                        */
                                        );

                return false; // слева направо
            }


            day_in_this_month.dat();

            return false;
        }
    }


}
