package com.example.zsoft.calendar_for_nm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by adolf on 14.04.2018.
 */

public class Adapter_recycle extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Object> data;
    String date;
    EditText eName,eNum,eSum;
    NumberPicker h1,h2,m1,m2;
    int sh1,sm1,sh2,sm2;
    private final static int TYPE_FULL=1,TYPE_EMPTY=2;

    Context context;


    public Adapter_recycle(Context context){
        this.context=context;
    }

    public void   setAdapter_recycle(List<Object> data,String date){
        this.data=data;
        this.date=date;
   }

    public  void refresh(){
        List<String> dataDB=new ExexDB().l_clients_of_day(context,date);
        List<Object>ulist=new Recycle_windows().set_test(dataDB,context);
        this.data=ulist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position)instanceof Constructor_data){
            return TYPE_FULL;
        }else if(data.get(position)instanceof Constructor_data.Constructor_free_data){
            return TYPE_EMPTY;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_FULL:
                view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card,parent,false);
                return new FullHolder(view);

            case  TYPE_EMPTY:
                view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_empty,parent,false);
                return new EmptyHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType=holder.getItemViewType();
        switch (viewType) {
            case TYPE_FULL:
                Constructor_data ful_data = (Constructor_data)
                        data.get(position);
                ((FullHolder)holder).show_data(ful_data);
                break;

            case TYPE_EMPTY:
                Constructor_data.Constructor_free_data empty_data = (Constructor_data.Constructor_free_data)
                        data.get(position);
                ((EmptyHolder)holder).show_data(empty_data);

                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FullHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        EditText editText;
        CheckBox checkBox;
        TextView h,m,h2,m2;

        public FullHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card);
            textView=(TextView) itemView.findViewById(R.id.textView12);
            editText=(EditText)itemView.findViewById(R.id.editText2);
            checkBox=(CheckBox) itemView.findViewById(R.id.checkBox2);
            h=(TextView) itemView.findViewById(R.id.h);
            m=(TextView) itemView.findViewById(R.id.m);
            h2=(TextView) itemView.findViewById(R.id.h2);
            m2=(TextView) itemView.findViewById(R.id.m2);

        }
        public void show_data(Constructor_data ful_data){
            textView.setText(ful_data.name);
            editText.setText(String.valueOf(ful_data.sum));
            checkBox.setChecked(ful_data.flag);

            h.setText(ful_data.h1);
            m.setText(ful_data.m1);
            h2.setText(ful_data.h2);
            m2.setText(ful_data.m2);

        }

    }

    public class EmptyHolder extends RecyclerView.ViewHolder {
        CardView card_empt;
        TextView h,m,h2,m2;

        public EmptyHolder(View itemView) {

            super(itemView);
            card_empt=(CardView)itemView.findViewById(R.id.card_emp);
            h=(TextView) itemView.findViewById(R.id.emty_h);
            m=(TextView) itemView.findViewById(R.id.empty_m);
            h2=(TextView) itemView.findViewById(R.id.empty_h2);
            m2=(TextView) itemView.findViewById(R.id.empty_m2);
        }


        public void show_data(final Constructor_data.Constructor_free_data free){
            h.setText(free.h1);
            m.setText(free.m1);
            h2.setText(free.h2);
            m2.setText(free.m2);

            card_empt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                   // Intent intent0=new Intent(view.getContext(),WriteOrder.class);
                   // view.getContext().startActivity(intent0);

                    return false;
                }
            });

            card_empt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  Alert(context,date,
                            free.h1+":"+free.m1,
                            free.h2+":"+free.m2);
                }
            });

        }
    }


//Для алерта запрет на запись в базу пустоты. при нажатии на ок
// то что записано в эдиты равно тому что было раньше,то не пишу

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
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
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
                        refresh();
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

    // если 23ч аходит в интервал свободных
    // то при пыставлении 23 59 в  дату начала позволяет  дату окончания сделать меньше
    // смог с 7:00 записать на 7.00 и слежд запись косячная
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