package com.smartscale;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FruitsController implements Initializable {
    @FXML
    private Label lblTimeAndDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
    }


    public void buttonSignInOnAction() throws IOException {

        Switch.switchTo("views/login.fxml",lblTimeAndDate);
    }
}