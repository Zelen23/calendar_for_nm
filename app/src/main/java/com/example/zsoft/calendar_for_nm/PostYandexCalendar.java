package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.zsoft.calendar_for_nm.json.CreateEventJson;
import com.example.zsoft.calendar_for_nm.json.CreateLayerJson;
import com.example.zsoft.calendar_for_nm.json.DeleteEventJson;
import com.example.zsoft.calendar_for_nm.json.Services;
import com.example.zsoft.calendar_for_nm.json.responseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostYandexCalendar {
    private static NetworkingYandex networkingYandex;
    private static Retrofit retrofit;
    Context context;
    GsonBuilder gsonBuilder = new GsonBuilder();

    Gson gson = gsonBuilder.create();
    public PostYandexCalendar(Context context) {
        this.context = context;
    }

   public OkHttpClient.Builder client(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String ContentType         = sharedPreferences.getString("ContentType","application/json");
        final Integer xyandexmayauid     = sharedPreferences.getInt("x-yandex-maya-uid",569420386);
        final String xyandexmayacid      = sharedPreferences.getString("x-yandex-maya-cid","MAYA-16891790-1578570738675");
        final String xyandexmayackey     = sharedPreferences.getString("x-yandex-maya-ckey","OzUM3DgHei8N64Mr7PoB0cM0hFlwOFl6JgI71uG8ue07ZNYzOJ1farW69DQXCsaq11HBRGEFvWSGr7gy5YHH6w==");
        final String xyandexmayalocale   = sharedPreferences.getString("x-yandex-maya-locale","ru");
        final String xyandexmayatimezone = sharedPreferences.getString("x-yandex-maya-timezone","Europe/Moscow");
        final String pr_token          = sharedPreferences.getString("token","null");

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("ContentType", ContentType)
                        .addHeader("Authorization", "OAuth "+pr_token)
                        .addHeader("x-yandex-maya-uid", String.valueOf(xyandexmayauid))
                        .addHeader("x-yandex-maya-cid", xyandexmayacid)
                        .addHeader("x-yandex-maya-ckey", xyandexmayackey)
                        .addHeader("x-yandex-maya-locale", xyandexmayalocale)
                        .addHeader("x-yandex-maya-timezone", xyandexmayatimezone)
                        .build();
                return chain.proceed(request);
            }
        });

       return httpClient;
    }

   public void sendEvent(CreateEventJson querry) {
       SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.getInt("layerId",-1)!=-1) {



            /*проблема в отправляемых данных*/
            String way = "/sdcard/sdcard/temp/";
            String name = "syncFile.json";

            final File file = new File(way + name);

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://calendar.yandex.ru/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client().build())
                    .build();

            networkingYandex = retrofit.create(NetworkingYandex.class);

            Call<responseModel> call = networkingYandex.createEvent(querry);
            call.enqueue(new Callback<responseModel>() {
                @Override
                public void onResponse(Call<responseModel> call, Response<responseModel> response) {

                    if (response.isSuccessful()) {
                        /* в файле
                         * номер имя дата и время
                         * найти в базе client.id*/

                            new Services(context).setUidtoOrder(response.body());
                            file.delete();

                    } else {
                    }

                }

                @Override
                public void onFailure(Call<responseModel> call, Throwable t) {
                    Log.i("onFailure ", "failure " + t);
                }
            });

            Log.i("PostYandexCalendar info", "---");
        }else{

            Toast.makeText(
                    context,
                    context.getResources().getText(R.string.errCreateCld),
                    Toast.LENGTH_LONG )
                    .show();
        }
    }

   public void deleteEvent(final DeleteEventJson querry) {

 Runnable runnable =new Runnable() {
     @Override
     public void run() {
         try {
             Thread.sleep(3000);
             /*проблема в отправляемых данных*/
             String way = "/sdcard/sdcard/temp/";
             String name = "syncFileDelete.json";

             final File file = new File(way + name);

             String namecrt = "syncFile.json";
             final File filecrt = new File(way + namecrt);

             if(filecrt.exists()){
                 CreateEventJson createEventJson=gson.fromJson(new HelperData()
                         .readToStream(way+namecrt).toString(),CreateEventJson.class);

                 sendEvent(createEventJson);

             }

             if(querry!=null){
                 retrofit = new Retrofit.Builder()
                         .baseUrl("https://calendar.yandex.ru/")
                         .addConverterFactory(GsonConverterFactory.create())
                         .client(client().build())
                         .build();

                 networkingYandex = retrofit.create(NetworkingYandex.class);

                 Call<responseModel> call = networkingYandex.deleteEvent(querry);
                 call.enqueue(new Callback<responseModel>() {
                     @Override
                     public void onResponse(Call<responseModel> call, Response<responseModel> response) {

                         if(response.isSuccessful()){

                                 file.delete();
                             Log.i("deleteEvent  " , String.valueOf(response.raw()));
                         }else{

                         }

                     }

                     @Override
                     public void onFailure(Call<responseModel> call, Throwable t) {
                         Log.i("onFailureDelete " ,"failure " + t);
                     }
                 });
             }


         } catch (InterruptedException e) {
             e.printStackTrace();
         }

     }

 };
       Thread thread=new Thread(runnable);
       thread.start();

    }

   public void createLayer(CreateLayerJson querry) {
       final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);



        retrofit = new Retrofit.Builder()
                .baseUrl("https://calendar.yandex.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client().build())
                .build();

        networkingYandex = retrofit.create(NetworkingYandex.class);

        Call<responseModel> call = networkingYandex.createLayer(querry);
        call.enqueue(new Callback<responseModel>() {
            @Override
            public void onResponse(Call<responseModel> call, Response<responseModel> response) {

                if(response.isSuccessful()){

                    //чекать ответ ОК
                    if(response.body().getModels().get(0).getStatus().equals("ok")){
                        int layerID=response.body().getModels().get(0).getData().getLayerId();
                        Log.i("createLayer resp", String.valueOf(layerID));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("layerId", layerID);
                        editor.commit();
                    } else{
                        Toast.makeText(context,context.getResources().getText(R.string.failCreateLayer),Toast.LENGTH_LONG).show();}


                }else{
                }

            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                Log.i("onFailure " ,"failure " + t);
            }
        });

        Log.i("createLayer info","---");
    }
}