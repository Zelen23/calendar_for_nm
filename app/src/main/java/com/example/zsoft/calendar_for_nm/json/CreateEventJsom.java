package com.example.zsoft.calendar_for_nm.json;

import java.util.HashMap;
import java.util.List;

public class CreateEventJsom {


    public CreateEventJsom() {
    }

    List<obj> jsons;
    HashMap<String, List<obj>> modules;

    public static class obj{
        String name;

        public obj(String name, CreateEventJsom.params params) {
            this.name = name;
            this.params = params;
        }

        params params;

    }

public static class params{
    public params(String name, String description, Boolean isAllDay, Boolean participantsCanEdit, Boolean participantsCanInvite, Boolean othersCanView, String availability, Integer layerId, String start, String end) {
        this.name = name;
        this.description = description;
        this.isAllDay = isAllDay;
        this.participantsCanEdit = participantsCanEdit;
        this.participantsCanInvite = participantsCanInvite;
        this.othersCanView = othersCanView;
        this.availability = availability;
        this.layerId = layerId;
        this.start = start;
        this.end = end;
    }

    String name;
    String description;
    Boolean isAllDay;
    Boolean participantsCanEdit;
    Boolean participantsCanInvite;
    Boolean othersCanView;
    String availability;
    Integer layerId;
    String start;
    String end;


}

}
