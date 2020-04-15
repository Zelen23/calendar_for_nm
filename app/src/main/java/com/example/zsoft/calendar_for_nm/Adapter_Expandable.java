package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by AZelinskiy on 14.06.2018.
 */

public class Adapter_Expandable extends BaseExpandableListAdapter {

    Context mContext;
    HashMap<String,List<Constructor_search>> search;
    List<String> groupName=new ArrayList<>();
    List<Constructor_search> child=new ArrayList<>();


    public Adapter_Expandable(Context mContext, HashMap<String, List<Constructor_search>> search) {
        this.mContext = mContext;
        this.search = search;


        for( List<Constructor_search>elt:search.values()){
            child.add(elt.iterator().next());
        }

        for (String elt: search.keySet()){
            //search.get(elt).iterator().next().name
            groupName.add(elt);
        }

    }


    @Override
    public int getGroupCount() {
        //return groupName.size();
        return search.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return search.get(groupName.get(groupPosition)).size();
        //return  search.get("").size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return  groupName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.exp_parent,null);

        }

        //Log.i("",""+groupPosition);


        TextView nameClient=(TextView)convertView.findViewById(R.id.nameClient);
        ImageView imageClient=(ImageView) convertView.findViewById(R.id.imageView);

        nameClient.setText(child.get(groupPosition).name);
        final String pk_num=child.get(groupPosition).sf_num;
        imageClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,UserEditActivity.class);
                intent.putExtra("pk_num", pk_num);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {


        if(convertView==null) {

        LayoutInflater layoutInflater=(LayoutInflater)
            mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.exp_item, null);
}

        RelativeLayout childItemRow=convertView.findViewById(R.id.childItemRow);
        TextView expItemTime=convertView.findViewById(R.id.expItemTime);
        TextView expItemDate=convertView.findViewById(R.id.expItemDate);
        ImageView expItemInfo=convertView.findViewById(R.id.expItemInfo);

        expItemTime.setText(search.get(child.get(groupPosition).sf_num).get(childPosition).time1);
        expItemDate.setText(new HelperData().ConvertDateFromDBX(
                search.get(child.get(groupPosition).sf_num).get(childPosition).date));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    @Override
    public void onGroupExpanded(int groupPosition) {



        super.onGroupExpanded(groupPosition);
    }


}
