package com.example.zsoft.calendar_for_nm;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
        return  search.get(child.get(groupPosition)).get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if(convertView==null) {

            LayoutInflater layoutInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.exp_parent, parent,false);
        }


        TextView nameClient=(TextView)convertView.findViewById(R.id.nameClient);
       // ImageButton imageClient=(ImageButton)convertView.findViewById(R.id.imageClient);

        nameClient.setText(child.get(groupPosition).name);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

if(convertView==null) {

   LayoutInflater layoutInflater=(LayoutInflater)
            mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    convertView = layoutInflater.inflate(R.layout.exp_item, null);
}



        TextView expItemTime=convertView.findViewById(R.id.expItemTime);
        TextView expItemDate=convertView.findViewById(R.id.expItemDate);
        ImageButton expItemInfo=convertView.findViewById(R.id.expItemInfo);


        expItemTime.setText(search.get(child.get(groupPosition).sf_num).get(childPosition).time1);
        expItemDate.setText(search.get(child.get(groupPosition).sf_num).get(childPosition).date);

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
