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

    // получаю по дате массив
    //[397, 19:00, 20:00, лукьянчикова ирина, , true]
    List<String> l_clients_of_day(Context ct, String ddat) {
//ddat="2016-4-12";
        // read db
        List<String> sqldat = new ArrayList<>();
        mDbHelper = new db(ct);
        mDbHelper.getWritableDatabase();
        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

        Cursor c = db1.rawQuery("SELECT * FROM clients where date like '" + ddat
                + "' order by time1 asc", null);
        if (c.moveToFirst()) {
            int id = c.getColumnIndex("_id");
            int time1 = c.getColumnIndex("time1");
            int time2 = c.getColumnIndex("time2");
            int name = c.getColumnIndex("name");
            // int date = c.getColumnIndex("date");
            int pay = c.getColumnIndex("pay");
            int flag = c.getColumnIndex("visit");
            // int sf_num = c.getColumnIndex("sf_num");

            do {
                sqldat.add(c.getString(id));
                if (c.getString(time1).length() == 8) {
                    sqldat.add(c.getString(time1).substring(0, c.getString(time1).length() - 3));
                } else {
                    sqldat.add(c.getString(time1).substring(0, c.getString(time1).length() - 7));
                }
                if (c.getString(time2).length() == 8) {
                    sqldat.add(c.getString(time2).substring(0, c.getString(time2).length() - 3));
                } else {
                    sqldat.add(c.getString(time2).substring(0, c.getString(time2).length() - 7));
                }

                sqldat.add(c.getString(name));
                sqldat.add(c.getString(pay));
                sqldat.add(c.getString(flag));
                // sqldat.add(c.getString(sf_num).toString());
                // + c.getString(date).toString());
            }
            while (c.moveToNext());
            c.close();
            db1.close();
        }
      // Log.i("2_read", String.valueOf(sqldat));
        return sqldat;
    }

}
