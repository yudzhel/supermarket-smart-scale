module com.smart.scale.supermarketsmartscale {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.smartscale to javafx.fxml;
    exports com.smartscale;
    exports com.smartscale.controller;
    opens com.smartscale.controller to javafx.fxml;
    exports com.smartscale.util;
    opens com.smartscale.util to javafx.fxml;
}