package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.util.Log;

import com.example.zsoft.calendar_for_nm.json.CreateEventJson;
import com.example.zsoft.calendar_for_nm.json.DeleteEventJson;
import com.example.zsoft.calendar_for_nm.json.Services;
import com.example.zsoft.calendar_for_nm.json.responseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

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

   public void sendEvent(CreateEventJson querry) {

       /*проблема в отправляемых данных*/
       String way = "/sdcard/sdcard/temp/";
       String name = "syncFile.json";

       final File file = new File(way + name);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://calendar.yandex.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkingYandex = retrofit.create(NetworkingYandex.class);

        Call<responseModel> call = networkingYandex.createEvent(querry);
        call.enqueue(new Callback<responseModel>() {
            @Override
            public void onResponse(Call<responseModel> call, Response<responseModel> response) {

                    if(response.isSuccessful()){
                        /* в файле
                         * номер имя дата и время
                         * найти в базе client.id*/

                        new Services(context).setUidtoOrder(response.body());
                        file.delete();

                    }else{
                    }

            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                Log.i("onFailure " ,"failure " + t);
            }
        });

Log.i("PostYandexCalendar info","---");
    }

   public void deleteEvent(final DeleteEventJson querry) {

 Runnable runnable =new Runnable() {
     @Override
     public void run() {
         try {
             Thread.sleep(1000);
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
                         .build();

                 networkingYandex = retrofit.create(NetworkingYandex.class);

                 Call<responseModel> call = networkingYandex.deleteEvent(querry);
                 call.enqueue(new Callback<responseModel>() {
                     @Override
                     public void onResponse(Call<responseModel> call, Response<responseModel> response) {

                         if(response.isSuccessful()){

                             file.delete();

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
}