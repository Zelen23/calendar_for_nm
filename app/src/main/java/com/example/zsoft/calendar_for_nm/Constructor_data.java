package com.example.zsoft.calendar_for_nm;

/**
 * Created by adolf on 14.04.2018.
 */

public class Constructor_data {
    String h1,h2,m1,m2;
    String name;
    int sum;
    boolean flag;

    Constructor_data(String name,int sum,boolean flag,String h1,String m1,String h2,String m2){
        this.name=name;
        this.sum=sum;
        this.flag=flag;
        this.h1=h1;
        this.h2=h2;
        this.m1=m1;
        this.m2=m2;
    }

    public static class Constructor_free_data{
        String h1,h2,m1,m2;
        Constructor_free_data(String h1,String m1,String h2,String m2){
            this.h1=h1;
            this.h2=h2;
            this.m1=m1;
            this.m2=m2;

        }


    }
}


