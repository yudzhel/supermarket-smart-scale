package com.smartscale.controller;

import com.smartscale.DatabaseConnection;
import com.smartscale.model.Employee;
import com.smartscale.util.Clock;
import com.smartscale.util.Switch;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {

    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblCurrentlyLoggedInText;


    @FXML
    private TableView<Employee> tableEmployees;

    @FXML
    private TableColumn<Employee, Integer> colID;

    @FXML
    private TableColumn<Employee, String> colEmail;

    @FXML
    private TableColumn<Employee, String> colFirstName;

    @FXML
    private TableColumn<Employee, String> colLastName;

    @FXML
    private TableColumn<Employee, String> colPassword;

    @FXML
    private TableColumn<Employee, String> colPhone;

    @FXML
    private TableColumn<Employee, String> colUserType;

    @FXML
    private TableColumn<Employee, String> colUsername;


    @FXML
    private ComboBox<String> comboUserType;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUsername;

    ObservableList<Employee> employees;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        lblCurrentlyLoggedInText.setText("Currently logged in: " + LogInController.getName());
        populateTable();
    }

    public void backButtonOnAction() throws IOException {
        Switch.switchTo("views/admin.fxml", lblTimeAndDate);
    }

    public void populateTable(){

        comboUserType.getItems().addAll(
                "administrator", "employee");

        comboUserType.setPromptText("Please select one");


        colID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        colUserType.setCellValueFactory(new PropertyValueFactory<Employee, String>("userType"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
        colUsername.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));

        employees = DatabaseConnection.getEmployeesData();
        tableEmployees.setItems(employees);
    }

    public void addUser(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String query = "INSERT INTO users (firstname, lastname, username, password, role, email, phone) " +
                "VALUES (?,?,?,?,?,?,?)";

        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, txtFirstName.getText());
            ps.setString(2, txtLastName.getText());
            ps.setString(3, txtUsername.getText());
            ps.setString(4, txtPassword.getText());
            ps.setString(5, (String) comboUserType.getValue());
            ps.setString(6, txtEmail.getText());
            ps.setString(7, txtPhone.getText());
            ps.execute();

            populateTable();


        } catch (Exception e){

        }
    }
}
