package com.example.zsoft.calendar_for_nm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // переписать на фрагменты

    GridView grView_cld;
    TextView l_date, l_year;
    ImageButton b_set;
    ConstraintLayout layout;
    RecyclerView recyclerViewMain;

    public static int today;
    public static int mns;
    public static int year;
    public static String mns_name;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    public   static int[] back={
            R.drawable.gradient_1,
            R.drawable.gradient_2,
            R.drawable.gradient_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grView_cld=(GridView)findViewById(R.id.gridView);
        b_set=(ImageButton)findViewById(R.id.settings);
        recyclerViewMain=(RecyclerView)findViewById(R.id.recycleMain);
        layout=(ConstraintLayout)findViewById(R.id.layout_id);
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(this);

        String index_background=sharedPreferences.getString("background","0");
        layout.setBackgroundResource(back[Integer.parseInt(index_background)]);


        // Прикутил слушатель на грид
        final GestureDetector gestureDetector=new GestureDetector(new GestureListener() );
        grView_cld.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return event.getAction()==MotionEvent.ACTION_MOVE;
            }
        });
        b_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),preference.class);
                startActivity(intent);
            }
        });
//из базы рпскрасить месяц при старте
        final String [] day=day();
        today=Integer.parseInt(day[0]);
        mns=Integer.parseInt(day[1]);
        year=Integer.parseInt(day[3]);
        mns_name=day[4];

        /*при старте проверяю есть ли разрещение если нет- не даю листать календарь
        * */
        ExecuteDB executeDB=new ExecuteDB();
        executeDB.permsion_ckecker(this);





        bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();
        final List<String> list_date=creat_mass.grv(mns,year);
        //получаю цветной массив
        final int [] mass_pict= creat_mass.convert_mass_for_render
                (this,creat_mass.grv(mns,year)
                        ,mns
                        ,year);

        set_date_to_label(mns,today,year);
        //      готовые массивы 1-с датами и пробелами 2- с цветами
        grView_cld.setAdapter(new custom_grid_adapter(this, list_date
                ,mass_pict)
        );

        // клик по лейблу даты возвращает на текущуу страницу
        l_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_date_to_label(
                        Integer.parseInt(day[1]),
                        Integer.parseInt(day[0]),
                        Integer.parseInt(day[3])
                );
                today=Integer.parseInt(day[0]);
                mns=Integer.parseInt(day[1]);
                year=Integer.parseInt(day[3]);
                mns_name=day[4];

                grView_cld.setAdapter(
                        //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                        new custom_grid_adapter(MainActivity.this, list_date, mass_pict));

            }
        });

        viewRecycle();
    }




    public void viewRecycle(){
       List data=new ArrayList<>();
        data.add(new Constructor_data("ann",100,false,"17","35",
                "18","55"));
        data.add(new Constructor_data("jane",200,true,"18","55",
                "19","20"));
        data.add(new Constructor_data.Constructor_free_data("19","20",
                "20","45"));
        data.add(new Constructor_data("гена",700,false,"17","35",
                "18","55"));
        data.add(new Constructor_data("ann",100,false,"17","35",
                "18","55"));
        data.add(new Constructor_data("jane",200,true,"17","35",
                "18","55"));
        data.add(new Constructor_data("Eedfhjd Dgfkgfk",1300,true,"17","35",
                "18","55"));
        data.add(new Constructor_data.Constructor_free_data("19","20",
                "20","45"));


        LinearLayoutManager li=new LinearLayoutManager(MainActivity.this);
        recyclerViewMain.setLayoutManager(li);
        Adapter_recycle adapter=new Adapter_recycle(MainActivity.this);
        adapter.setAdapter_recycle(data);
        recyclerViewMain.setAdapter(adapter);

    }

    // Разбор текущей даты для проверок

    String[] day() {
//month-day
        GregorianCalendar cld_m = new GregorianCalendar();
        String day = cld_m.getTime().toString();
        String[] getday = day.split(" ", 5);
        int mn = cld_m.get(Calendar.MONTH);
        String[] show_day = new String[5];
        show_day[0] = String.valueOf(Integer.parseInt(getday[2]));
        show_day[1] = String.valueOf(mn);
        show_day[2] = String.valueOf(getday[4]);
        show_day[3] = String.valueOf(cld_m.get(Calendar.YEAR));
        show_day[4] = getday[1];


        Log.i("today**", getday[2]);
        Log.i("mns_name", getday[1]);
        Log.i("**", getday[0]);
        Log.i("int_mns*", String.valueOf(mn));


        return show_day;
    }

    // размерность грида
    private void adjustGridView(){
        grView_cld.setNumColumns(7);
        grView_cld.setColumnWidth(30);
    }

    // шапка дата и год
    void set_date_to_label(int month,int day,int th_syear){

        List<String> l_month=new ArrayList<String>();
        l_month.add("Январь");
        l_month.add("Февраль");
        l_month.add("Март");
        l_month.add("Апрель");
        l_month.add("Май");
        l_month.add("Июнь");
        l_month.add("Июль");
        l_month.add("Август");
        l_month.add("Сентябрь");
        l_month.add("Октябрь");
        l_month.add("Ноябрь");
        l_month.add("Декабрь");


        l_date=(TextView)findViewById(R.id.date_label);
        l_year=(TextView)findViewById(R.id.year_label);
        if (day==0){
            l_date.setText(l_month.get(month));

        }else

        l_date.setText(l_month.get(month)+" "+day);
        l_year.setText(""+th_syear);

    }



    // слушатель жестов
    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        final bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();


    // прикрутил слушатель к одиночному клику
        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            grView_cld.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    // дата для запроса
                    SharedPreferences sharedPreferencs = PreferenceManager
                            .getDefaultSharedPreferences(MainActivity.this);
                    String edit_valve=sharedPreferencs.getString("background","0");
                    String day_of_pos=String.valueOf(
                            creat_mass.grv(mns,year).get(position));

                    try{
                        String s_date= day_of_pos+":" +mns+ ":"+year;
                        Toast.makeText(MainActivity.this,
                                "++"+s_date,Toast.LENGTH_SHORT).show();
                        set_date_to_label(mns, Integer.parseInt(day_of_pos), year);
                    }
                     catch(NumberFormatException ex){
                     Log.i("number_format",ex.getMessage());
                    }
                    /*
                    if(day_of_pos!=" ") {


                        Toast.makeText(MainActivity.this,"++"+edit_valve,Toast.LENGTH_SHORT).show();
                        set_date_to_label(mns, Integer.parseInt(day_of_pos), year);
                    }
                    */
                }
            });
//LongClick
            grView_cld.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView,
                                               View view, int i, long l) {
                    Intent intent=new Intent(MainActivity.this,Recycle_windows.class);
                    startActivity(intent);
                    return false;
                }

            });


            return super.onSingleTapUp(e);


        }


        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            bild_mass_for_adapter day_in_this_month=new bild_mass_for_adapter();

            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) >
                    SWIPE_THRESHOLD_VELOCITY) {
                if(mns<11) {
                    mns++;
                }else{
                    mns=0;
                    year++;

                }

                set_date_to_label(mns,0,year);
                grView_cld.setAdapter(
                        //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                        new custom_grid_adapter(MainActivity.this,
                                creat_mass.grv(mns,year),
                                creat_mass.convert_mass_for_render(MainActivity.this
                                        ,creat_mass.grv(mns,year)
                                        ,mns
                                        ,year)));

                return false; // справа налево

            }  else if (e2.getX() - e1.getX() >
                    SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(mns>0){
                    mns--;
                }else{
                    mns=11;
                    year--;
                }
                final bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();
                set_date_to_label(mns,0,year);
                grView_cld.setAdapter(
                        //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                        new custom_grid_adapter(MainActivity.this,
                                creat_mass.grv(mns,year),
                                creat_mass.convert_mass_for_render(MainActivity.this
                                        ,creat_mass.grv(mns,year)
                                        ,mns
                                        ,year)));

                return false; // слева направо
            }


            day_in_this_month.dat();

            return false;
        }
    }
}
