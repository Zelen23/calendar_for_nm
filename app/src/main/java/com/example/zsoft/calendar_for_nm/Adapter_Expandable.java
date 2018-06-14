package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AZelinskiy on 14.06.2018.
 */

public class Adapter_Expandable implements ExpandableListAdapter {

    Context mContext;
    HashMap<String,List<String>> search;


    public Adapter_Expandable(Context mContext, HashMap<String, List<String>> search) {
        this.mContext = mContext;
        this.search = search;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return search.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<String> gr=new ArrayList<String>();
        for(String eit:search.keySet()){
            gr.add(eit);
        }
        String group=gr.get(groupPosition);
        List<String> childInfoList = search.get(group);
        return childInfoList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        List<String> gr=new ArrayList<String>();
        for(String eit:search.keySet()){
            gr.add(eit);
        }

        return  gr.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<String> gr=new ArrayList<String>();
        for(String eit:search.keySet()){
            gr.add(eit);
        }

        String group=gr.get(groupPosition);
        List<String> childInfoList = search.get(group);
        return childInfoList.get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LinearLayout groupLayoutView = new LinearLayout(mContext);
        groupLayoutView.setOrientation(LinearLayout.HORIZONTAL);

        // Create and add an imageview in returned group view.


        // Create and add a textview in returned group view.
        List<String> gr=new ArrayList<String>();
        for(String eit:search.keySet()){
            gr.add(eit);
        }


        String group=gr.get(groupPosition);
        TextView groupTextView = new TextView(mContext);
        groupTextView.setText(group);
        groupTextView.setTextSize(30);
        groupLayoutView.addView(groupTextView);

        return groupLayoutView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Object childObj = this.getChild(groupPosition, childPosition);
        String childText = (String)childObj;

        // Create a TextView to display child text.
        TextView childTextView = new TextView(mContext);
        childTextView.setText(childText);
        childTextView.setTextSize(20);
       // childTextView.setBackgroundColor(Color.GREEN);

        // Get group image width.

        // Set child textview offset left. Then it will align to the right of the group image.


        return childTextView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
