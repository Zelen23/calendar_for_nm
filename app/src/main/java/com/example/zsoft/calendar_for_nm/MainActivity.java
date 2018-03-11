package com.example.zsoft.calendar_for_nm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    GridView grView_cld;

    public static int[] image_item={
            R.mipmap.maxday,
            R.mipmap.empty,
            R.mipmap.nowday,
            R.mipmap.maxday,
            R.mipmap.empty,
            R.mipmap.nowday

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grView_cld=(GridView)findViewById(R.id.gridView);
        String[] line=new String[35];
        for(int i=0;i<35;i++){
            line[i]=" "+i;
        }

        grView_cld.setAdapter(
              //  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,line));
                new custom_grid_adapter(this,line,null));

    }

    private void adjustGridView(){
        grView_cld.setNumColumns(7);
        //grView_cld.setColumnWidth(50);
    }
}
