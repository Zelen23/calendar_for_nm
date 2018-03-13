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
        View grid;
        LayoutInflater inflater=(LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null){
            grid=new View(mContext);
            grid=inflater.inflate(R.layout.layout_item,null);

            ImageView image=(ImageView)grid.findViewById(R.id.im_day_View);
            image.setImageResource(Image_id[i]);

            TextView text=(TextView)grid.findViewById(R.id.textDay);
            text.setText(string.get(i));
            text.setTextColor(Color.parseColor("White"));




        }else{
                grid=(View)view;
        }

        return grid;
    }
}
