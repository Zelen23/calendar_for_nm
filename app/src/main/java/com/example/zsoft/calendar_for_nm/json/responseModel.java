package com.example.zsoft.calendar_for_nm.json;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class responseModel {


    public void setModels(ArrayList<ListParams> models) {
        this.models = models;
    }

    private ArrayList< ListParams > models = new ArrayList <ListParams> ();
    private String uid;
    private float timestamp;
    private boolean cookieRenew;
    Version VersionObject;
    private String reqid;


    // Getter Methods

    public String getUid() {
        return uid;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public boolean getCookieRenew() {
        return cookieRenew;
    }

    public Version getVersion() {
        return VersionObject;
    }

    public String getReqid() {
        return reqid;
    }

    public ArrayList<ListParams> getModels() {
        return models;
    }

    // Setter Methods

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

    public void setCookieRenew(boolean cookieRenew) {
        this.cookieRenew = cookieRenew;
    }

    public void setVersion(Version versionObject) {
        this.VersionObject = versionObject;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }
    public class ListParams{
        public ListParams(String status, String name, DataParams data) {
            this.status = status;
            this.name = name;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DataParams getData() {
            return data;
        }

        public void setData(DataParams data) {
            this.data = data;
        }

        String status;
        String name;
        DataParams data;



    }
    public class DataParams{
        String status;
        Integer sequence;
        Integer showEventId;
        String showDate;
        String endTs;
        String [] externalIds;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getSequence() {
            return sequence;
        }

        public void setSequence(Integer sequence) {
            this.sequence = sequence;
        }

        public Integer getShowEventId() {
            return showEventId;
        }

        public void setShowEventId(Integer showEventId) {
            this.showEventId = showEventId;
        }

        public String getShowDate() {
            return showDate;
        }

        public void setShowDate(String showDate) {
            this.showDate = showDate;
        }

        public String getEndTs() {
            return endTs;
        }

        public void setEndTs(String endTs) {
            this.endTs = endTs;
        }

        public String[] getExternalIds() {
            return externalIds;
        }

        public void setExternalIds(String[] externalIds) {
            this.externalIds = externalIds;
        }



        public DataParams(String status, Integer sequence, Integer showEventId, String showDate, String endTs, String[] externalIds) {
            this.status = status;
            this.sequence = sequence;
            this.showEventId = showEventId;
            this.showDate = showDate;
            this.endTs = endTs;
            this.externalIds = externalIds;
        }





    }
    public class Version {
        private String duffman;
        private String maya;


        // Getter Methods

        public String getDuffman() {
            return duffman;
        }

        public String getMaya() {
            return maya;
        }

        // Setter Methods

        public void setDuffman(String duffman) {
            this.duffman = duffman;
        }

        public void setMaya(String maya) {
            this.maya = maya;
        }

    }
}





