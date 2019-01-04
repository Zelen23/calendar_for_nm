package com.example.zsoft.calendar_for_nm.json;

import android.content.ContentValues;
import android.util.Log;

import com.example.zsoft.calendar_for_nm.HelperData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adolf on 27.12.2018.
 */

public class Services {
     /*Если включена настройка синхронизации
     * при создании записи,удалении, измении пишу данные в json
     * отправляю данные по расписанию
     * после отправки удаляю локально созданный json
     *
     **/

     /*логика обмена один json
     * три массива
     * добавленные
     * удаленные
     * измененные*/


     public SyncFileJson writeOrdToJson(ContentValues valuse){
         /*time1=09:00:00 date=2019-0-28 time2=10:00:00 name=rrr pay=666 date1=чт, 27 дек. 2018 21:10:57 sf_num=7777*/

          SyncFileJson syncFileJson=new SyncFileJson();
          syncFileJson.timeshtamp=444;
          syncFileJson.version="3";

          List<SyncFileJson.Clients_write> ord=new ArrayList<>();
          ord.add(new SyncFileJson.Clients_write(
                  valuse.getAsString("time1").toString(),
                  valuse.getAsString("time2").toString(),
                  valuse.getAsString("date").toString(),
                  valuse.getAsString("name").toString(),
                  valuse.getAsString("sf_num").toString(),
                  Integer.parseInt(valuse.getAsString("pay")),
                  valuse.getAsString("date1").toString()));

          syncFileJson.clients_write=ord;

          Log.i("SyncFileJson", "tab "+valuse.getAsString("time1"));
          return syncFileJson;

     }

     public void savejson(SyncFileJson syncFileJson){

          GsonBuilder gsonBuilder=new GsonBuilder();
          Gson gson=gsonBuilder.create();

          File file=new File("/sdcard/sdcard/222/info.json");
          if(file.exists()){
          List<SyncFileJson.Clients_write> ord= gson.fromJson(new HelperData()
                       .readToStream("/sdcard/sdcard/222/info.json").toString(),SyncFileJson.class).clients_write;
               ord.add(syncFileJson.clients_write.get(0));

               syncFileJson.clients_write=ord;

               new HelperData().
                       saveFile("/sdcard/sdcard/222/",gson.toJson(syncFileJson));

          }else{
               new HelperData().
                       saveFile("/sdcard/sdcard/222/",gson.toJson(syncFileJson));
          }

     }

}
