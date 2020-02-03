package com.example.zsoft.calendar_for_nm;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsoft.calendar_for_nm.json.CreateLayerJson;
import com.example.zsoft.calendar_for_nm.json.JsonFile;
import com.example.zsoft.calendar_for_nm.json.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
 * <p>
 * 1я группа- стили
 * -размер
 * -цвет
 * -фон
 * автопименение при выходе из раздела настроек
 */

public class preference extends PreferenceActivity {
    SharedPreferences sharedPreferences;

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
        loadHeadersFromResource(R.xml.pref_head, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {

        //return  PrefFragment.class.getName().equals(fragmentName);
        return true;
    }

    // фрагменты настоек
    public static class PrefFragment extends PreferenceFragment {


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i("pref__psuse", "" +
                    "Pause");

        }
    }

    public static class PrefUserSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_user);

        }

        @Override
        public void onPause() {
            super.onPause();
            Log.i("prefusr__psuse", "" +
                    "Pause");

        }

    }

    public static class PrefClearDB extends PreferenceFragment {

        public static final int IDM_RETURN = 103;
        public static final int IDM_DELETE_BUP = 104;
        String head_gr;

        TextView textView, countOrd, verVal;
        Button b;
        SeekBar intervalClean;
        ExecDB execDB = new ExecDB();
        ProgressBar prb;
        ListView lw2;

        private db mDbHelper;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final String dateFirstWrite = execDB.MinOrMax(getActivity(), "min");
            final String dateLastWrite = execDB.MinOrMax(getActivity(), "max");
            int i = MainActivity.mns + 1;
            String toDay = MainActivity.year + "-" + i + "-" + MainActivity.today;
            final HelperData helperData = new HelperData();

            View v = inflater.inflate(R.layout.pref_clear_db, container, false);

            b = v.findViewById(R.id.button22);
            b.setVisibility(View.INVISIBLE);
            intervalClean = v.findViewById(R.id.seekBar);
            textView = v.findViewById(R.id.textView6);
            prb = v.findViewById(R.id.progressBar);
            prb.setVisibility(View.INVISIBLE);
            lw2 = v.findViewById(R.id.ListView);
            countOrd = v.findViewById(R.id.sizeDBVal);
            countOrd.setText("" + execDB.get_count("clients", getActivity()));
            verVal = v.findViewById(R.id.VerVal);
            verVal.setText(toDay);


            view_buckUp();

            //intervalClean.setMin(1);
            if (dateFirstWrite != null) {
                intervalClean.setMax(
                        helperData.Intrval_to_seekBar(dateFirstWrite, dateLastWrite));
                intervalClean.setProgress(
                        helperData.Intrval_to_seekBar(dateFirstWrite, toDay));
                textView.setText(helperData.fromIntToDateString(
                        dateFirstWrite, intervalClean.getProgress()));
                intervalClean.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        textView.setText(helperData.fromIntToDateString(
                                dateFirstWrite, seekBar.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView.setText(helperData.fromIntToDateString(
                                dateFirstWrite, seekBar.getProgress()));
                        b.setVisibility(View.VISIBLE);

                    }
                });
            }


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

            long st, fn;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prb.setMax(execDB.get_count("history", getActivity()));

                prb.setVisibility(View.VISIBLE);
                backup_2();

                st = System.currentTimeMillis();
            }

            @Override
            protected List<String> doInBackground(Void... lt) {

                //  tt.reads(common.this);
                //   publishProgress( tt.reads(common.this));

                /*
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

                */
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
                fn = System.currentTimeMillis() - st;
                prb.setProgress(0);
                Toast.makeText(getActivity(),
                        "1-st_step table have " + execDB.get_count
                                ("history", getActivity()) + " rows\n" + fn, Toast.LENGTH_SHORT);
                Log.i("CleanDb1stStep", String.valueOf(aVoid));
                new clean_db2().execute();

            }


        }

        // st_2tr копирую всех посетителей во временную таблицу user to temp
        // удаляю все значения из clients кроме последних deleterow()
        public class clean_db2 extends AsyncTask<Void, Integer, Void> {

            long st, fn;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prb.setMax(execDB.get_count("user", getActivity()));
                st = System.currentTimeMillis();
                prb.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                int ii = 0;

                mDbHelper = new db(getActivity());
                mDbHelper.getWritableDatabase();
                SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

                ContentValues val = new ContentValues();
                Cursor c = db1.rawQuery("SELECT id,pk_num,count,last FROM user", null);
                db1.beginTransaction();
                if (c.moveToFirst()) {
                    int id = c.getColumnIndex("id");
                    int pk_num = c.getColumnIndex("pk_num");
                    int count = c.getColumnIndex("count");
                    int last = c.getColumnIndex("last");

                    do {
                        ii++;
                        publishProgress(ii++);

                        val.put(db.ID_TEMP, c.getString(id));
                        val.put(db.CONTACT_TEMP, c.getString(pk_num));
                        val.put(db.PAY_TEMP, c.getString(count));
                        val.put(db.DATE_TEMP, c.getString(last));
                        val.put(db.NAME_TEMP, "--");

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
                fn = System.currentTimeMillis() - st;
                Toast.makeText(getActivity(), "2-nd_step_table have " + execDB.get_count(
                        "user", getActivity()) + " rows \n" + fn, Toast.LENGTH_SHORT);
                prb.setProgress(0);
                Log.i("CleanDb2ndStep", textView.getText().toString());
                // удалить записи clients старше чем указано
                new ExecDB().CleanDBofDate(getActivity(), textView.getText().toString());
                new upd_user().execute();

            }
        }

        // st_3 tr переписываю users
        public class upd_user extends AsyncTask<Void, Integer, Void> {

            long st, fn;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prb.setMax(execDB.get_count("temp", getActivity()));

                prb.setVisibility(View.VISIBLE);
                st = System.currentTimeMillis();

            }

            @Override
            protected Void doInBackground(Void... voids) {
                int ii = 0;

                mDbHelper = new db(getActivity());
                mDbHelper.getWritableDatabase();
                SQLiteDatabase db1 = mDbHelper.getWritableDatabase();
                db1.beginTransaction();
                ContentValues val = new ContentValues();
                String s = "select sf_num,pay,date from 'temp'";
                Cursor c = db1.rawQuery(s, null);

                if (c.moveToFirst()) {
                    int id = c.getColumnIndex("_id");
                    int pk_num = c.getColumnIndex("sf_num");
                    int count = c.getColumnIndex("pay");
                    int date = c.getColumnIndex("date");

                    do {
                        ii++;
                        publishProgress(ii++);
                        //  val.put(db.ID_TEMP,c.getString(id));
                        val.put(db.last_us, c.getString(date));
                        val.put(db.count_us, c.getString(count));
                        db1.update("user", val, "pk_num = '" + c.getString(pk_num) + "'", null);

                        Log.i("upd_user", val.toString());

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
                fn = System.currentTimeMillis() - st;
                Toast.makeText(getActivity(), "3-rd_step_table have " + execDB.get_count(
                        "temp", getActivity()) + " rows \n" + fn, Toast.LENGTH_SHORT);
                execDB.deleterow(getActivity(), "temp", "0 or _id!=0");
                prb.setProgress(0);
                prb.setVisibility(View.INVISIBLE);
                view_buckUp();
                Toast.makeText(getActivity(), "выполннено", Toast.LENGTH_SHORT).show();

            }


        }


        void backup_2() {
            InputStream in = null;
            OutputStream out = null;

            //SimpleDateFormat datinback=new SimpleDateFormat("YYYY:MM:hh:mm");

            Date now = Calendar.getInstance().getTime();
            // косяк из-за текущего формата на устройстве
            // String nowDate = String.valueOf(datinback.format(now));


            String nowDate = String.valueOf(now);

            try {
                File mdr = new File("/sdcard/archive");
                if (!mdr.exists()) {
                    mdr.mkdirs();
                }

                in = new FileInputStream("/sdcard/sdcard/st.db");
                out = new FileOutputStream("/sdcard/archive/st_" + nowDate + ".db");
                byte buff[] = new byte[1024];
                int read;

                try {
                    while ((read = in.read(buff)) != -1) {
                        out.write(buff, 0, read);
                    }
                    in.close();
                    in = null;

                    out.flush();
                    out.close();
                    out = null;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        void view_buckUp() {
            /*показать папку archive*/


            lw2.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
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

            head_gr = null;

            AdapterView.AdapterContextMenuInfo inf =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            head_gr = View("/sdcard/archive").get(inf.position).toString();
            menu.setHeaderTitle(View("/sdcard/archive").get(inf.position).toString());
            menu.add(menu.NONE, IDM_RETURN, menu.NONE, getResources().getString(R.string.set_DB_backup_check));
            menu.add(menu.NONE, IDM_DELETE_BUP, menu.NONE, getResources().getString(R.string.set_DB_backup_del));
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case IDM_RETURN:
                    // return_copy("/sdcard/sdcard/",head_gr);
                    // del_buckup("/sdcard/sdcard/","st.db");
                    new HelperData().return_copy(
                            new File("/sdcard/archive/" + head_gr),
                            new File("/sdcard/sdcard/st.db"));

                    break;

                case IDM_DELETE_BUP:
                    del_buckup("/sdcard/archive/", head_gr);
                    view_buckUp();


                    break;
            }
            return super.onContextItemSelected(item);
        }

        public List View(String way) {
            File dir = new File(way);
            List fld = new ArrayList<ArrayList>();

            if (dir.isDirectory()) {
                File[] lfld = dir.listFiles();
                fld.add("<<<");
                for (File elt : lfld) {
                    fld.add(elt.getName());

                }
            }
            return fld;
        }
        //clients чищу таблицу


        public void del_buckup(String way, String fl) {
            File fld = new File(way + fl);
            Boolean delete = fld.delete();

        }

        public void return_copy(String way, String fl) {
            /*копируем в папку sdcard
             * удаляем st.db
             * переимеовываеи то что скопировали в st.db*/

            InputStream in = null;
            OutputStream out = null;
            del_buckup("/sdcard/sdcard/", "st.db");
            try {
                File mdr = new File(way);
                if (!mdr.exists()) {
                    mdr.mkdirs();
                }

                in = new FileInputStream("/sdcard/archive/" + fl);
                out = new FileOutputStream("/sdcard/sdcard/st.db");
                byte buff[] = new byte[1024];
                int read;

                try {
                    while ((read = in.read(buff)) != -1) {
                        out.write(buff, 0, read);
                    }
                    in.close();
                    in = null;

                    out.flush();
                    out.close();
                    out = null;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    //https://oauth.yandex.ru/
    public static class Yandex_sync extends PreferenceFragment {

        SharedPreferences sharedPreferences;
        String pr_loginHint, pr_token, pr_client_id, pr_dbFolder, ya_getToken;
        EditText ya_id, ya_login;
        TextView token;
        Button yaBtn;
        CheckBox checkSync;
        LayoutInflater li;

        boolean pr_checkSync;
        String ContentType, Authorization, xyandexmayacid, xyandexmayackey, xyandexmayalocale, xyandexmayatimezone;
        String ya_disk_folder;
        Integer xyandexmayauid,layerId;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.ya_sync, container, false);
// SharedPreference
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            pr_loginHint = sharedPreferences.getString("login_hint", "DskDrv@yandex.ru");
            pr_token = sharedPreferences.getString("token", "null");
            pr_client_id = sharedPreferences.getString("client_id", "fc3985e6de824b35a95e56b00dd21685");
            pr_dbFolder = sharedPreferences.getString("yaFolder", "/");

            pr_checkSync = sharedPreferences.getBoolean("syncFlag", false);
            ContentType = sharedPreferences.getString("ContentType", "application/json");
            xyandexmayauid = sharedPreferences.getInt("x-yandex-maya-uid", 569420386);
            xyandexmayacid = sharedPreferences.getString("x-yandex-maya-cid", "MAYA-16891790-1578570738675");
            xyandexmayackey = sharedPreferences.getString("x-yandex-maya-ckey", "OzUM3DgHei8N64Mr7PoB0cM0hFlwOFl6JgI71uG8ue07ZNYzOJ1farW69DQXCsaq11HBRGEFvWSGr7gy5YHH6w==");
            xyandexmayalocale = sharedPreferences.getString("x-yandex-maya-locale", "ru");
            xyandexmayatimezone = sharedPreferences.getString("x-yandex-maya-timezone", "Europe/Moscow");
            layerId = sharedPreferences.getInt("layerId", -1);

            checkSync = v.findViewById(R.id.CheckSync);
// SharedPreference
            ya_id = v.findViewById(R.id.ya_ClientID);
            ya_login = v.findViewById(R.id.ya_ln);
            token = v.findViewById(R.id.ya_token);
            yaBtn = v.findViewById(R.id.ya_btn);


            ya_login.setText(pr_loginHint);
            ya_id.setText(pr_client_id);


            final File fileInfoToSDcard = new File("/sdcard/sdcard/sinfo.json");
            final String fileFromDisk = "disk:" + pr_dbFolder + "/sinfo.json";
            ya_disk_folder = "disk:" + pr_dbFolder;

            //  String s_token=pr_token;
            token.setText(pr_token);
            // если изменил токен пишу в настройки
            token.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token.getText().toString());
                    editor.commit();
                }
            });
            //  если изменил логин
            ya_login.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login_hint", ya_login.getText().toString());
                    editor.commit();
                }
            });

            if (pr_token == "null") {
                checkSync.setEnabled(false);
            }
            checkSync.setChecked(pr_checkSync);
            checkSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (checkSync.isChecked()) {

                        editor.putBoolean("syncFlag", true);
                    } else {
                        editor.putBoolean("syncFlag", false);


                    }
                    editor.commit();
                }
            });

            // получаю токен
            yaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ya_getToken = "https://oauth.yandex.ru/authorize?" +
                            "response_type=token" +
                            "&client_id=" + ya_id.getText().toString() +
                            "&device_name=NM_mobile" +
                            // "& redirect_uri=dskdrv://token" +
                            "&login_hint=" + ya_login.getText().toString() +
                            // "&scope=<запрашиваемые необходимые права>" +
                            // "&optional_scope=<запрашиваемые опциональные права>" +
                            "& force_confirm=yes" +
                            "& state=id000001" +
                            "& display=popup";

                    alert_premission(ya_getToken);
                    //  если нет календаря

                }
            });

            return v;

        }

        // получаю разрешение на доступ к диску и код для получения токена
        public void alert_premission(String surl) {

            li = LayoutInflater.from(getActivity());
            View view = li.inflate(R.layout.alert_premission, null);
            final AlertDialog.Builder ad_b = new AlertDialog.Builder(getActivity());
            ad_b.setView(view);
            final String[] s = new String[1];

            final WebView wv = view.findViewById(R.id.webview);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setSaveFormData(true);
            wv.getSettings().setBuiltInZoomControls(true);
            wv.loadUrl(surl);

            final AlertDialog alert = ad_b.create();
            alert.show();
            alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            //тут можно определить действие если пришел токен и если нет
            // нет клавы
            // refresher
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("dskdrv://token")) {
                        s[0] = url.toString();
                        alert.dismiss();
                        view.destroy();

                        //Log.i("response",s[0]);

                        token.setText(parseURL(url.toString()));

                        PostYandexCalendar postYandexCalendar = new PostYandexCalendar(getActivity());
                        postYandexCalendar.createLayer(new Services(getActivity()).createLayer(pr_loginHint));
                        checkSync.setEnabled(true);


                        return true;
                    }

                    return false;
                }
            });
            wv.clearCache(true);
            wv.clearFormData();
        }

        String parseURL(String url) {
            // dskdrv://token#
            // access_token=AQAAAAAh8KpiAASqQodGr4H4sU2qrhODN1B84NI
            // &token_type=bearer
            // &expires_in=31531472
            ArrayList list = new ArrayList<String>();
            String tok = url.substring(url.indexOf("#") + 1, url.length());
            String[] parse = tok.split("&");
            for (int i = 0; i < parse.length; i++) {
                if (parse[i].contains("error")) {
                    return parse[i].split(" error=")[1];
                } else if (parse[i].contains("access_token")) {
                    Log.i("response", parse[i].split("access_token=")[1]);
                    return parse[i].split("access_token=")[1];


                }
            }


            return null;
        }

        String comporateLastOrderWrite(long dateInJson) {


            HelperData help = new HelperData();
            //  Boolean whoEarly =help.comparateDateX(dateInJson,new Date());
            long timeLastOrd = help.tempDate(new ExecDB().database_info(getActivity()).get(0).toString());
            if (dateInJson > timeLastOrd) {
                return "На Диске лежат данные новее чем локально" +
                        "\n Нажмите Получить";
            } else {
                if (dateInJson == timeLastOrd) {
                    return "Локальные данные соответствуют данным на диске";
                } else

                    return "Локальные данные актуальнее чем на диске" +
                            "\n Нажмите Отправить";
            }

        }

        public void PushBaseAndInfoToDisc(String sync) {
            /* получить инфу из базы
            собрать в json
            отправить базу+json
            * */
// получаю данные в базе
            HelperData help = new HelperData();
            List<String> fromDbToJson = new ExecDB().database_info(getActivity());

            JsonFile jsonFile = new JsonFile();
            jsonFile.createDate = help.tempDate(help.nowDate());
            jsonFile.date_Last_write = help.tempDate(fromDbToJson.get(0));
            jsonFile.countOrders = Integer.parseInt(fromDbToJson.get(2));
            jsonFile.version_base = fromDbToJson.get(1);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

// coхраняю данные в json
            help.saveFile("/sdcard/sdcard/", "info.json", gson.toJson(jsonFile));

            new yandex_setFile(getActivity(),
                    "disk:" + sync + "/st.db",
                    new File("/sdcard/sdcard/st.db")).execute();

            new yandex_setFile(getActivity(),
                    "disk:" + sync + "/sinfo.json",
                    new File("/sdcard/sdcard/info.json")).execute();

            Log.i("pref_PushBase", "disk:" + sync + "/st.db");
        }

    }


}
