package com.example.zsoft.calendar_for_nm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView grView_cld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grView_cld=(GridView)findViewById(R.id.gridView);
        String[] line=new String[35];
        for(int i=0;i<35;i++){
            line[i]=" "+i;
        }

        List<String> list_d=new ArrayList<String>();
        list_d.add("1");
        list_d.add("2");
        list_d.add("3");
        list_d.add("4");
        list_d.add("5");
        list_d.add("6");
        list_d.add("7");
        list_d.add("8");
        list_d.add("9");
        list_d.add("10");
        list_d.add("11");
        list_d.add("12");
        list_d.add("13");
        list_d.add("14");
        list_d.add("15");
        list_d.add("16");
        list_d.add("17");
        list_d.add("18");
        list_d.add("19");
        list_d.add("20");
        list_d.add("21");
        list_d.add("22");
        list_d.add("");
        list_d.add("");




        bild_mass_for_adapter creat_mass=new bild_mass_for_adapter();


        grView_cld.setAdapter(
                //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                new custom_grid_adapter(this,list_d,creat_mass.convert_mass_for_render(list_d)));

    }

    private void adjustGridView(){
        grView_cld.setNumColumns(7);
        //grView_cld.setColumnWidth(50);
    }
}
