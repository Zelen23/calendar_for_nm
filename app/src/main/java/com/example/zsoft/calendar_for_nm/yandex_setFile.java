package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.json.Link;

import java.io.File;
import java.io.IOException;

public class yandex_setFile extends AsyncTask<Void,Void,String> {

    Context context;

    public yandex_setFile(Context context) {
        this.context = context;
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
            Link link = client.getUploadLink(
                    //sharedPreferences.getString("yaFolder","/")
                    "disk:/sync/st.db"
                    , true);
            client.uploadFile(link, true, new File("/sdcard/sdcard/st.db"),
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
