package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;

/**
 * Created by adolf on 14.04.2018.
 */

public class Swipe_Adapter_recycle extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> data;
    private String date;


    private EditText eName,eNum,eSum;
    private NumberPicker h1,h2,m1,m2;

    private final static int TYPE_FULL=1,TYPE_EMPTY=2;

    private Context context;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();


     Swipe_Adapter_recycle(Context context){
        this.context=context;
    }

     void   setAdapter_recycle(List<Object> data,String date){
        this.data=data;
        this.date=date;
   }

     void refresh(){
        List<String> dataDB=new ExecDB().l_clients_of_day(context,date);
          this.data= new RecycleWinActivity().set_test(dataDB,context);
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_FULL:
                view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.swipe_card,parent,false);
                return new FullHolder(view);

            case  TYPE_EMPTY:
                view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.swipe_card_empty,parent,false);
                return new EmptyHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull  final RecyclerView.ViewHolder holder, int position) {
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
    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    public class FullHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        EditText editText;
        CheckBox checkBox;
        TextView h,m,h2,m2;
        ImageButton bInf,bCut,bCopy,bDelete;

        View firstFrame;
        View secondFrame;
        SwipeRevealLayout swipe;

        FullHolder(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            textView=itemView.findViewById(R.id.textView12);
            editText=itemView.findViewById(R.id.editText2);
            checkBox= itemView.findViewById(R.id.checkBox2);
            h=itemView.findViewById(R.id.h);
            m=itemView.findViewById(R.id.m);
            h2=itemView.findViewById(R.id.h2);
            m2=itemView.findViewById(R.id.m2);

            swipe=itemView.findViewById(R.id.swipe_layout);
            firstFrame=itemView.findViewById(R.id.firstFrame);

            secondFrame=itemView.findViewById(R.id.secondFrame);
            bInf=itemView.findViewById(R.id.bInfo);
            bCut=itemView.findViewById(R.id.bCut);
            bCopy=itemView.findViewById(R.id.bCopy);
            bDelete=itemView.findViewById(R.id.bDelete);

        }

        void show_data(final Constructor_data ful_data){

            binderHelper.bind(swipe,ful_data.id );
            binderHelper.setOpenOnlyOne(true);

            textView.setText(ful_data.name);
            editText.setText(String.valueOf(ful_data.sum));

            checkBox.setChecked(ful_data.flag);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBox.isChecked()) {
                        ExecDB vis = new ExecDB();
                        vis.flag_visitOrPay(context, "true",
                                ful_data.id, "clients","visit");
                        refresh();
                    } else {
                        ExecDB vis = new ExecDB();
                        vis.flag_visitOrPay(context, "false",
                                ful_data.id, "clients","visit");
                        refresh();
                    }
                }
            });

            h.setText(ful_data.h1);
            m.setText(ful_data.m1);
            h2.setText(ful_data.h2);
            m2.setText(ful_data.m2);


            swipe.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener(){
                @Override
                public void onOpened(SwipeRevealLayout view) {
                    super.onOpened(view);



                }

                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    super.onSlide(view, slideOffset);


                }
            });

            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder adb=new AlertDialog.Builder(context);
                    ArrayList data=new ExecDB().getLine_(context,"clients",ful_data.id);
                    adb.setTitle("Удалить запись "+data.get(0));
                    adb.setMessage("Номер:"+data.get(1)+
                    "\nC:"+data.get(2)+
                    "\nПо:"+data.get(3));

                    adb.setPositiveButton("Удалить",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ExecDB().deleterow(context,"clients",ful_data.id);
                            //data.remove(getAdapterPosition());
                            //notifyItemRemoved(getAdapterPosition());
                            new MainActivity().updGridCld();
                            refresh();
                            dialog.dismiss();
                        }
                    });

                    adb.setNegativeButton("Oтменить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                }
            });

            bCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
    //  проверка на наличие вырезанной записи
                    ExecDB exec= new ExecDB();
                    ArrayList<String> data=exec.getLine_(context,"clients",ful_data.id);
                     final ArrayList<String> temp=exec.getLine_(context,"temp"
                           ,"'' or _id>0");
                    if(temp.size()>0&&Boolean.parseBoolean(temp.get(7))==true){

                       // Toast.makeText(context,"true",Toast.LENGTH_SHORT).show();
                        /*
                        Если в буффере находится вырезанная запись
                        * то  сначала возвращаю старую запись
                        * чищу темп
                        * копирую новую
                        * */

                        //возвращаю из темп в клиенты
                        exec.write_orders(context,temp.get(4),temp.get(2),temp.get(3)
                                ,temp.get(6),temp.get(0),temp.get(1),"clients",temp.get(8));
                        // чищу темп
                        exec.deleterow(context,"temp","'' or _id>0");

                        //пишу в темп  новую запись
                        exec.write_orders(context,data.get(4),data.get(2),data.get(3)
                                ,data.get(6),data.get(0),data.get(1),"temp",ful_data.id);
                        refresh();
                    }else{
// если темп пустой
                        exec.deleterow(context, "temp", "'' or _id>0");
                        exec.write_orders(context, data.get(4), data.get(2), data.get(3), data.get(6)
                                , data.get(0), data.get(1), "temp", ful_data.id);

                    }

                }
            });

            bCut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExecDB exec= new ExecDB();
                    ArrayList<String> data=exec.getLine_(context,"clients",ful_data.id);
                    ArrayList<String> temp=exec.getLine_(context,"temp"
                          ,"'' or _id>0");
                    if(temp.size()>0&&Boolean.parseBoolean(temp.get(7))==true){
                       // Toast.makeText(context,"true",Toast.LENGTH_SHORT).show();
                        /*Если в базе уже есть вырезанная запись
                        * Возвращаю старую на место
                        * чищу темп
                        * Пишу новую в темп
                        *
                        * ставлю флаг в темпе
                        * удаляю в клиентах*/
                        alertTemp(data,temp,ful_data.id);
                        // удаляю в клиентах


                    }else{
                        //возвращаю из темп в клиенты
                        // чищу темп
                        exec.deleterow(context,"temp","'' or _id>0");

                        //пишу в темп  новую запись
                        exec.write_orders(context,data.get(4),data.get(2),data.get(3)
                                ,data.get(6),data.get(0),data.get(1),"temp",ful_data.id);
                        // удаляю в клиентах
                        exec.deleterow(context,"clients",ful_data.id);
                        // ставлю влаг
                        exec.flag_visitOrPay(context, "true",
                                ful_data.id, "temp","visit");
                        new MainActivity().updGridCld();
                        refresh();


// если темп пустой
                    }

                }
            });

            bInf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExecDB exec= new ExecDB();
                    ArrayList<String> data=exec.getLine_(context,"clients",ful_data.id);

                    ArrayList<String> user=exec.getLine_(context,"user",data.get(1));

                    AlertDialog.Builder adb=new AlertDialog.Builder(context);
                    adb.setTitle(user.get(1)+" "+user.get(2));

                    String  mess="Номер: "+user.get(3)+
                            "\nВсего записей: "+user.get(6)+
                            "\nЗаписана: "+ getTimeStamp(data.get(5));


                    if(exec.beWrite(context,data.get(1),data.get(5))!=null
                            && exec.beWrite(context,data.get(1),data.get(5)).size()>0){
                        mess=mess
                                +"\nБыла в момент записи: ДА"
                                +"\nC: "+exec.beWrite(context,data.get(1),data.get(5)).get(1)
                                +"\nПо: "+exec.beWrite(context,data.get(1),data.get(5)).get(2);
                    }else{
                        mess=mess
                                +"\nБыла в момент записи: НЕТ";
                    }



                    adb.setMessage(mess);

                    adb.setNeutralButton("OK",new  DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();


                }
            });
        }
//Дата на русском
        String getTimeStamp(String s){
            SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                    new Locale("ru"));

           //Locale locale=new Locale("ru");
           // Locale.setDefault(locale);

            String ss;
            try {
                Date day=sdf.parse(s);
                ss=sdf.format(day);

               // Date day=sdf.parse(timeShtamp);
               // int d=day.getDate();
               // ss=day.toString();

            } catch (ParseException e) {
                e.printStackTrace();
                ss=s;
            }

            return ss;
        }

        // если в базе есть вырезанная запись
        void alertTemp(final ArrayList<String> data, final ArrayList<String> temp, final String id){
            String flagcut=temp.get(7);
            Log.i("flagcut",flagcut);

                AlertDialog.Builder adb= new AlertDialog.Builder(context);
                adb.setTitle("В буфере уже есть вырезанная запись");
                adb.setMessage("Имя: "+temp.get(0) +
                        "\nМоб:"+temp.get(1)+
                        "\nC: "+temp.get(2)+" По: "+temp.get(3)+
                        "\nДата: "+temp.get(4)+
                        "\n"+
                        "\nЗакрыть диалог или вернуть запись на место " +
                        "и записать в буфер новую ");
                adb.setNegativeButton(" Закрыть", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                adb.setPositiveButton("Перезаписать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExecDB exec= new ExecDB();


                        //возвращаю из темп в клиенты
                        exec.write_orders(context,temp.get(4),temp.get(2),temp.get(3)
                                ,temp.get(6),temp.get(0),temp.get(1),"clients",temp.get(8));
                        // чищу темп
                        exec.deleterow(context,"temp","'' or _id>0");

                        //пишу в темп  новую запись
                        exec.write_orders(context,data.get(4),data.get(2),data.get(3)
                                ,data.get(6),data.get(0),data.get(1),"temp",id);
                        exec.deleterow(context,"clients",id);
                        // ставлю влаг
                        exec.flag_visitOrPay(context, "true",
                                id, "temp","visit");

                        refresh();
                        new MainActivity().updGridCld();
                        dialog.dismiss();
                    }
                });
                adb.show();
                //диалог затереть или нет
                //пишу в базу(дата/время1/время2/сумма/имя/Номер/таблица/ид)
                //


        }

    }

    public class EmptyHolder extends RecyclerView.ViewHolder {
        CardView card_empt;
        TextView h,m,h2,m2;

        ImageButton bPase,bWrite;
        SwipeRevealLayout swipeFree;

        View firstFrameFree;
        View secondFrameFree;

        EmptyHolder(View itemView) {

            super(itemView);
            card_empt=itemView.findViewById(R.id.card_emp);
            h=itemView.findViewById(R.id.emty_h);
            m= itemView.findViewById(R.id.empty_m);
            h2= itemView.findViewById(R.id.empty_h2);
            m2= itemView.findViewById(R.id.empty_m2);

            bPase=itemView.findViewById(R.id.bPaste);
            bWrite=itemView.findViewById(R.id.bWrite);
            firstFrameFree=itemView.findViewById(R.id.firstFrameFree);
            secondFrameFree=itemView.findViewById(R.id.secondFrameFree);
            swipeFree=itemView.findViewById(R.id.swipe_layoutFree);
        }

        void show_data(final Constructor_data.Constructor_free_data free){
            h.setText(free.h1);
            m.setText(free.m1);
            h2.setText(free.h2);
            m2.setText(free.m2);
            binderHelper.bind(swipeFree,free.h1);
            binderHelper.setOpenOnlyOne(true);

            bWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alert(context,date,
                            free.h1+":"+free.m1,
                            free.h2+":"+free.m2);

                }
            });

            final boolean flg=true;

            bPase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> data=new ExecDB().getLine_(context,"temp"," ' ' or _id>0");
                    //еслт Temp не пуст то
                    if(data.size()>0) {

                        Alert(context, date,
                                free.h1 + ":" + free.m1,
                                free.h2 + ":" + free.m2);
                        eName.setText(data.get(0));
                        eNum.setText(data.get(1));
                        eSum.setText(data.get(6));

                    }
                }
            });

            clicableBtn(true,bPase);
            swipeFree.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener(){

                @Override
                public void onClosed(SwipeRevealLayout view) {
                    super.onClosed(view);
                }

                @Override
                public void onOpened(SwipeRevealLayout view) {
                    super.onOpened(view);
                }

                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    super.onSlide(view, slideOffset);



                }
            });

            card_empt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });

            card_empt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
        void clicableBtn(Boolean flg,ImageButton btn){
            if(flg==false) {
                btn.setClickable(false);
                btn.setVisibility(View.INVISIBLE);

            }

        }
    }


//Для алерта запрет на запись в базу пустоты. при нажатии на ок
// то что записано в эдиты равно тому что было раньше,то не пишу

    public void Alert(final Context context, final String date, String time1, String time2){
           AlertDialog alert;
           LayoutInflater li = LayoutInflater.from(context);
           View vw = li.inflate(R.layout.frame_write, null);
           eName=vw.findViewById(R.id.eName);
           eNum = vw.findViewById(R.id.eNnm);
           eSum = vw.findViewById(R.id.eSum);
           h1 = vw.findViewById(R.id.pH1);
           h2 = vw.findViewById(R.id.pH2);
           m1 = vw.findViewById(R.id.pm1);
           m2 = vw.findViewById(R.id.pm2);

           final AlertDialog.Builder builder = new AlertDialog.Builder(context);

       /*дата и 2 времени*/

           builder.setView(vw)
                   .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                           if(h1.getValue()==h2.getValue()&&m1.getValue()==m2.getValue()){

                               Toast.makeText(context,"WTF&! time1==time2",Toast.LENGTH_SHORT)
                                       .show();
                           }else

                           new ExecDB().write_orders(context, date,
                                   formTime(h1.getValue(), m1.getValue()),
                                   formTime(h2.getValue(), +m2.getValue()),

                                   eSum.getText().toString(),
                                   eName.getText().toString(),
                                   eNum.getText().toString(), "clients", "");

                           new MainActivity().updGridCld();
                           new ExecDB().deleterow(context,"temp","'' or _id>0");

                           refresh();
                           dialogInterface.dismiss();
                       }
                   });

           try {
               set_time_to_spiner(time1, time2);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           alert = builder.create();
           alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
           alert.show();





    }

    // если 23ч аходит в интервал свободных
    // то при пыставлении 23 59 в  дату начала позволяет  дату окончания сделать меньше
    // смог с 7:00 записать на 7.00 и слежд запись косячная

    private void set_time_to_spiner(String time,String time2)throws ParseException {


        @SuppressLint("SimpleDateFormat")
        final SimpleDateFormat e_time=new SimpleDateFormat("HH:mm");
        //  final SimpleDateFormat s_time=new SimpleDateFormat("HH:mm");

        Date dt1= e_time.parse(time);
        final Date dt2= e_time.parse(time2);


        final int th1=dt1.getHours();
        final int th2=dt2.getHours();
        final int tm1=dt1.getMinutes();
        final int tm2=dt2.getMinutes();

        Log.i("Alert_time_to_spinner",String.valueOf(th1)+"   "+String.valueOf(th2));
        //  long minutes = dt2.getTime() - dt1.getTime();
        //   int deltaminutes = (int) (minutes / (60 * 1000));
        //   final String s1,s2;
        // установить минимальное значение равное часу t1 t2
// api23 выебывается

        h1.setMinValue(th1);
        h1.setMaxValue(th2);
        if(th1==th2){
            m1.setMinValue(tm1);
            m1.setMaxValue(tm2);

            m2.setMinValue(tm1);
            m2.setMaxValue(tm2);
            m2.setValue(tm2);
        }else{
            m1.setMinValue(tm1);
            m1.setMaxValue(59);

            m2.setMinValue(0);
            m2.setMaxValue(tm2);
        }

        h2.setMinValue(th1);
        h2.setMaxValue(th2);
        h2.setValue(th1+1);

        h1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Если установленное значение больше чем первый час
                //
                h2.setMinValue(newVal);
                h2.setMaxValue(th2);



                if(newVal>oldVal&&newVal>th1){
                    h2.setValue(newVal+1);
                    m2.setMinValue(0);
                    m2.setMaxValue(59);
                    m2.setValue(0);

                    m1.setMinValue(0);
                    m1.setMaxValue(59);
                    m1.setValue(0);

                    if(newVal==th2&&th2>th1){
                        m2.setMinValue(0);
                        m2.setMaxValue(tm2);
                        m2.setValue(tm2);

                        m1.setMinValue(0);
                        m1.setMaxValue(tm2);
                        m1.setValue(0);
                    }
                    if(h2.getValue()==th2){
                        m2.setMinValue(0);
                        m2.setMaxValue(tm2);

                    }
                }
                if(newVal<oldVal&&newVal>th1){
                    h2.setValue(newVal+1);
                    m2.setMinValue(0);
                    m2.setMaxValue(59);
                    m2.setValue(0);

                    m1.setMinValue(0);
                    m1.setMaxValue(59);

                    if(newVal==th1){
                        m2.setMinValue(0);
                        m2.setMaxValue(tm2);
                        m2.setValue(0);

                        m1.setMinValue(tm1);
                        m1.setMaxValue(59);
                    }
                    if(h2.getValue()==th2){
                        m2.setMinValue(0);
                        m2.setMaxValue(tm2);
                    }
                }
                if(newVal<oldVal&&newVal==th1){
                    h2.setValue(newVal+1);

                    m2.setMinValue(0);
                    m2.setMaxValue(59);
                    m2.setValue(0);

                    m1.setMinValue(tm1);
                    m1.setMaxValue(59);
                }
            }

        });

        m1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // Если время1=крайней границе интервала и

                if(h1.getValue()==th2){
                    m2.setMinValue(i1);
                    m2.setMaxValue(tm2);
                }else{

                    if(h1.getValue()==h2.getValue()){
                        m2.setMinValue(i1);
                        m2.setMaxValue(59);
                    }else{
                        m2.setMinValue(0);
                        m2.setMaxValue(tm2);
                    }
                }

            }
        });

        h2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
// если час последний то от нуля до последней минуты если нет от от0 до 50
         /*если h2=th2 то m2=tm2
         * и если th2=th1
         * */
                if(i1==th2){
                    m2.setMinValue(0);
                    m2.setMaxValue(tm2);
                }
                if(i1==th1){
                    m2.setMinValue(m1.getValue());
                    m2.setMaxValue(59);
                    if(th1==th2){
                        m2.setMinValue(m1.getValue());
                        m2.setMaxValue(tm2);
                    }

                }
                if(i1==h1.getValue()){

                    m2.setMinValue(m1.getValue());
                    m2.setMaxValue(59);
                    if(th1==th2){
                        m2.setMinValue(m1.getValue());
                        m2.setMaxValue(tm2);
                    }
                }

            }

        });

        m2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }

        });

    }

    @SuppressLint("DefaultLocale")
    private String formTime(int h, int m){
        return format("%02d",h)+":"+ format("%02d",m)+":00";
    }
}