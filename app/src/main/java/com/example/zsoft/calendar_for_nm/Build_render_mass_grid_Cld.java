package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by azelinsky on 13.03.2018.
 */

class Build_render_mass_grid_Cld {

// прислан массив и дата c годом,
// дату и год исп для получения массивов

    int[] convert_mass_for_render(Context context, List list_d, int month, int year){
        List<String> day_for_render=new ArrayList<>();
        List<Integer> weight_of_day=new ArrayList<>();
        List<Constructor_dayWeight> listDayWeight=new ExecDB().init_mass(context,month,year);
        for(Constructor_dayWeight elt:listDayWeight){
            day_for_render.add(elt.day);
            weight_of_day.add(elt.weight);

        }

        int[] mass_pic=new int[list_d.size()];

        for(int i=0;i<day_for_render.size();i++){

          //  Log.i("Build_conv_mass_pos",String.valueOf(list_d.indexOf(day_for_render.get(i))));

            switch (weight_of_day.get(i)){
                case 1:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_10;
                break;
                case 2:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_10;
                    break;
                case 3:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_10;
                    break;
     //----
                case 4:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_25;
                    break;
                case 5:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_25;
                    break;
                case 6:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_25;
                    break;
    //----

                case 7:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_50;
                    break;

                case 8:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_50;
                    break;
                case 9:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_50;
                    break;
    //----
                case 10:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_75;
                    break;
                case 11:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_75;
                    break;
                case 12:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_75;
                    break;
                case 13:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_75;
                    break;
    //----
                case 14:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_100;
                    break;
                case 15:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_100;
                    break;
                case 16:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_100;
                    break;
                case 17:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_100;
                    break;
                case 18:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_100;
                    break;
                case 19:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_svg_100;
                    break;

                case 20:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 21:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 22:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 23:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 24:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 25:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 26:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 27:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 28:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;
                case 29:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.ic_services;
                    break;

                case -1:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.empty;
                    break;
                default:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.empty;
                    break;
            }
        }
        return mass_pic;
    }

     List grv(final int month, int y) {

        GregorianCalendar cld = new GregorianCalendar();
        int d;
        int max;

        cld.set(y, month, 1);
        d = cld.get(Calendar.DAY_OF_WEEK);
        max = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] ms = new String[50];
        final List<String> list_d = new ArrayList<>();
        list_d.add("Пн");
        list_d.add("Вт");
        list_d.add("Ср");
        list_d.add("Чт");
        list_d.add("Пт");
        list_d.add("Сб");
        list_d.add("Вс");

//из нетбина календарь
        int in = 1;
        for (int ir = 1; ir < 49; ir++) {

            if (in <= max) {
                if (d == 1) {

            /*то в массив первые 6 позиций пишем null*/
                    if (ir >= d + 6) {
                        ms[ir] = String.valueOf(in++);
                    }

                } else if (ir >= d - 1) {

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

     void dat(){
        boolean flags=false;
        //если год и месяц в массива соответствуют текущим то
        if(2018==MainActivity.year && 2==MainActivity.mns);
        flags=true;
        Log.i("Build_flag", String.valueOf(flags));


    }




}
