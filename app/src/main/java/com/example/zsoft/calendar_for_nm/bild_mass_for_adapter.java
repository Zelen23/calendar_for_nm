package com.example.zsoft.calendar_for_nm;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azelinsky on 13.03.2018.
 */

public class bild_mass_for_adapter {

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


}
