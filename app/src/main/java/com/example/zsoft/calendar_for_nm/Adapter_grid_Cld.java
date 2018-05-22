package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
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
    private final int[] Image_id;
    private String[]ms_day;


     Adapter_grid_Cld(Context mContext, List<String> string, int[] image_id) {
        this.mContext = mContext;
        this.string = string;
        Image_id = image_id;
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

        holder.imageView.setImageResource(Image_id[i]);
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
