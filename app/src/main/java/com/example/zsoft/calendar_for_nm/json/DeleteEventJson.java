package com.example.zsoft.calendar_for_nm.json;

import java.util.List;

public class DeleteEventJson {

    List<DeleteEventJson.obj> models;
    public DeleteEventJson() {
    }


    public static class obj{
        String name;
        DeleteEventJson.params params;

        public obj(String name, DeleteEventJson.params params) {
            this.name = name;
            this.params = params;
        }


    }
    public static class params{
      Integer id;
      Integer sequence;
      Boolean applyToFuture;

        public params(Integer id, Integer sequence, Boolean applyToFuture) {
            this.id = id;
            this.sequence = sequence;
            this.applyToFuture = applyToFuture;
        }



    }
}
