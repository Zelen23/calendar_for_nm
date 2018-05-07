package com.example.zsoft.calendar_for_nm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.jar.Manifest;

/**
 * Created by azelinsky on 03.05.2018.
 */


public class CheckerDB extends MainActivity {


int PERMISSION_REQEST_CODE=123;
int REQEST_WRT_EXTERNAL=2;
public   static  final String WRITE_EXTERNAL_PERM= android.Manifest.permission
        .WRITE_EXTERNAL_STORAGE;
public  static  final String READ_EXTERNAL_PERM= android.Manifest.permission
        .READ_EXTERNAL_STORAGE;
private boolean status;

    private boolean isPermissionGranted( Context context,String perm){
        int premcheck=ActivityCompat.checkSelfPermission(context,perm);
        return premcheck== PackageManager.PERMISSION_GRANTED;
    }

    public void reqestPerm(Context context){
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                },PERMISSION_REQEST_CODE);

        refreser_MainAct();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQEST_WRT_EXTERNAL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешения получены", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Разрешения не получены", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        /*
        if(requestCode==REQEST_WRT_EXTERNAL){
            for(int i=0;i<permissions.length;i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //
                    Log.i("Perm_Gr",permissions[i]);

                } else {
                    //
                    Log.i("Perm_NO_Gr",permissions[i]);
                    Toast.makeText(this, "NoPerm", Toast.LENGTH_LONG).show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
        */

    }



    public boolean permsion_ckecker_status(Context context,String prm){
        status=false;
        if(isPermissionGranted(context,prm)){
            status=true;
        }else{
            reqestPerm(context);


        }
        return status;

    }
}
