package com.example.zsoft.calendar_for_nm.json;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class responseModel {
    ArrayList< Data > models = new ArrayList <Data> ();
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

}

class Version {
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
class Data{
      String status;
      Integer sequence;
      Integer showEventId;
      String showDate;
      String endTs;
      String [] externalIds;

      public Data(String status, Integer sequence, Integer showEventId, String showDate, String endTs, String[] externalIds) {
          this.status = status;
          this.sequence = sequence;
          this.showEventId = showEventId;
          this.showDate = showDate;
          this.endTs = endTs;
          this.externalIds = externalIds;
      }





  }



