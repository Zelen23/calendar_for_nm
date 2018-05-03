package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azelinsky on 03.05.2018.
 */

public class ExexDB {



    public  List<String> mn_ = new ArrayList<String>();
    public List<Integer> cl_ = new ArrayList<Integer>();
    private db mDbHelper;

    void init_mass(Context context, int month, int y){
        mn_  = new ArrayList<String>();
        cl_ = new ArrayList<Integer>();

        mDbHelper = new db(context);
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
                String d = c.getString(date).toString();
                String[] pd = d.split("-", 3);
                //mn_ day in history_for_fender
                mn_.add(pd[2]);
                //cl_ weight of gay(color_weight)
                cl_.add(c.getInt(d_count));
            }
            while (c.moveToNext());
            c.close();

        }
        Log.i("1_mn_", String.valueOf(mn_.size()));
        Log.i("1_mn", String.valueOf(mn_));
        Log.i("1_cl_", String.valueOf(cl_));



    }
}
