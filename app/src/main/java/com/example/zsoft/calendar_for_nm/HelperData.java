package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.String.format;

/**
 * Created by AZelinskiy on 18.06.2018.
 */

public class HelperData {

    private  static String TAG="zsoft.Helper";
    public  String TimeShtampTranslater(String s){
      //  пт, 1 мар. 2019 11:51:02
      //  сб, 9 февр. 2019 22:11:11
        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"
                ,new Locale("ru"));
        String ss = " ";
        try {
            Date day=sdf.parse(s);
            ss=sdf.format(day);
            Log.i(TAG,ss+"loc "+Locale.getDefault());

        } catch (ParseException e) {
                /* в гетлайне Thu May 10 10:05:00 EAT 2018-  в таком формате приходит
                * //Thu May 10 10:05:00 EAT 2018 loc en_US
                 //Thu May 10 11:05:00 GMT+04:00 2018 loc en_US
                * */
                /*4.4 -z хавает EAT */
            SimpleDateFormat sdf3=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"
                    ,new Locale("en"));
            Date day;
            try {
                day = sdf3.parse(s);
                ss=sdf.format(day);
                Log.i(TAG,ss+" loc "+Locale.getDefault());
            } catch (ParseException e1) {

                SimpleDateFormat sdf2=new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy"
                        ,Locale.US );
                // sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                // String sss=s.replace("EAT","");
                String [] splt=s.split(" ");

                try {
                    if(splt.length>5){
                        day = sdf2.parse(splt[0]+" "+splt[1]+" "+splt[2]+" "+splt[3]+" "+splt[5]);
                        ss=sdf.format(day);
                    }else{
                        ss=s;
                    }

                } catch (ParseException e2) {
                    e2.printStackTrace();
                    ss="^"+s;
                }
               // e1.printStackTrace();
            }
        }

        Log .i("HelperData_TimeShtamp",s+"  "+ss);
        return ss;
    }
    public String ConvertDateFromDBX(String data){
        //2019-2-31
        /*взять эту кривую дату  к месяцу прибавить 1*/
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String[]datePM=data.split("-");
        Integer mms=Integer.parseInt(datePM[1])+1;
        String dataP=datePM[0]+"-"+mms+"-"+datePM[2];
        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
        String ss;
        try {
            Date dat=sdf.parse(dataP);
            ss=sdf2.format(dat);

        } catch (ParseException e) {
            ss=dataP;
            e.printStackTrace();
        }
        return ss;
    }
    public boolean comparateDate(String data, Date nowDate) {
        //приходит дата сравниваю ее с текушей, если больше текущей
        // возвпащаю true
        boolean status = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(data);
            date.setMonth(date.getMonth() + 1);
             //nowDate = new Date();
            if (date.compareTo(nowDate) > 0) {
                status = true;
            } else {
                status = false;
            }
            Log.i(TAG,date+"  "+nowDate+" status "+status);

        } catch (ParseException e) {
            Log.i(TAG,e.getMessage());
            e.printStackTrace();
        }


        return status;

    }
    public  String dbTimestampToLocaleDate(String unixTime){

        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"
                ,Locale.getDefault());

        try {
            String day=sdf.format(Long.parseLong(unixTime));
            return day;
        }catch (Exception ex){

        }


        return null;
    }
    public String dateFix(String date){
        String[] d=date.split("-");
        return d[0]+"-"+format("%02d",Integer.parseInt(d[1])+1)+"-"+ format("%02d",Integer.parseInt(d[2]));
    }
    public String fromSyncDateToDateDB(String date){
        //"2020-01-09T18:10:00

        String[] d=date.split("T");
        String[] dateOrd=d[0].split("-");
        String timeStart=d[1];

        String fakeDateTime=dateOrd[0]+"-"+(Integer.parseInt(dateOrd[1])-1)+"-"+ (Integer.parseInt(dateOrd[2])
                +"T"+timeStart);
        Log.i(TAG,fakeDateTime);
        return fakeDateTime;
    }
    //возвращаю количество дней между датами
    public int Intrval_to_seekBar(String t1,String t2){
        int i1 = 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date startDate=sdf.parse(t1);
            Date startDate2=sdf.parse(t2);

            DateTime dt1 = new DateTime(startDate);
            DateTime dt2 = new DateTime(startDate2);

            Days daysDelta=Days.daysBetween(dt1,dt2);
            i1=daysDelta.getDays();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        return i1;

        /*
        Дата первой записи
        дата последней записи
        */
    }
    //к начальной дате прибавляю дни, получаю новую дату
    public String fromIntToDateString(String startDate,int i){

        String s;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("d MMM yyyy",
                Locale.getDefault());
        try {
            Date date=sdf.parse(startDate);
            DateTime dt1 = new DateTime(date);
            DateTime dateTime=dt1.plusDays(i);
            Date date1=new Date(dateTime.getMillis());

            s=sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            s="-----";
        }




        return s;
    }
    // Data now
    public  String nowDate(){

        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"
                ,Locale.getDefault());
        //Locale locale=new Locale("ru");
        //Locale.setDefault(locale);

        String day=sdf.format(now);

        return day;
    }
    // temp convert 'ср, 26 дек. 2018 19:55:14' to date
    public Long tempDate (String sdateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                Locale.getDefault());
        Date day=new Date();
        try {
            day = sdf.parse(sdateTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day.getTime();
    }

    public String ClearNumberFormat(String phonenumber){
        return  phonenumber.toString().replaceAll("\\D+","");
    }
    public void HideKeyboeard(View v){
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    //читать из json. получать версию и дату
    /*{
    'date_Last_write':'Mon Dec 05 00:17:16 EET 2016',
     'version_base':1
    }*/
    //прочитать json
    public ArrayList readjson(String jsonData){
        ArrayList info=new ArrayList<String>();
        // переделать на автопарсинг

        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            String date_Last_write=jsonObject.getString("date_Last_write");
            int version=jsonObject.getInt("version_base");
            info.add(date_Last_write);
            info.add(String.valueOf(version));

            return info;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    //получить файл по пути
    public StringBuffer readToStream(String way){
        StringBuffer fileContent = new StringBuffer("");

        try {
            FileInputStream fis=new FileInputStream(way);
            byte buff []=new byte[1024];

            int read;
            try {
                while ((read=fis.read(buff))!=-1){

                    fileContent.append(new String(buff, 0, read));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileContent;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  null;

        }

    }
    public void return_copy(File fromFile,File toPath){
        /*копируем в папку sdcard
        * удаляем st.db
        * переимеовываеи то что скопировали в st.db*/

        InputStream in=null;
        OutputStream out=null;
       // del_buckup("/sdcard/sdcard/","st.db");
        try {
            File mdr=new File(toPath.getParent());
            if(!mdr.exists()){
                mdr.mkdirs();
            }

            in= new FileInputStream(fromFile.getAbsolutePath());
            out= new FileOutputStream(toPath.getAbsolutePath());
            byte buff []=new byte[1024];
            int read;

            try {
                while ((read=in.read(buff))!=-1){
                    out.write(buff,0,read);
                }
                in.close();
                in=null;

                out.flush();
                out.close();
                out=null;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void saveFile(String pathName,String filename, String dataToSave){
        InputStream in=null;
        OutputStream out=null;

        try {
            File mdr=new File(pathName);
            if(!mdr.exists()){
                mdr.mkdirs();
            }

            in= new ByteArrayInputStream(dataToSave.getBytes());
            out= new FileOutputStream(pathName+filename);
            byte buff []=new byte[1024];
            int read;

            try {
                while ((read=in.read(buff))!=-1){
                    out.write(buff,0,read);
                }
                in.close();
                in=null;

                out.flush();
                out.close();
                out=null;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    @SuppressLint("DefaultLocale")
    private String FormatToHHmm(int h, int m){
        return format("%02d",h)+":"+ format("%02d",m)+":00";
    }
    //2018-1-21
    public String ConvertDateFromDB(String data){
        //2019-2-31
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
        String ss;
        try {
            Date dat=sdf.parse(data);
            dat.setMonth(dat.getMonth()+1);
            ss=sdf2.format(dat);

        } catch (ParseException e) {
            ss=data;
            e.printStackTrace();
        }
        return ss;
    }
    public  String cutTimeShtamp(String timeShtamp){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                Locale.getDefault());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");

        try {
            Date day = sdf.parse(timeShtamp);
            String dateOfShtamp = format.format(day);
            String [] am=dateOfShtamp.split("-");
            int mns=Integer.parseInt(am[1])-1;
            String fuckedDate=am[0]+"-"+mns+"-"+am[2];

            return fuckedDate;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean comparateDateX(String data, Date nowDate) {
        //приходит дата сравниваю ее с текушей, если больше текущей
        // возвпащаю true
        boolean status = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(data);
            date.setMonth(date.getMonth());
            //nowDate = new Date();
            if (date.compareTo(nowDate) > 0) {
                status = true;
            } else {
                status = false;
            }
            Log.i(TAG,date+"  "+nowDate+" status "+status);

        } catch (ParseException e) {
            Log.i(TAG,e.getMessage());
            e.printStackTrace();
        }


        return status;

    }
    public void forBiwrite(String  timeShtamp){
        //вт, 5 июня 2018 11:52:28
        //пт, 1 мар. 2019 11:51:02
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                Locale.getDefault());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");


        Date day = null;
        try {
            day = sdf.parse(timeShtamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateOfShtamp = format.format(day);
        String [] am=dateOfShtamp.split("-");
        int mns=Integer.parseInt(am[1])-1;
        String fuckedDate=am[0]+"-"+mns+"-"+am[2];

    }
}



