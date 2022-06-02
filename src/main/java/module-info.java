module com.smart.scale.supermarketsmartscale {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires com.google.common;
    requires io;
    requires kernel;
    requires layout;


    opens com.smartscale to javafx.fxml;
    exports com.smartscale;
    exports com.smartscale.controller;
    opens com.smartscale.controller to javafx.fxml;
    exports com.smartscale.util;
    opens com.smartscale.util to javafx.fxml;
    exports com.smartscale.model;
    opens com.smartscale.model to javafx.fxml;
    exports com.smartscale.database;
    opens com.smartscale.database to javafx.fxml;
}