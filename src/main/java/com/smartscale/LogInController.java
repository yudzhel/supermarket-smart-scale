package com.smartscale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.EventObject;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblInvalidLoginMessage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    static String name;
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
    }

    public void cancelButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fruits.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
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

        String verifyLogin = "SELECT role, firstname FROM users WHERE username = '" + txtUsername.getText() + "' AND password = '" + txtPassword.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            if (queryResult.next()) {
                name = queryResult.getString("firstname");

                if ((queryResult.getString("role").equals("administrator"))) {
                    switchToAdmin();
                } else {
                    switchToEmployee(stage);
                }
            } else {
                lblInvalidLoginMessage.setText("Invalid username or password. Please try again!");
            }


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void switchToEmployee(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employee.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("styles.css")).toExternalForm());
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAdmin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("styles.css")).toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
