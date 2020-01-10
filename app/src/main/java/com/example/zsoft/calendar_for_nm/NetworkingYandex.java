package com.example.zsoft.calendar_for_nm;

import android.os.AsyncTask;

import com.example.zsoft.calendar_for_nm.json.responseModel;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.Call;

public interface NetworkingYandex  {

    @Headers({
            "Content-Type: application/json",
            "Authorization: OAuth AQAAAAAh8KpiAASqQodGr4H4sU2qrhODN1B84NI",
            "x-yandex-maya-uid: 569420386",
            "x-yandex-maya-cid: MAYA-16891790-1578570738675",
            "x-yandex-maya-ckey: OzUM3DgHei8N64Mr7PoB0cM0hFlwOFl6JgI71uG8ue07ZNYzOJ1farW69DQXCsaq11HBRGEFvWSGr7gy5YHH6w==",
            "x-yandex-maya-locale: ru",
            "x-yandex-maya-timezone: Europe/Moscow"
    })
    @POST("/api/models?_models=create-event")
    Call<responseModel>getData(@Body String querry);

}

