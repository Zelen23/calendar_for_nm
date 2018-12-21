package com.example.zsoft.calendar_for_nm;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.json.Link;

import java.io.File;
import java.io.IOException;


/**
 * Created by adolf on 23.10.2018.
 */


/*https://tech.yandex.ru/disk/api/sdk/java-docpage/*/
public class yandex_api extends AsyncTask<Void,Void,String> {


    Context context;
    public yandex_api(Context context) {
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
            String ss;
            ss = client.getResources(new ResourcesArgs.Builder()
                    .setPath(sharedPreferences.getString("yaFolder","/"))
                    .setLimit(10)

                    .build()).getResourceList().getItems().toString();

           // Log.i("inf",ss);
            return ss;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerIOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

