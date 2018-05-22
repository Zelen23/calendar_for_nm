package com.example.zsoft.calendar_for_nm;

import android.util.Log;

/**
 * Created by adolf on 14.04.2018.
 */

public class Constructor_data {

    String h1,h2,m1,m2;
    String id,name;
    String sum;
    boolean flag;


    String[] parseTimefree(String t){
        String[] mst1=t.split(":");
        return mst1;

    }

    Constructor_data(String id,String t1,String t2,String name,String sum,boolean flag){
        this.id=id;
        this.h1=parseTimefree(t1)[0];
        this.h2=parseTimefree(t2)[0];
        this.m1=parseTimefree(t1)[1];
        this.m2=parseTimefree(t2)[1];

        this.name=name;
        this.sum=sum;

        this.flag=flag;
    }

    public static class Constructor_free_data{


        String h1,h2,m1,m2;

        String[] parseTimefree(String t){
            String[] mst1=t.split(":");
            return mst1;



        }
        Constructor_free_data(String t1,String t2){
            this.h1=parseTimefree(t1)[0];
            this.h2=parseTimefree(t2)[0];
            this.m1=parseTimefree(t1)[1];
            this.m2=parseTimefree(t2)[1];

        }


    }

}

 class Constructor_dayWeight{
    String day;
    int weight;

     Constructor_dayWeight(String day,int weight){
         this.day=day;
         this.weight=weight;

     }

}

