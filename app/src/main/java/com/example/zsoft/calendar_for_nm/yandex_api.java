package com.example.zsoft.calendar_for_nm;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
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


/**
 * Created by adolf on 23.10.2018.
 */


/*https://tech.yandex.ru/disk/api/sdk/java-docpage/*/
public class yandex_api extends AsyncTask<Void,Void,List> {


    Context context;


    public yandex_api(Context context) {
        this.context = context;

    }

    @Override
    protected List doInBackground(Void... voids) {
        SharedPreferences sharedPreferences;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);

        String login_hint=sharedPreferences.getString("login_hint","DskDrv@yandex.ru");
        String token=sharedPreferences.getString("token","null");
        String dir=sharedPreferences.getString("yaFolder","/");

        Credentials credentials = new Credentials(login_hint,token);

        RestClient client = RestClientUtil.getInstance(credentials);
        try {
            String ss;
            final ArrayList list=new ArrayList();
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

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServerIOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

