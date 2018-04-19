package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by adolf on 14.04.2018.
 */

public class Adapter_recycle extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Object> data;
    private final static int TYPE_FULL=1,TYPE_EMPTY=2;

    private Context context;

    // Constructor
    public Adapter_recycle(Context context){
        this.context=context;
    }

  public void   setAdapter_recycle(List<Object> data){
        this.data=data;
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
       // int layout=0;
       // RecyclerView.ViewHolder viewHolder;
        View view;
        switch (viewType){
            case TYPE_FULL:
                view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card,parent,false);
                return new FullHolder(view);

                /*
                layout=R.layout.card;
                View full_view=LayoutInflater.from(parent.getContext())
                        .inflate(layout,parent,false);
                viewHolder= new FullHolder(full_view);
                break;
                */


            case  TYPE_EMPTY:
                view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_empty,parent,false);
                return new EmptyHolder(view);

                /*
                layout=R.layout.card_empty;
                View empty_view=LayoutInflater.from(parent.getContext())
                        .inflate(layout,parent,false);
                viewHolder= new EmptyHolder(empty_view);
                 break;

            default:
                viewHolder=null;
                break;
                */
        }
       // return viewHolder;
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

        public FullHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card);
            textView=(TextView) itemView.findViewById(R.id.textView12);
            editText=(EditText)itemView.findViewById(R.id.editText2);
            checkBox=(CheckBox) itemView.findViewById(R.id.checkBox2);
        }
        public void show_data(Constructor_data ful_data){
            textView.setText(ful_data.name);
            editText.setText(String.valueOf(ful_data.sum));
            checkBox.setChecked(ful_data.flag);

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

        public void show_data(Constructor_data.Constructor_free_data free){
            h.setText(free.h1);
            m.setText(free.m1);
            h2.setText(free.h2);
            m2.setText(free.m2);

        }


    }


}
