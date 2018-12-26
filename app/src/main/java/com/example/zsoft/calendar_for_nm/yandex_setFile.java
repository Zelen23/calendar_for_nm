package com.example.zsoft.calendar_for_nm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.json.Link;
import com.yandex.disk.rest.json.Resource;

import java.io.File;
import java.io.IOException;


/* Скачать или отправить
* должна быть проверка
* на свежесть базы
* выкидывать окно с описанием базы которая лежит на диске
* --версия базы
* --кол-во записей
* --дата последнего изменния
*
*  в окне 3 кнопки скачать/отправить/отменить
*   если отправить то все что есть в задании на отправку
* */


 class yandex_setFile extends AsyncTask <Void,Void,String>  {
    Context context;
    String toDiskfile;
    File fromSDFile;

    public yandex_setFile(Context context,String toDiskfile,File fromSDFile) {
        this.context = context;
        this.toDiskfile=toDiskfile;
        this.fromSDFile=fromSDFile;
    }

    @Override
    protected String doInBackground(Void... voids) {
        SharedPreferences sharedPreferences;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        Credentials credentials = new Credentials(
                sharedPreferences.getString("login_hint","DskDrv@yandex.ru"),
                sharedPreferences.getString("token","null"));
        RestClient client = RestClientUtil.getInstance(credentials);
        try {
            String ss="";
            //"disk:/sync/st.db"
            Link link = client.getUploadLink( toDiskfile, true);
            client.uploadFile(link, true, fromSDFile,
                    new ProgressListener() {
                        @Override
                        public void updateProgress(long loaded, long total) {

                        }

                        @Override
                        public boolean hasCancelled() {
                            return false;
                        }
                    });

            Log.i("inf",ss);
            return ss;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerIOException e) {
            e.printStackTrace();
        } catch (WrongMethodException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }

        return null;
    }




}
 class yandex_getFile extends AsyncTask <Void,Void,String>  {
    Context context;
    String filename;
    File dwnLoadFile;
    public yandex_getFile(Context context,String filename,File dwnLoadFile) {
        this.context = context;
        this.filename=filename;
        this.dwnLoadFile=dwnLoadFile;
    }

    @Override
    protected String doInBackground(Void... voids) {
        SharedPreferences sharedPreferences;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        Credentials credentials = new Credentials(
                sharedPreferences.getString("login_hint","DskDrv@yandex.ru"),
                sharedPreferences.getString("token","null"));
        RestClient client = RestClientUtil.getInstance(credentials);
        try {

            ProgressListener progressListener=new ProgressListener() {
                @Override
                public void updateProgress(long loaded, long total) {
                }

                @Override
                public boolean hasCancelled() {
                    return false;
                }
            };
            client.downloadFile(filename,dwnLoadFile,
                    progressListener);


            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerIOException e) {
            e.printStackTrace();
        } catch (WrongMethodException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }

        return null;
    }


     @Override
     protected void onPostExecute(String s) {
         super.onPostExecute(s);



     }
 }

 class yandex_read_meta{



 }