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
import java.util.HashMap;
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
          syncFileJson.timeshtamp=new HelperData().tempDate(new HelperData().nowDate());
          syncFileJson.version="4";

          List<SyncFileJson.Clients_write> ord=new ArrayList<>();
          ord.add(new SyncFileJson.Clients_write(
                  valuse.getAsString("time1"),
                  valuse.getAsString("time2"),
                  valuse.getAsString("date"),
                  valuse.getAsString("name"),
                  valuse.getAsString("sf_num"),
                  valuse.getAsString("pay"),
                  valuse.getAsString("date1")));

          syncFileJson.clients_write=ord;

          Log.i("SyncFileJson", "tab "+valuse.getAsString("time1"));
          return syncFileJson;

     }

     public void savejson(SyncFileJson syncFileJson){

          GsonBuilder gsonBuilder=new GsonBuilder();
          Gson gson=gsonBuilder.create();
          String way="/sdcard/sdcard/temp/";
          String name="info.json";

          File file=new File(way+name);

          if(file.exists()){
          List<SyncFileJson.Clients_write> ord= gson.fromJson(new HelperData()
                       .readToStream(way+name).toString(),SyncFileJson.class).clients_write;
               ord.add(syncFileJson.clients_write.get(0));

               syncFileJson.clients_write=ord;

               new HelperData().
                       saveFile(way,name,gson.toJson(syncFileJson));

          }else{
               new HelperData().
                       saveFile(way,name,gson.toJson(syncFileJson));
          }

     }

     public void js(ContentValues valuse){


                  String name=valuse.getAsString("name");
                  String description=valuse.getAsString("sf_num");

                  String start= valuse.getAsString("date")+"T"+valuse.getAsString("time1");
                  String end= valuse.getAsString("date")+"T"+valuse.getAsString("time2");




          HashMap<String,CreateEventJsom.params> params=new HashMap<>();
          CreateEventJsom.params valparams= new CreateEventJsom.params
                  (        name
                          ,description
                          ,false
                          ,false
                          ,true
                          ,false
                          ,"busy"
                          ,9443673
                          ,start
                          ,end
                          );

          params.put("params",valparams);

          HashMap<String, List<CreateEventJsom>> models=new HashMap<>();
          List<CreateEventJsom> modelsValue=new ArrayList<>();

          modelsValue.add(new CreateEventJsom("create-event",valparams));

          models.put("models",modelsValue);

          String jsonStr = new Gson().toJson(models);
          Log.i("jsonStr", jsonStr);
     }

     public SyncFileJson writeCreateEvent(ContentValues valuse){
          /*time1=09:00:00 date=2019-0-28 time2=10:00:00 name=rrr pay=666 date1=чт, 27 дек. 2018 21:10:57 sf_num=7777*/

          SyncFileJson syncFileJson=new SyncFileJson();

          return syncFileJson;

     }
}
