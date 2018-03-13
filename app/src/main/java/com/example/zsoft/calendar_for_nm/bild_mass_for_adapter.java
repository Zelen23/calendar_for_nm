package com.example.zsoft.calendar_for_nm;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by azelinsky on 13.03.2018.
 */

public class bild_mass_for_adapter {
    GregorianCalendar cld = new GregorianCalendar();
    int d;
    int max;

    public int[] convert_mass_for_render(List list_d){

        List<String> day_for_render=new ArrayList<String>();
        day_for_render.add("1");
        day_for_render.add("2");
        day_for_render.add("3");
        day_for_render.add("21");
        day_for_render.add("22");

        List<String> weight_of_day=new ArrayList<String>();
        weight_of_day.add("2");
        weight_of_day.add("8");
        weight_of_day.add("15");
        weight_of_day.add("2");
        weight_of_day.add("15");

        int[] mass_pic=new int[list_d.size()];

        for(int i=0;i<day_for_render.size();i++){
           // if()
            Log.i("conv_mass_position",String.valueOf(list_d.indexOf(day_for_render.get(i))));

            switch (weight_of_day.get(i)){
                case "2":
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.minday;
                break;

                case "8":
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;

                case "15":
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.maxday;
                    break;

                case "":
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.empty;
                    break;

                case "-1":
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.empty;
                    break;

            }


        }
        return mass_pic;

    }

    // если установленный год соответствует текущему и месяц соответствует текущему то дент выделяю

    public List grv(final int month, int y) {

        final String[] name = {"пн", "вт", "ср", "чт", "пт", "сб", "вс"};


        cld.set(y, month, 1);
        d = cld.get(Calendar.DAY_OF_WEEK);
        max = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] ms = new String[36];
        final List<String> list_d = new ArrayList<>();

//из нетбина календарь
        int in = 1;
        for (int ir = 1; ir < 35; ir++) {

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

    void dat(int month, int year){
        MainActivity dat=new MainActivity();
        String[] date = dat.day();
        boolean flags=false;

        //если год и месяц в массива соответствуют текущим то
        if(year==Integer.parseInt(date[3])& month==Integer.parseInt(date[1]));
        flags=true;
        Log.i("flag", String.valueOf(flags));





    }


}
