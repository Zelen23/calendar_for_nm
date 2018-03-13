package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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

public class custom_grid_adapter extends BaseAdapter {
    private Context mContext;
    private final List<String> string;
    private final int[] Image_id;

    public custom_grid_adapter(Context mContext, List<String> string, int[] image_id) {
        this.mContext = mContext;
        this.string = string;
        Image_id = image_id;
    }

    private class ViewHolder{
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View item ){
            textView=(TextView)item.findViewById(R.id.textDay);
            imageView=(ImageView) item.findViewById(R.id.im_day_View);
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        View grid=view;

        LayoutInflater inflater=(LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(grid==null){
            grid=new View(mContext);
            grid=inflater.inflate(R.layout.layout_item,null);

            holder=new ViewHolder(grid);
            grid.setTag(holder);

        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.imageView.setImageResource(Image_id[i]);
        holder.textView.setText(string.get(i));
        holder.textView.setTextColor(Color.parseColor("White"));

        grid.setTag(holder);
        return grid;
    }
}
