package com.example.zsoft.calendar_for_nm;

import android.util.Log;

import com.example.zsoft.calendar_for_nm.NetworkingYandex;
import com.example.zsoft.calendar_for_nm.json.responseModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostYandexCalendar {

    private static NetworkingYandex networkingYandex;
    private static Retrofit retrofit;

   public void sendEvent(String querry) {


        retrofit = new Retrofit.Builder()
                .baseUrl("https://calendar.yandex.ru:443")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkingYandex = retrofit.create(NetworkingYandex.class);

        Call<responseModel> call = networkingYandex.getData(querry);


       call.enqueue(new Callback<responseModel>() {
            @Override
            public void onResponse(Call<responseModel> call, Response<responseModel> response) {


                try {
                    Log.i("PostYandexCalendar", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<responseModel> call, Throwable t) {
                Log.i("onFailure " ,"failure " + t);
            }
        });

Log.i("PostYandexCalendar info","---");
    }
}