package com.example.zsoft.calendar_for_nm;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView grView_cld;
    GregorianCalendar cld = new GregorianCalendar();
    int month;
    int d;
    int max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grView_cld=(GridView)findViewById(R.id.gridView);
        String[] line=new String[35];
        for(int i=0;i<35;i++){
            line[i]=" "+i;
        }

        List<String> list_d=new ArrayList<String>();





        bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();


        grView_cld.setAdapter(
                //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                new custom_grid_adapter(this,grv(2,2018),
                        creat_mass.convert_mass_for_render(grv(2,2018))));

    }

    public List grv(final int month, int y) {

        final String[] name = {"пн", "вт", "ср", "чт", "пт", "сб", "вс"};


        cld.set(y, month, 1);
        d = cld.get(Calendar.DAY_OF_WEEK);
        max = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] ms = new String[50];
        final List<String> list_d = new ArrayList<>();

//из нетбина календарь
        int in = 1;
        for (int ir = 1; ir < 49; ir++) {

            if (in <= max) {
                if (d == 1) {
                    Log.i("d_grv_if=1", String.valueOf(d));
            /*то в массив первые 6 позиций пишем null*/
                    if (ir >= d + 6) {
                        ms[ir] = String.valueOf(in++);
                    }

                } else if (ir >= d - 1) {
                    Log.i("d_grv_if!=1", String.valueOf(d));
                    ms[ir] = String.valueOf(in++);
                }
            }
            if (ms[ir] == null) {
                list_d.add(" ");
            } else {
                list_d.add(String.valueOf(ms[ir]));
            }
        }

       return list_d;
    }

    private void adjustGridView(){
        grView_cld.setNumColumns(7);
        //grView_cld.setColumnWidth(50);
    }
}
