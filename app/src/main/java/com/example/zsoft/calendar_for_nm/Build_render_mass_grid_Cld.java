package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by azelinsky on 13.03.2018.
 */

class Build_render_mass_grid_Cld {
    private ArrayList<String> mn_;
    private ArrayList<Integer> cl_;


// прислан массив и дата c годом,
// дату и год исп для получения массивов


private void init_mass(Context context, int month, int y){
    mn_ = new ArrayList<>();
    cl_ = new ArrayList<>();
        db mDbHelper = new db(context);
    //Application did not close the cursor or database object that was opened here
    mDbHelper.getWritableDatabase();
    SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
// показывать только на выбранный месяц!!
//*создать два массива записи в день и цвет дня */
///*Запрос из истории*/

    Cursor c = db1.rawQuery("SELECT * FROM history where date " +
            "like '" + y + "-" + month + "-%'", null);

    if (c.moveToFirst()) {
        int id = c.getColumnIndex("id");
        int date = c.getColumnIndex("date");
        int d_count = c.getColumnIndex("d_count");
// Привести в проядок
        do {
//распарсить дату до числа 2016-6-17
            String d = c.getString(date);
            String[] pd = d.split("-", 3);
            //mn_ day in history_for_fender
            mn_.add(pd[2]);
            //cl_ weight of gay(color_weight)
            cl_.add(c.getInt(d_count));
        }
        while (c.moveToNext());
        c.close();

    }
    Log.i("Build_mn_", String.valueOf(mn_.size()));
    Log.i("Build_mn", String.valueOf(mn_));
    Log.i("Build_cl_", String.valueOf(cl_));

}


private void init_mass2(Context context, int month, int y){
    List<Constructor_dayWeight> listDayWeight=new ExexDB().init_mass(context,month,y);
    for(Constructor_dayWeight elt:listDayWeight){
        mn_.add(elt.day);
        cl_.add(elt.weight);
    }
    Log.i("Build_mn_", String.valueOf(mn_.size()));
    Log.i("Build_mn", String.valueOf(mn_));
    Log.i("Build_cl_", String.valueOf(cl_));

}

    int[] convert_mass_for_render(Context context, List list_d, int month, int year){

        init_mass(context,month,year);
        List<String> day_for_render=mn_;
        List<Integer> weight_of_day=cl_;

        int[] mass_pic=new int[list_d.size()];

        for(int i=0;i<day_for_render.size();i++){

            Log.i("Build_conv_mass_pos",String.valueOf(list_d.indexOf(day_for_render.get(i))));

            switch (weight_of_day.get(i)){
                case 1:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.minday;
                break;
                case 2:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.minday;
                    break;
                case 3:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.minday;
                    break;
                case 4:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.minday;
                    break;
                case 5:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.minday;
                    break;
                case 6:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.minday;
                    break;

                case 7:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;

                case 8:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;
                case 9:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;
                case 10:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;
                case 11:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;
                case 12:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;
                case 13:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.midday;
                    break;

                case 14:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.maxday;
                    break;
                case 15:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.maxday;
                    break;
                case 16:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.maxday;
                    break;
                case 17:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.maxday;
                    break;
                case 18:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.maxday;
                    break;
                case 19:
                    mass_pic[list_d.indexOf(day_for_render.get(i))]=R.drawable.maxday;
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
