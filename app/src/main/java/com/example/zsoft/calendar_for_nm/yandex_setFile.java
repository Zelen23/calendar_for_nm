package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.ResourcesHandler;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.json.Link;
import com.yandex.disk.rest.json.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

     File tempFile= new File("/sdcard/sdcard/temp/st.db");

     // скачиваю файл во временную папку
     // после скачивания заменяю
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

        tempFile.delete();

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
            client.downloadFile(filename,tempFile,
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

         HelperData helperData=new HelperData();
         helperData.return_copy(tempFile,dwnLoadFile);

         Log.i("yandex_getFile",tempFile.getAbsolutePath()+"  "+dwnLoadFile.getAbsolutePath());

         super.onPostExecute(s);



     }
 }

 class yandex_aps extends AsyncTask<Void,Void,List> {


    Context context;
    String dir;


    public yandex_aps(Context context, String dir) {
        this.context = context;
        this.dir= dir;
    }



     @Override
    protected List doInBackground(Void... voids) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String login_hint = sharedPreferences.getString("login_hint","DskDrv@yandex.ru");
        String token      = sharedPreferences.getString("token","null");

        Credentials credentials = new Credentials(login_hint,token);
        RestClient client       = RestClientUtil.getInstance(credentials);
        final ArrayList list =new ArrayList();
        try {
            String ss;

            Resource resource=client.getResources(new ResourcesArgs.Builder()
                    .setPath(dir)
                    .setParsingHandler(new ResourcesHandler() {
                        @Override
                        public void handleItem(Resource item) {
                            list.add(new String(item.getName()));
                        }
                    })
                    .build());

            ss = client.getResources(new ResourcesArgs.Builder()
                    .setPath(dir)
                    .setLimit(10)
                    .build()).getResourceList().getItems().toString();

            boolean HttpCode= client.getResources(new ResourcesArgs.Builder()
                    .setPath(dir)
                    .build()).isDir();



            return list;
        } catch (IOException e) {
            list.add(e);
            e.printStackTrace();
        } catch (ServerIOException e) {
            list.add(e);
            e.printStackTrace();
        }
        return list;
    }

     @Override
     protected void onProgressUpdate(Void... values) {
         super.onProgressUpdate(values);

     }
     @Override
     protected void onPostExecute(List list) {
         super.onPostExecute(list);

         Toast.makeText(context,list.toString(),Toast.LENGTH_SHORT).show();

     }


}

 class yandex_read_meta{



 }