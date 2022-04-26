package com.smartscale.controller;

import com.smartscale.util.Clock;
import com.smartscale.database.DatabaseConnection;
import com.smartscale.util.Switch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    private static String name, id, role;
    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblInvalidLoginMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
    }

    public void cancelButtonOnAction() throws IOException {
        Switch.switchTo("views/fruits.fxml", lblTimeAndDate);
    }

    public void loginButtonOnAction() {

        if (txtUsername.getText().isBlank() && txtPassword.getText().isBlank()) {
            lblInvalidLoginMessage.setText("Please enter username and password!");
        } else {
            validateLogin();
        }
    }

    public void validateLogin() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "SELECT accountID, role, firstname FROM users WHERE username = '" + txtUsername.getText() + "' AND password = '" + txtPassword.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            if (queryResult.next()) {
                name = queryResult.getString("firstname");
                id = queryResult.getString("accountID");
                role = queryResult.getString("role");

                if ((queryResult.getString("role").equals("administrator"))) {
                    switchToAdmin();
                } else {
                    switchToEmployee();
                }
            } else {
                lblInvalidLoginMessage.setText("Invalid username or password. Please try again!");
            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void switchToEmployee() throws IOException {

        Switch.switchTo("views/employee.fxml", lblTimeAndDate);
    }

    public void switchToAdmin() throws IOException {

        Switch.switchTo("views/admin.fxml", lblTimeAndDate);
    }

    public static String getName(){
        return name;
    }

    public static String getID(){
        return id;
    }

    public static String getRole(){
        return role;
    }
}
