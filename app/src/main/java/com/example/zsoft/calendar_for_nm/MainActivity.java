package com.example.zsoft.calendar_for_nm;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView grView_cld;

    public static int today;
    public static int mns;
    public static int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grView_cld=(GridView)findViewById(R.id.gridView);

        final GestureDetector gestureDetector=new GestureDetector(new GestureListener() );
        grView_cld.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });


        String [] day=day();
        today=Integer.parseInt(day[0]);
        mns=Integer.parseInt(day[1]);
        year=Integer.parseInt(day[3]);

        bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();
        grView_cld.setAdapter(
                //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                new custom_grid_adapter(this,
                        creat_mass.grv(mns,year),
                        creat_mass.convert_mass_for_render(creat_mass.grv(mns,year))));



    }



    // Разбор текущей даты для проверок
    String[] day() {
//month-day
        GregorianCalendar cld_m = new GregorianCalendar();
        String day = cld_m.getTime().toString();
        String[] getday = day.split(" ", 5);
        int mn = cld_m.get(Calendar.MONTH);
        String[] show_day = new String[4];
        show_day[0] = String.valueOf(Integer.parseInt(getday[2]));
        show_day[1] = String.valueOf(mn);
        show_day[2] = String.valueOf(getday[4]);
        show_day[3] = String.valueOf(cld_m.get(Calendar.YEAR));


        Log.i("today**", getday[2]);
        Log.i("mns_name", getday[1]);
        Log.i("**", getday[0]);
        Log.i("int_mns*", String.valueOf(mn));


        return show_day;
    }

    // размерность грида
    private void adjustGridView(){
        grView_cld.setNumColumns(7);
        grView_cld.setColumnWidth(30);
    }


    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            bild_mass_for_adapter day_in_this_month=new bild_mass_for_adapter();

            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) >
                    SWIPE_THRESHOLD_VELOCITY) {
                if(mns<11) {
                    mns = mns + 1;
                    bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();
                    grView_cld.setAdapter(
                            //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                            new custom_grid_adapter(MainActivity.this,
                                    creat_mass.grv(mns,year),
                                    creat_mass.convert_mass_for_render(creat_mass.grv(mns,year))));

                }

                Toast.makeText(MainActivity.this,
                        "<---^ "+mns,Toast.LENGTH_SHORT).show();
                return false; // справа налево

            }  else if (e2.getX() - e1.getX() >
                    SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(mns>0){
                    mns=mns-1;
                    bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();
                    grView_cld.setAdapter(
                            //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                            new custom_grid_adapter(MainActivity.this,
                                    creat_mass.grv(mns,year),
                                    creat_mass.convert_mass_for_render(creat_mass.grv(mns,year))));
                }

                Toast.makeText(MainActivity.this,
                        "^---> "+mns,Toast.LENGTH_SHORT).show();

                return false; // слева направо
            }
            day_in_this_month.dat();
            return false;
        }
    }
}
