package com.smartscale.model;


import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class Logbook {

   private SimpleStringProperty datetime;
   private String message;

    public Logbook(SimpleStringProperty datetime, String message) {
        this.datetime = datetime;
        this.message = message;
    }

    public String getDatetime() {
        return datetime.get();
    }

    public SimpleStringProperty datetimeProperty() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime.set(datetime);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
