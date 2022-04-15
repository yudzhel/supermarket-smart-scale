package com.smartscale.controller;

import com.smartscale.util.Clock;
import com.smartscale.util.Switch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblCurrentlyLoggedInText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        lblCurrentlyLoggedInText.setText("Currently logged in: " + LogInController.name);
    }

    public void logoutButtonOnAction() throws IOException {
        Switch.switchTo("views/login.fxml", lblTimeAndDate);
    }

    public void myAccountButtonOnAction() throws IOException {
        Switch.switchTo("views/myaccount.fxml", lblTimeAndDate);
    }
}