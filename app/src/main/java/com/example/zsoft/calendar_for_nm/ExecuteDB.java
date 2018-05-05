package com.example.zsoft.calendar_for_nm;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.jar.Manifest;

/**
 * Created by azelinsky on 03.05.2018.
 */


public class ExecuteDB extends MainActivity {
    private db mDbHelper;
    boolean status;

int PERMISSION_REQEST_CODE=123;
int REQEST_WRT_EXTERNAL=2;
private  static  final String WRITE_EXTERNAL_PERM= android.Manifest.permission
        .WRITE_EXTERNAL_STORAGE;
private  static  final String READ_EXTERNAL_PERM= android.Manifest.permission
        .READ_EXTERNAL_STORAGE;

    private boolean isPermissionGranted( Context context,String perm){
        int premcheck=ActivityCompat.checkSelfPermission(context,perm);
        return premcheck== PackageManager.PERMISSION_GRANTED;
    }

    private void reqestPerm(Context context){
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                },
                PERMISSION_REQEST_CODE);


    }
//походу не работает
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode==REQEST_WRT_EXTERNAL){
            for(int i=0;i<permissions.length;i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //
                    status = false;
                    Log.i("Perm_Gr", permissions[i]);

                }
            }
        }

    }



    public boolean permsion_ckecker(Context context){

        if(isPermissionGranted(context,WRITE_EXTERNAL_PERM)){
            status=true;
        }else{
            reqestPerm(context);


        }
        return  status;
    }
}
