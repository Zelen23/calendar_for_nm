package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adolf on 29.04.2018.
 */

public class AlertDiagWriteOrder  {
    EditText eName,eNum,eSum;
    NumberPicker h1,h2,m1,m2;
    int sh1,sm1,sh2,sm2;

   public void Alert(final Context context, final String date, String time1, String time2){
       android.app.AlertDialog alert;

       LayoutInflater li= LayoutInflater.from(context);
       View vw=li.inflate(R.layout.frame_write,null);
       eName=(EditText)vw.findViewById(R.id.eName);
       eNum=(EditText)vw.findViewById(R.id.eNnm);
       eSum=(EditText)vw.findViewById(R.id.eSum);
       h1=(NumberPicker)vw. findViewById(R.id.pH1);
       h2=(NumberPicker)vw. findViewById(R.id.pH2);
       m1=(NumberPicker)vw. findViewById(R.id.pm1);
       m2=(NumberPicker)vw. findViewById(R.id.pm2);

       /*дата и 2 времени*/
    android.app.AlertDialog.Builder builder= new android.app.AlertDialog.Builder(context);
       builder.setView(vw)
               .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       new ExexDB().write_orders(context,date,
                               formTime(h1.getValue(),m1.getValue()),
                               formTime(h2.getValue(),+m2.getValue()),

                               eSum.getText().toString(),
                               eName.getText().toString(),
                               eNum.getText().toString(),"clients","");
                      new Adapter_recycle(context).refresh();
                       dialogInterface.dismiss();

                   }
               });

       try {
           set_time_to_spiner(time1,time2);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       eName.setText(date);
       alert = builder.create();
       alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
       alert.show();
   }

// если 23ч аходит в интервал свободных то при пыставлении 23 59 в  дату начала позволяет  дату окончания сделать меньше
    void set_time_to_spiner(String time,String time2)throws ParseException {



        // Log.i("time_to_spinner",String.valueOf());
        final SimpleDateFormat e_time=new SimpleDateFormat("HH:mm");
        final SimpleDateFormat s_time=new SimpleDateFormat("HH:mm");
        //   String strTime = e_time.format(new Date());
        Date dt1= e_time.parse(time);
        final Date dt2= e_time.parse(time2);
        final int th1=dt1.getHours();
        final int th2=dt2.getHours();
        final int tm1=dt1.getMinutes();
        final int tm2=dt2.getMinutes();
        Log.i("time_to_spinner",String.valueOf(th1)+"   "+String.valueOf(th2));
        //  long minutes = dt2.getTime() - dt1.getTime();
        //   int deltaminutes = (int) (minutes / (60 * 1000));

        //   final String s1,s2;
        // установить минимальное значение равное часу t1 t2
// api23 выебывается
        h1.setMinValue(th1);
        h1.setMaxValue(th2);

        m1.setMinValue(tm1);
        m1.setMaxValue(59);

        sm1=m1.getValue();
        sh1=h1.getValue();

        h2.setMinValue(th1);
        h2.setMaxValue(th2);
        h2.setValue(th1+1);

        //если значение часа окончания = максимальному то от нуля до минуты окончания
        if(h2.getValue()<th2){
            m2.setMinValue(0);
            m2.setMaxValue(59);
        }else{
            m2.setMinValue(0);
            m2.setMaxValue(tm2);
        }


        sm2=m2.getValue();
        sh2=h2.getValue();


        //   stime1=e_time.parse(String.valueOf(h1.getValue()+":"+m1.getValue()));
        //  stime2=e_time.parse(String.valueOf(h2.getValue()+":"+m2.getValue()));

        h1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
// если час окончания меньше или равен выбранному часу начала
                h2.setMinValue(h1.getValue());
                h2.setMaxValue(th2);
                h2.setValue(h1.getValue()+1);


                if(h1.getValue()==th1) {
                    m1.setMinValue(tm1);
                    m1.setMaxValue(59);

                }


                if(h1.getValue()==th2){
                    m2.setMinValue(0);
                    m2.setMaxValue(tm2);

                    m1.setMinValue(0);
                    m1.setMaxValue(tm2);

                }else{
                    m1.setMinValue(0);
                    m1.setMaxValue(59);
                }
                if(h1.getValue()==th2-1) {
                    m1.setMinValue(0);
                    m1.setMaxValue(59);

                    m2.setMinValue(0);
                    m2.setMaxValue(tm2);
                }

                sh1=h1.getValue();
                sh2=h2.getValue();

            }


        });

        m1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //не проверенно
                if(h1.getValue()==h2.getValue()&&h1.getValue()!=th2){
                    m2.setMinValue(m1.getValue());
                    m2.setMaxValue(59);
                }
                sm1=m1.getValue();
            }
        });

        h2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
// если час последний то от нуля до последней минуты если нет от от0 до 50
                if(h2.getValue()==th2){
                    m2.setMinValue(0);
                    m2.setMaxValue(tm2);
                }else{
                    m2.setMinValue(0);
                    m2.setMaxValue(59);
                }

                if(h1.getValue()==h2.getValue()&&h1.getValue()!=th2){
                    m2.setMinValue(m1.getValue());
                    m2.setMaxValue(59);
                }
                sh2=h2.getValue();
            }

        });

        m2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                sm2=m2.getValue();
            }

        });




    }

    public String formTime(int h,int m){
       String t=String.format("%02d",h)+":"+String.format("%02d",m)+":00";
       return t;
    }
}
