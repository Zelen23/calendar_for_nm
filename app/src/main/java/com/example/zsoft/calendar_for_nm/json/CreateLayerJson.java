package com.example.zsoft.calendar_for_nm.json;

import java.util.List;

public class CreateLayerJson {

    List<obj> models;

    public CreateLayerJson() {
    }

    public static class obj{
        String name;

        public obj(String name, CreateLayerJson.params params) {
            this.name = name;
            this.params = params;
        }

        params params;

    }

public static class params{

String type;
String name;

    public params(String type, String name, String color, Boolean notifyAboutEventChanges, Boolean isEventsClosedByDefault, Boolean affectAvailability, Boolean isDefault, List<Notifications> notifications) {
        this.type = type;
        this.name = name;
        this.color = color;
        this.notifyAboutEventChanges = notifyAboutEventChanges;
        this.isEventsClosedByDefault = isEventsClosedByDefault;
        this.affectAvailability = affectAvailability;
        this.isDefault = isDefault;
        this.notifications = notifications;
    }

    String color;
Boolean notifyAboutEventChanges;
Boolean isEventsClosedByDefault;
Boolean affectAvailability;
Boolean isDefault;
List<Notifications> notifications;



}
    public static class  Notifications{

        public Notifications(String channel, String offset) {
            this.channel = channel;
            this.offset = offset;
        }

        String channel;
        String offset;

    }
}
