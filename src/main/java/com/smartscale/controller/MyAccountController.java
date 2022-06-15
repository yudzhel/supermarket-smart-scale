package com.smartscale.controller;

import com.smartscale.util.Clock;
import com.smartscale.database.DatabaseConnection;
import com.smartscale.util.Switch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {

    public static String role;
    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblCurrentlyLoggedInText;
    @FXML
    private TextField txtID, txtFirstName, txtLastName, txtUserType, txtEmail, txtPhone, txtUsername, txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Clock.initClock(lblTimeAndDate);
            lblCurrentlyLoggedInText.setText("Currently logged in: " + LogInController.getUsername());
            getDataFromTablesAndAddToTextFields();
    }

    public void backButtonOnAction() throws IOException {

        if(role.equals("admin")){
            Switch.switchTo("views/admin.fxml","Admin", lblTimeAndDate);
        }
        else {
            Switch.switchTo("views/employee.fxml","Employee", lblTimeAndDate);
        }

    }

    public void getDataFromTablesAndAddToTextFields(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String query = "SELECT * FROM users WHERE accountID = " + Integer.valueOf(LogInController.getID());

        try {
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            if (queryResult.next()) {
                int accountID = queryResult.getInt("accountID");
                String firstName = queryResult.getString("firstname");
                String lastName = queryResult.getString("lastname");
                String userType = queryResult.getString("role");
                String email = queryResult.getString("email");
                String phone = queryResult.getString("phone");
                String username = queryResult.getString("username");
                String password = queryResult.getString("password");

                addValuesToTextFields(accountID, firstName, lastName, userType, email, phone, username, password);
                role = userType;

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void addValuesToTextFields(int id, String firstName, String lastName, String userType, String email,
    String phone, String username, String password){

        txtID.setText(String.valueOf(id));
        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtUserType.setText(userType);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtUsername.setText(username);
        txtPassword.setText(password);
    }


}
