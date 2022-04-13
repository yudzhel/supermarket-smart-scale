module com.smart.scale.supermarketsmartscale {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.smartscale to javafx.fxml;
    exports com.smartscale;
}