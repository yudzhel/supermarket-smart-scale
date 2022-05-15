package com.smartscale.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ShowMessage {

    // Dialog windows

    public static void displayErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void displayInformationDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    //Product Message Button

    public static void displayChooseProductMessage(Button button) {
        button.setText("PLEASE CHOOSE A PRODUCT!");
    }

    public static void displayEnterKgMessage(Button button) {
        button.setText("PLEASE ENTER KG!");
    }

    public static void displayGetReceiptMessage(Button button) {
        button.setText("CLICK GET RECEIPT TO FINISH!");
    }
}
