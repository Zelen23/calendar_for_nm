package com.example.zsoft.calendar_for_nm;



/**
 * Created by adolf on 14.04.2018.
 */

class Constructor_data {

    String h1,h2,m1,m2;
    String id,name;
    String sum;
    boolean flag;


    private String[] parseTimefree(String t){
        return t.split(":");

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

    static class Constructor_free_data{

        String h1,h2,m1,m2;

        String[] parseTimefree(String t){
            return t.split(":");
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

class Constructor_search{
    String sf_num,name,time1,date,date1;

     Constructor_search(String sf_num, String name, String time1, String date, String date1) {
        this.sf_num = sf_num;
        this.name = name;
        this.time1 = time1;
        this.date = date;
        this.date1 = date1;
    }
}
