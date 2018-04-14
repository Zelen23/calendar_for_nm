package com.example.zsoft.calendar_for_nm;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

public class Adapter_recycle extends RecyclerView.Adapter<Adapter_recycle.ViewHolder>{
    List<Constructor_data> data;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        EditText editText;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card);
            textView=(TextView) itemView.findViewById(R.id.textView12);
            editText=(EditText)itemView.findViewById(R.id.editText2);
            checkBox=(CheckBox) itemView.findViewById(R.id.checkBox2);

        }

    }

    Adapter_recycle(List<Constructor_data> data){
        this.data=data;
    }

    @Override
    public Adapter_recycle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter_recycle.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).name);
        holder.editText.setText(String.valueOf(data.get(position).sum));
        holder.checkBox.setChecked(data.get(position).flag);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
