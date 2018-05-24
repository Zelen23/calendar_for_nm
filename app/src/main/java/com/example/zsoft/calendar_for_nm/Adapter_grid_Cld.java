package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by adolf on 10.03.2018.
 */

public class Adapter_grid_Cld extends BaseAdapter {
    private Context mContext;

    private List<String> string;
    private  int[] image_id;
    //24,4,GMT+03:00 2018,2018,May
    private String[]ms_day;


    Adapter_grid_Cld(Context mContext) {
        this.mContext = mContext;

       // this.string = string;
       // this.image_id = image_id;
       // MainActivity main = new MainActivity();
       // ms_day = main.day();
    }
    void setAdapter_grid_Cld(List<String> string, int[] image_id){
        this.string = string;
        this.image_id = image_id;
        MainActivity main = new MainActivity();
        ms_day = main.day();
    }



    private class ViewHolder{
         TextView textView;
         ImageView imageView;

         ViewHolder(View item ){
            textView=item.findViewById(R.id.textDay);
            imageView=item.findViewById(R.id.im_day_View);
        }
    }

    void refresh(int mns,int year){
        List l_date=new Build_render_mass_grid_Cld().grv(mns,year);
        this.string=l_date;
        this.image_id=new  Build_render_mass_grid_Cld()
                .convert_mass_for_render(mContext,l_date
                ,mns,year);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return string.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SharedPreferences sharedPreferences=PreferenceManager
                .getDefaultSharedPreferences(mContext);
        int size= Integer.parseInt(sharedPreferences.getString("list_size","12"));
        String color=sharedPreferences.getString("list_color","White");


        final ViewHolder holder;
        View grid=view;


        LayoutInflater inflater=(LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(grid==null){
            new View(mContext);
            grid= inflater != null ? inflater.inflate(R.layout.layout_item, null) : null;

            holder=new ViewHolder(grid);
            if (grid != null) {
                grid.setTag(holder);
            }

        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.imageView.setImageResource(image_id[i]);
        holder.textView.setText(string.get(i));

        if(holder.textView.getText().toString().equals(ms_day[0])&&
                MainActivity.mns==Integer.parseInt(ms_day[1])&&
                MainActivity.year==Integer.parseInt(ms_day[3])){
            holder.textView.setTextColor(Color.parseColor("Gray"));


        }
        else
        holder.textView.setTextColor(Color.parseColor(color));
        holder.textView.setTextSize(size);


        if (grid != null) {
            grid.setTag(holder);
        }
        return grid;
    }

}
