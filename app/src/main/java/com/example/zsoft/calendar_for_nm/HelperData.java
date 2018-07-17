package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.String.format;

/**
 * Created by AZelinskiy on 18.06.2018.
 */

public class HelperData {

    public void HideKeyboeard(View v){
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public  String TimeShtampTranslater(String s){

        SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"
                ,new Locale("ru"));
        String ss = null;
        try {
            Date day=sdf.parse(s);
            ss=sdf.format(day);
            Log.i("1eeeSwipeAd_s",ss+"loc "+Locale.getDefault());

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
                Log.i("3eeeSwipeAd_s",ss+" loc "+Locale.getDefault());
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

    @SuppressLint("DefaultLocale")
    private String FormatToHHmm(int h, int m){
        return format("%02d",h)+":"+ format("%02d",m)+":00";
    }

    //2018-1-21
    public String ConvertDateFromDB(String data){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MMM-yyyy",new Locale("ru"));
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
            new Locale("ru"));

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

    public boolean comparateDate(String data) {
        //приходит дата сравниваю ее с текушей, если больше текущей
        // возвпащаю true
        boolean status = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(data);
            date.setMonth(date.getMonth() + 1);
            Date nowDate = new Date();
            if (date.compareTo(nowDate) > 0) {
                status = true;
            } else {
                status = false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return status;

    }
    public String ClearNumberFormat(String phonenumber){
        return  phonenumber.toString().replaceAll("\\D+","");
    }
    


    }
