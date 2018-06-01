package com.example.zsoft.calendar_for_nm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by azelinsky on 03.05.2018.
 */

 class ExecDB {

    private db mDbHelper;

    // по дате(месяц, год) создаю лист с парами(день, кол-во записей)
    List<Constructor_dayWeight> init_mass(Context context, int month, int y) {
        mDbHelper = new db(context);
        //Application did not close the cursor or database object that was opened here
        mDbHelper.getWritableDatabase();
        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
        List<Constructor_dayWeight> dateWeight = new ArrayList<Constructor_dayWeight>() {
        };
// показывать только на выбранный месяц!!
//*создать два массива записи в день и цвет дня */
///*Запрос из истории*/


        Cursor c = db1.rawQuery("SELECT * FROM history where date " +
                "like '" + y + "-" + month + "-%'", null);

        if (c.moveToFirst()) {

            int date = c.getColumnIndex("date");
            int d_count = c.getColumnIndex("d_count");
// Привести в проядок
            do {
//распарсить дату до числа 2016-6-17
                String d = c.getString(date);
                String[] pd = d.split("-", 3);
                dateWeight.add(new Constructor_dayWeight(pd[2], c.getInt(d_count)));

            }
            while (c.moveToNext());
            c.close();

        }


        return dateWeight;
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

    // пишу в базу(дата/время1/время2/сумма/имя/Номер/таблица/ид)
    void write_orders(Context context, String dats, String times,
                      String times2, String pays, String names,
                      String conts, String table, String id) {

        Date now = Calendar.getInstance().getTime();
        String nowDate;
        nowDate = String.valueOf(now);

        mDbHelper = new db(context);
        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();

        switch (table) {
            case "clients":
                val.put(db.DATE1_COLUMN, nowDate);
                val.put(db.CONTACT_COLUMN, conts);
                val.put(db.NAME_COLUMN, names);
                val.put(db.PAY_COLUMN, pays);
                val.put(db.TIME1_COLUMN, times);
                val.put(db.TIME2_COLUMN, times2);
                val.put(db.DATE_COLUMN, dats);
                break;
            case "temp":

                val.put(db.DATE1_TEMP, nowDate);
                val.put(db.CONTACT_TEMP, conts);
                val.put(db.NAME_TEMP, names);
                val.put(db.PAY_TEMP, pays);
                val.put(db.TIME1_TEMP, times);
                val.put(db.TIME2_TEMP, times2);
                val.put(db.DATE_TEMP, dats);
                val.put(db.ID_TEMP, id);
                break;
        }
        db1.insert(table, null, val);
        Log.i("insert", "selectedDate_ord" + table);
        Log.i("time_ord", "*********" + nowDate);
        db1.close();
    }


    // удаляю строку по _id
    public boolean deleterow(Context context, String table, String id) {
        mDbHelper = new db(context);
        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
        Log.i("2_delete_row", id);
        return db1.delete(table, "_id = " + id, null) > 0;
        //return db1.delete(table, "date like'"+date+"' "+time1, null) > 0;

    }

    //получаю по id инфо
    public ArrayList<String> getLine_(Context context, String table, String ids) {
       /*вывод диалогового окна с деталями по клиенту
       * для теста номер*/
        ArrayList<String> line = new ArrayList<>();
// контекст из метода
        mDbHelper = new db(context);
        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
        Cursor c;

        switch (table) {
            case "user":
                c = db1.rawQuery("SELECT * FROM user where id= " + ids, null);
                if (c.moveToFirst()) {
                    int id = c.getColumnIndex("id");
                    int pk_num = c.getColumnIndex("pk_num");
                    int name = c.getColumnIndex("name");
                    int family = c.getColumnIndex("family");
                    int url = c.getColumnIndex("url");
                    int count = c.getColumnIndex("count");
                    int last = c.getColumnIndex("last");

                    do {
                        line.add(c.getString(id));
                        line.add(c.getString(name));
                        line.add(c.getString(family));
                        line.add(c.getString(pk_num));
                        line.add(c.getString(url));
                        line.add(c.getString(last));
                        line.add(c.getString(count));
                    }
                    while (c.moveToNext());
                    c.close();
                }
                break;

            case "clients":
                c = db1.rawQuery("SELECT * FROM clients where _id= " + ids, null);
                if (c.moveToFirst()) {
                    // int id = c.getColumnIndex("id");
                    int pk_num = c.getColumnIndex("sf_num");
                    int name = c.getColumnIndex("name");
                    int time1 = c.getColumnIndex("time1");
                    int time2 = c.getColumnIndex("time2");
                    int date = c.getColumnIndex("date");
                    int date1 = c.getColumnIndex("date1");
                    int pay = c.getColumnIndex("pay");

                    do {
                        line.add(c.getString(name));
                        line.add(c.getString(pk_num));
                        line.add(c.getString(time1));
                        line.add(c.getString(time2));
                        line.add(c.getString(date));
                        line.add(c.getString(date1));
                        line.add(c.getString(pay));
                    }
                    while (c.moveToNext());
                    c.close();
                    break;

                }
            case "temp" :
                c = db1.rawQuery("SELECT * FROM temp where _id= " + ids, null);
                if (c.moveToFirst()) {
                    // int id = c.getColumnIndex("_id");
                    int name = c.getColumnIndex("name");
                    int time1 = c.getColumnIndex("time1");
                    int time2 = c.getColumnIndex("time2");
                    int sf_num = c.getColumnIndex("sf_num");
                    int pay = c.getColumnIndex("pay");
                    int date = c.getColumnIndex("date");
                    int date1 = c.getColumnIndex("date1");
                    int visit = c.getColumnIndex("visit");

                    do {
                        //  sqldat.add(  c.getString(id).toString() );
                        line.add(c.getString(name));
                        line.add(c.getString(time1));
                        line.add(c.getString(time2));
                        line.add(c.getString(sf_num));
                        line.add(c.getString(pay));
                        line.add(c.getString(date));
                        line.add(c.getString(date1));
                        line.add(c.getString(visit));
                        // + c.getString(date).toString());
                    }

                    while (c.moveToNext());
                    c.close();
                    break;
                }
        }
        Log.i("execute_getLine", line.toString());
        return line;
    }


    public  void flag_visitOrPay(Context ct, String flag, String id,String coloumn){


        ContentValues val = new ContentValues();
        mDbHelper = new db(ct);
        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
        switch(coloumn){
            case "visit":
                val.put(db.VISIT_COLUMN,flag);
                break;
            case "pay":
                val.put(db.PAY_COLUMN,flag);
                break;
        }

        db1.update("clients",val,"_id = '"+id+"'",null);

        Log.i("execute_visit",val.toString());

        db1.close();
    }


    /*Копировать- ложу в базу строчку
     *  если нажать копировать еще раз то строчка в базе затирается
     *  where _id='' or _id>0
     *  и ложится новая
     *  что перед копированием что пер*/



}
