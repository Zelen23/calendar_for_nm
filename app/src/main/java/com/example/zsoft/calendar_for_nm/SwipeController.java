package com.example.zsoft.calendar_for_nm;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by AZelinskiy on 25.05.2018.
 *
 * https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28
 */

 class SwipeController extends ItemTouchHelper.Callback {

     public  static  final  int LEFT=30;
    public  static  final  int  RIGHT=30;
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,LEFT| RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
