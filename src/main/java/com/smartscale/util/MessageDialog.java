package com.smartscale.util;

import javafx.scene.control.Alert;

public class MessageDialog {

    public static void displayError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void displayInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
