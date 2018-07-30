package com.example.zsoft.calendar_for_nm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by adolf on 17.03.2018.
 * раздел настроек по группам
 * реализовать фрагментами
 * для маленьких экранов переходом
 * для больших двумя фрагментами
 *
 * 1я группа- стили
 * -размер
 * -цвет
 * -фон
 * автопименение при выходе из раздела настроек
 *
 */

public class preference  extends PreferenceActivity{


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.pref_head,target);
    }


    @Override
    protected boolean isValidFragment(String fragmentName) {

        //return  PrefFragment.class.getName().equals(fragmentName);
        return true;
    }


    // фрагменты настоек

    public static class PrefFragment extends PreferenceFragment{


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i("pref__psuse","" +
                    "Pause");

        }
    }

    public static class PrefUserSettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_user);

        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i("prefusr__psuse","" +
                    "Pause");

        }

    }

    public static class PrefClearDB extends PreferenceFragment{

        public static final int IDM_RETURN = 103;
        public static final int IDM_DELETE_BUP = 104;
        String head_gr;

        TextView textView,countOrd;
        Button b;
        SeekBar intervalClean;
        ExecDB execDB=new ExecDB();
        ProgressBar prb;
        ListView lw2;

        private db mDbHelper;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
             View v=inflater.inflate(R.layout.pref_clear_db,container,false);
             b=v.findViewById(R.id.button22);
             b.setVisibility(View.INVISIBLE);
             intervalClean=v.findViewById(R.id.seekBar);
             textView=v.findViewById(R.id.textView6);
             prb=v.findViewById(R.id.progressBar);
             lw2=v.findViewById(R.id.ListView);
             countOrd=v.findViewById(R.id.sizeDBVal);
             countOrd.setText(""+execDB.get_count("clients",getActivity()));





            final String dateFirstWrite=execDB.MinOrMax(getActivity(),"min");
            final String dateLastWrite=execDB.MinOrMax(getActivity(),"max");
            String toDay= MainActivity.year + "-" + MainActivity.mns + "-" + MainActivity.today;
            final HelperData helperData=new HelperData();
            view_buckUp();

            //intervalClean.setMin(1);
            intervalClean.setMax(
                    helperData.Intrval_to_seekBar(dateFirstWrite,dateLastWrite));
            intervalClean.setProgress(
                    helperData.Intrval_to_seekBar(dateFirstWrite,toDay));
            textView.setText(helperData.fromIntToDateString(
                    dateFirstWrite,intervalClean.getProgress()));

            intervalClean.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    textView.setText(helperData.fromIntToDateString(
                            dateFirstWrite,seekBar.getProgress()));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    textView.setText(helperData.fromIntToDateString(
                            dateFirstWrite,seekBar.getProgress()));
                    b.setVisibility(View.VISIBLE);

                }
            });


            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cleandb catTask = new cleandb();
                    catTask.execute();

                }
            });

            return v;
        }
        // st_1() копирую дневные каунты во временную таблицу history to temp
        public class cleandb extends AsyncTask<Void, Integer, List<String>> {

            long st,fn;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prb.setMax(execDB.get_count("history",getActivity()));

                prb.setVisibility(View.VISIBLE);
                backup_2();

                st=System.currentTimeMillis();
            }

            @Override
            protected List<String> doInBackground(Void... lt) {

                //  tt.reads(common.this);
                //   publishProgress( tt.reads(common.this));
                int ii = 0;

                mDbHelper = new db(getActivity());
                mDbHelper.getWritableDatabase();
                SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

                ContentValues val = new ContentValues();
                Cursor c = db1.rawQuery("SELECT * FROM history", null);
                db1.beginTransaction();
                if (c.moveToFirst()) {
                    int id = c.getColumnIndex("id");
                    int date = c.getColumnIndex("date");
                    int d_count = c.getColumnIndex("d_count");

                    do {
                        ii++;
                        publishProgress(ii++);
                        val.put(db.id_his_t, c.getString(id));
                        val.put(db.date_his_t, c.getString(date));
                        val.put(db.count_his_t, c.getString(d_count));

                        db1.insert("temp_hist", null, val);

                    }
                    while (c.moveToNext());
                    c.close();
                }
                db1.setTransactionSuccessful();
                db1.endTransaction();

                // TimeUnit.SECONDS.sleep(1);
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                //adapt.add(values[0]);

                prb.setProgress(values[0]);


            }

            @Override
            protected void onPostExecute(List<String> aVoid) {
                super.onPostExecute(aVoid);
                fn=System.currentTimeMillis()-st;
                prb.setProgress(0);
                Toast.makeText(getActivity(),
                        "1-st_step table have " +execDB.get_count
                                ("history",getActivity())+" rows\n"+fn,Toast.LENGTH_SHORT);
                Log.i("CleanDb1stStep", String.valueOf(aVoid));
                new clean_db2().execute();

            }


        }
        // st_2tr копирую всех посетителей во временную таблицу user to temp
        // удаляю все значения из clients кроме последних deleterow()
        public class clean_db2 extends AsyncTask<Void, Integer, Void> {

            long st,fn;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prb.setMax(execDB.get_count("user",getActivity()));
                st=System.currentTimeMillis();
                prb.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                int ii = 0;

                mDbHelper = new db(getActivity());
                mDbHelper.getWritableDatabase();
                SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

                ContentValues val = new ContentValues();
                Cursor c = db1.rawQuery("SELECT id,pk_num,count FROM user", null);
                db1.beginTransaction();
                if (c.moveToFirst()) {
                    int id = c.getColumnIndex("id");
                    int pk_num = c.getColumnIndex("pk_num");
                    int count = c.getColumnIndex("count");

                    do {
                        ii++;
                        publishProgress(ii++);

                        val.put(db.ID_TEMP,c.getString(id));
                        val.put(db.CONTACT_TEMP,c.getString(pk_num));
                        val.put(db.PAY_TEMP,c.getString(count));
                        val.put(db.NAME_TEMP,"--");

                        db1.insert("temp", null, val);
                    }
                    while (c.moveToNext());
                    c.close();
                }
                db1.setTransactionSuccessful();
                db1.endTransaction();
                db1.close();
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                prb.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                fn=System.currentTimeMillis()-st;
                Toast.makeText(getActivity(),"2-nd_step_table have "+execDB.get_count(
                       "user",getActivity())+" rows \n"+fn,Toast.LENGTH_SHORT);
                prb.setProgress(0);
                Log.i("CleanDb2ndStep", textView.getText().toString());
                new ExecDB().CleanDBofDate(getActivity(),textView.getText().toString());
                new upd_user().execute();

            }
        }
        // st_3 tr переписываю users
        public class upd_user extends AsyncTask<Void, Integer, Void>{

            long st,fn;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prb.setMax(execDB.get_count("temp",getActivity()));

                prb.setVisibility(View.VISIBLE);
                st=System.currentTimeMillis();

            }

            @Override
            protected Void doInBackground(Void... voids) {
                int ii = 0;

                mDbHelper = new db(getActivity());
                mDbHelper.getWritableDatabase();
                SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
                db1.beginTransaction();
                ContentValues val = new ContentValues();
                Cursor c = db1.rawQuery("SELECT * FROM temp", null);

                if (c.moveToFirst()) {
                    int id = c.getColumnIndex("_id");
                    int pk_num = c.getColumnIndex("sf_num");
                    int count = c.getColumnIndex("pay");

                    do {
                        ii++;
                        publishProgress(ii++);
                        //  val.put(db.ID_TEMP,c.getString(id));
                        //  val.put(db.CONTACT_TEMP,c.getString(pk_num));
                        val.put(db.count_us,c.getString(count));
                        db1.update("user",val,"pk_num = '"+c.getString(pk_num)+"'",null);


                    }
                    while (c.moveToNext());
                    c.close();
                }
                db1.setTransactionSuccessful();
                db1.endTransaction();
                db1.close();
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                prb.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                fn=System.currentTimeMillis()-st;
               Toast.makeText(getActivity(),"3-rd_step_table have "+execDB.get_count(
                       "temp",getActivity())+" rows \n"+fn,Toast.LENGTH_SHORT);
                execDB. deleterow(getActivity(), "temp","0 or _id!=0");
                prb.setProgress(0);
                view_buckUp();
                Toast.makeText(getActivity(),"выполннено",Toast.LENGTH_SHORT).show();

            }


        }


        void backup_2(){
            InputStream in=null;
            OutputStream out=null;

            //SimpleDateFormat datinback=new SimpleDateFormat("YYYY:MM:hh:mm");

            Date now = Calendar.getInstance().getTime();
            // косяк из-за текущего формата на устройстве
            // String nowDate = String.valueOf(datinback.format(now));


            String nowDate = String.valueOf(now);

            try {
                File mdr=new File("/sdcard/archive");
                if(!mdr.exists()){
                    mdr.mkdirs();
                }

                in= new FileInputStream("/sdcard/sdcard/st.db");
                out= new FileOutputStream("/sdcard/archive/st_"+nowDate+".db");
                byte buff []=new byte[1024];
                int read;

                try {
                    while ((read=in.read(buff))!=-1){
                        out.write(buff,0,read);
                    }
                    in.close();
                    in=null;

                    out.flush();
                    out.close();
                    out=null;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        void view_buckUp(){
        /*показать папку archive*/


            lw2.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                    View("/sdcard/archive")));
        /*
        lwView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(clean_db.this, View("/sdcard/archive").get(i)+" ",Toast.LENGTH_SHORT).show();
            }
        });
        */
            registerForContextMenu(lw2);
            lw2.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {



                head_gr=null;

                AdapterView.AdapterContextMenuInfo inf =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;
                head_gr=View("/sdcard/archive").get(inf.position).toString();
                menu.setHeaderTitle(View("/sdcard/archive").get(inf.position).toString());
                menu.add(menu.NONE, IDM_RETURN, menu.NONE, "Восстановить");
                menu.add(menu.NONE, IDM_DELETE_BUP, menu.NONE, "Удалить точку");



        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {




            switch (item.getItemId()) {

                case IDM_RETURN:
                    return_copy("/sdcard/sdcard/",head_gr);

                    break;

                case IDM_DELETE_BUP:
                    del_buckup("/sdcard/archive/",head_gr);
                    view_buckUp();


                    break;
            }
            return super.onContextItemSelected(item);
        }
        public  List View(String way){
            File dir=new File(way);
            List fld= new ArrayList<ArrayList>();

            if(dir.isDirectory()){
                File[] lfld=dir.listFiles();
                fld.add("<<<");
                for(File elt:lfld){
                    fld.add(elt.getName());

                }
            }
            return fld;
        }
        //clients чищу таблицу


        public void del_buckup(String way,String fl){
            File fld=new File(way+fl);
            Boolean delete=fld.delete();

        }

        public void return_copy(String way,String fl){
        /*копируем в папку sdcard
        * удаляем st.db
        * переимеовываеи то что скопировали в st.db*/

            InputStream in=null;
            OutputStream out=null;
            del_buckup("/sdcard/sdcard/","st.db");
            try {
                File mdr=new File(way);
                if(!mdr.exists()){
                    mdr.mkdirs();
                }

                in= new FileInputStream("/sdcard/archive/"+fl);
                out= new FileOutputStream("/sdcard/sdcard/st.db");
                byte buff []=new byte[1024];
                int read;

                try {
                    while ((read=in.read(buff))!=-1){
                        out.write(buff,0,read);
                    }
                    in.close();
                    in=null;

                    out.flush();
                    out.close();
                    out=null;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }






}
