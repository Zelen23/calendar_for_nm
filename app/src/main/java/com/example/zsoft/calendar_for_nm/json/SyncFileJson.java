package com.example.zsoft.calendar_for_nm.json;

import java.util.List;

/**
 * Created by adolf on 27.12.2018.
 */

public class SyncFileJson {

    public long timeshtamp;
    public String version;
    public List<Clients_write> clients_write;


    SyncFileJson(){

    }

    public static class Clients_write{

        public String time1;
        public String time2;
        public String date;
        public String name;
        public String sf_num;
        public String pay;
        public String date1;

        public Clients_write(String time1, String time2, String date, String name, String sf_num, String pay, String date1) {
            this.time1 = time1;
            this.time2 = time2;
            this.date = date;
            this.name = name;
            this.sf_num = sf_num;
            this.pay = pay;
            this.date1 = date1;
        }
    }


}
