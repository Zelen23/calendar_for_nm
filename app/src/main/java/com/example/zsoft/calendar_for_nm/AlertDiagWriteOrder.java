package com.example.zsoft.calendar_for_nm;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by adolf on 29.04.2018.
 */

public class AlertDiagWriteOrder  {

   public void Alert(final Context context){
       android.app.AlertDialog alert;

       LayoutInflater li= LayoutInflater.from(context);
       View vw=li.inflate(R.layout.frame_write,null);

       android.app.AlertDialog.Builder builder= new android.app.AlertDialog.Builder(context);
       builder.setView(vw)
               .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       Adapter_recycle ee=new Adapter_recycle(context);
                       ee.notifyDataSetChanged();
                       dialogInterface.dismiss();
                   }
               });

       alert = builder.create();
       alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
       alert.show();
   }
}
