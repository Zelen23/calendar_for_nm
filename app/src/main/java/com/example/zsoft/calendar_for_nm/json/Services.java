package com.example.zsoft.calendar_for_nm.json;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.zsoft.calendar_for_nm.ExecDB;
import com.example.zsoft.calendar_for_nm.HelperData;
import com.example.zsoft.calendar_for_nm.PostYandexCalendar;
import com.example.zsoft.calendar_for_nm.db;
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


     public CreateEventJson.obj js(ContentValues value){


                  String name=value.getAsString("name");
                  String description=value.getAsString("sf_num");
                  String dateFix=new HelperData().dateFix(value.getAsString("date"));
                  String start= dateFix+"T"+value.getAsString("time1");
                  String end= dateFix+"T"+value.getAsString("time2");

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

          Log.i("js", name+" " +description+ " " +start+" "+end);
          return new CreateEventJson.obj("create-event",valparams);
     }
     public  void saveTempCreateEvent(ContentValues value) {

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

               // проверить ессть ли эта
               if(!upditem.contains(value)){
                    upditem.add(js(value));
                    createEventJson.models=upditem;
                    new HelperData().
                            saveFile(way, name, gson.toJson(createEventJson));
               }

          } else {
               List<CreateEventJson.obj> item=new ArrayList<>();
               item.add(js(value));


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
          if(file.exists()){
               List<CreateEventJson.obj> item = gson.fromJson(new HelperData()
                       .readToStream(way+name).toString(),CreateEventJson.class).models;

               for(int i=0;i<item.size();i++){
                    String names= item.get(i).params.name;
                    String sf_num= item.get(i).params.description;
                    String date[]= new HelperData().fromSyncDateToDateDB(item.get(i).params.start).split("T");
                    String exploit=date[0]+"'"+" and time1="+"'"+date[1]+"' and sf_num="+"'"+sf_num;
                    //l_clients_of_day
                    //не смог найти индекс вернул пустой массив от которого пытаюсь взять нулевой элемент
                    List<String> listID = new ExecDB().l_clients_of_day(context, exploit);
                    if (!listID.isEmpty()){
                         Integer id= Integer.valueOf(listID.get(0));
                         Integer uid=  resp.getModels().get(i).data.getShowEventId();
                         new ExecDB().writeUID(context,id,uid);
                    }

                    // ExecDB execDB= new ExecDB();
                    // execDB.l_clients_of_day(exploit);


               }
          }



     }

     public DeleteEventJson.obj json(int uid){

          HashMap<String,DeleteEventJson.params> params=new HashMap<>();
          DeleteEventJson.params valparams= new DeleteEventJson.params
                  (uid,0,false);
          return new DeleteEventJson.obj("delete-event",valparams);
     }
     public  void saveTempDeleteEvent(String id) {

          /* проверяю есть ли json файл
           * ксли есть то дописываю аолученный обьект в конец файла
           * если нет то создаю файл*/
          DeleteEventJson deleteEventJson=new DeleteEventJson();
          PostYandexCalendar postYandexCalendar =new PostYandexCalendar(context);
          ExecDB execDB=new ExecDB();

          // по id получаю uid
          Integer uid= execDB.getCalendarUid(context,id);
          if(uid!=null){
               GsonBuilder gsonBuilder = new GsonBuilder();
               Gson gson = gsonBuilder.create();
               String way = "/sdcard/sdcard/temp/";
               String name = "syncFileDelete.json";

               File file = new File(way + name);


               if (file.exists()) {
                    Log.i("jsonStr", "existFile");


                    List<DeleteEventJson.obj> upditemD = gson.fromJson(new HelperData()
                            .readToStream(way+name).toString(),DeleteEventJson.class).models;

                    upditemD.add(json(uid));
                    deleteEventJson.models=upditemD;
                    new HelperData().
                            saveFile(way, name, gson.toJson(deleteEventJson));


               } else {
                    List<DeleteEventJson.obj> item=new ArrayList<>();
                    item.add(json(uid));

                    deleteEventJson.models=item;
                    Log.i("jsonStrDelete",deleteEventJson.models.get(0).toString());
                    new HelperData().
                            saveFile(way, name, gson.toJson(deleteEventJson));
               }

          }else {
               Log.i("jsonStrDelete","_id "+id+" nonSync, if filr send Exist checl in");

               String way = "/sdcard/sdcard/temp/";
               String name = "syncFile.json";
               File fileSend = new File(way + name);
               if(fileSend.exists()){

                    Log.i("jsonStrDelete","file "+name +" exist");
                    ArrayList<String> list = execDB.getLine_(context, "clients", id);
                    // если uid-a нет проверить в  файле на отпавку и удалитьв нем
                    ContentValues values=new ContentValues();
                    db dbs=new db(context);

                    values.put(dbs.DATE1_COLUMN, list.get(5));
                    values.put(dbs.CONTACT_COLUMN, list.get(1));
                    values.put(dbs.NAME_COLUMN, list.get(0));
                    values.put(dbs.PAY_COLUMN, list.get(6));
                    values.put(dbs.TIME1_COLUMN, list.get(2));
                    values.put(dbs.TIME2_COLUMN, list.get(3));
                    values.put(dbs.DATE_COLUMN, list.get(4));

                    CreateEventJson.obj searchObj = js(values);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    List<CreateEventJson.obj> listObj = gson.fromJson(new HelperData()
                            .readToStream(way+name).toString(),CreateEventJson.class).models;

                    for (int i=0; i<listObj.size();i++){

                         CreateEventJson.obj elt=listObj.get(i);

                      if(
                              elt.params.name.equals(searchObj.params.name)&&
                              elt.params.description.equals(searchObj.params.description)&&
                              elt.params.start.equals(searchObj.params.start)

                      ){
                           listObj.remove(i);
                           CreateEventJson createEventJson=new CreateEventJson();
                           createEventJson.models= listObj;
                           Log.i("jsonStrDelete","id for delete "+i);
                           new HelperData().
                                   saveFile(way, name, gson.toJson(createEventJson));
                      }

                         }



               }

          }


         postYandexCalendar.deleteEvent(deleteEventJson);

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

     /*создать запись: запись в бд-создать json->отправить(успешно-удалить)
     создать запись: запись в бд-создать json->отправить(не успешно-оставить)

     удалить: 1 запись  была отсинхронена
     найти uid создать json  на удаление-есть сеть  удалить  и удалить файл
                                          нет сети  создать оставить json на удаление

               2 запись не была синхронизирована uid is null- смотрим в файле(есть) удаляем из файла
               отправляем файл на создание(есть сеть)
        */
}
