module com.smart.scale.supermarketsmartscale {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.smartscale to javafx.fxml;
    exports com.smartscale;
}