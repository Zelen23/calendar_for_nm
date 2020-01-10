package com.example.zsoft.calendar_for_nm.json;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.zsoft.calendar_for_nm.ExecDB;
import com.example.zsoft.calendar_for_nm.HelperData;
import com.example.zsoft.calendar_for_nm.PostYandexCalendar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
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

     public Services(Context context) {
          this.context = context;
     }

     Context context;


     public CreateEventJson.obj js(ContentValues valuse){


                  String name=valuse.getAsString("name");
                  String description=valuse.getAsString("sf_num");
                  String dateFix=new HelperData().dateFix(valuse.getAsString("date"));
                  String start= dateFix+"T"+valuse.getAsString("time1");
                  String end= dateFix+"T"+valuse.getAsString("time2");

          HashMap<String,CreateEventJson.params> params=new HashMap<>();
          CreateEventJson.params valparams= new CreateEventJson.params
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


          return new CreateEventJson.obj("create-event",valparams);
     }
     public  void saveTemp(ContentValues valuse) {

          /* проверяю есть ли json файл
           * ксли есть то дописываю аолученный обьект в конец файла
           * если нет то создаю файл*/

          GsonBuilder gsonBuilder = new GsonBuilder();
          Gson gson = gsonBuilder.create();
          String way = "/sdcard/sdcard/temp/";
          String name = "syncFile.json";

          File file = new File(way + name);

          CreateEventJson createEventJson=new CreateEventJson();
          PostYandexCalendar postYandexCalendar =new PostYandexCalendar(context);

          if (file.exists()) {
               Log.i("jsonStr", "existFile");


               List<CreateEventJson.obj> upditem = gson.fromJson(new HelperData()
                       .readToStream(way+name).toString(),CreateEventJson.class).models;

               upditem.add(js(valuse));
               createEventJson.models=upditem;
               new HelperData().
                       saveFile(way, name, gson.toJson(createEventJson));


          } else {
               List<CreateEventJson.obj> item=new ArrayList<>();
               item.add(js(valuse));


               createEventJson.models=item;
               Log.i("jsonStr",createEventJson.models.get(0).toString());
               new HelperData().
                       saveFile(way, name, gson.toJson(createEventJson));
          }
          String querry=new HelperData()
                  .readToStream(way+name).toString();
          Log.i("querry",querry);
          postYandexCalendar.sendEvent(createEventJson);

     }
     public  void setUidtoOrder( responseModel resp){
          String way = "/sdcard/sdcard/temp/";
          String name = "syncFile.json";
          GsonBuilder gsonBuilder = new GsonBuilder();
          Gson gson = gsonBuilder.create();
          File file = new File(way + name);

          List<CreateEventJson.obj> item = gson.fromJson(new HelperData()
                  .readToStream(way+name).toString(),CreateEventJson.class).models;

          for(int i=0;i<item.size();i++){
              String names= item.get(i).params.name;
              String sf_num= item.get(i).params.description;
              String date[]= new HelperData().fromSyncDateToDateDB(item.get(i).params.start).split("T");
              String exploit=date[0]+"'"+" and time1="+"'"+date[1]+"' and sf_num="+"'"+sf_num;
              //l_clients_of_day
             Integer id= Integer.valueOf(new ExecDB().l_clients_of_day(context, exploit).get(0));
             Integer uid=  resp.getModels().get(i).data.getShowEventId();
             new ExecDB().writeUID(context,id,uid);
              // ExecDB execDB= new ExecDB();
              // execDB.l_clients_of_day(exploit);


          }

     }

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
     public SyncFileJson writeCreateEvent(ContentValues valuse){
          /*time1=09:00:00 date=2019-0-28 time2=10:00:00 name=rrr pay=666 date1=чт, 27 дек. 2018 21:10:57 sf_num=7777*/

          SyncFileJson syncFileJson=new SyncFileJson();

          return syncFileJson;

     }
}
