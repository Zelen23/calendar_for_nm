package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AZelinskiy on 20.06.2018.
 */

public class Adapter_TopUser extends BaseAdapter {

    Context mContext;
    List <Constructor_top> constructorTop=new ArrayList<>();
    ExecDB execDB=new ExecDB();

    public Adapter_TopUser(Context mContext, List<Constructor_top> constructorTop) {
        this.mContext = mContext;
        this.constructorTop = constructorTop;
    }

    public class  ViewHolder{
        TextView itemTopName;
        TextView itemTopCount;
        TextView itemTopNumber;
        ImageView imageArrow;


        ViewHolder(View view){
           itemTopName=view.findViewById(R.id.itemTopName);
             itemTopCount=view.findViewById(R.id.itemTopCount);
             itemTopNumber=view.findViewById(R.id.itemTopNum);
             imageArrow=view.findViewById(R.id.imageArrow);



        }

    }

    @Override
    public int getCount() {
        return constructorTop.size() ;
    }

    @Override
    public Object getItem(int position) {

        return constructorTop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        final ViewHolder holder;
        LayoutInflater inflater=(LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(row==null){
            row=inflater.inflate(R.layout.item_top_list,parent,false);
            holder=new ViewHolder(row);

            if (row != null) {
                row.setTag(holder);
            }

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemTopName.setText(
              constructorTop.get(position).name);

        holder.itemTopCount.setText(
                constructorTop.get(position).count);

        holder.itemTopNumber.setText(
                constructorTop.get(position).pk_num);
        if(new HelperData().comparateDate(constructorTop.get(position).last)){
            holder.imageArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }else{
            holder.imageArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }

        return row;
    }
}
