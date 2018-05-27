package com.example.zsoft.calendar_for_nm;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by AZelinskiy on 25.05.2018.
 *
 * https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28
 *
 * https://androidtutorialmagic.wordpress.com/android-material-design-tutorial/sectionheader-with-recyclerview-and-swipe-to-addeditdelete/
 */

 class SwipeController extends ItemTouchHelper.Callback {

     public  static  final  int LEFT=30;
    public  static  final  int  RIGHT=30;
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag=0;
        int swipeFlag=0;
        if(viewHolder instanceof Adapter_recycle.FullHolder){
            swipeFlag=ItemTouchHelper.START|ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlag,swipeFlag);
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

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return  true;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {






        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }


}

