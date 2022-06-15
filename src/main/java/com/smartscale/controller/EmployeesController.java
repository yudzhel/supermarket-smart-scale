package com.smartscale.controller;

import com.smartscale.database.DatabaseConnection;
import com.smartscale.database.EmployeeDAO;
import com.smartscale.database.RecentActivityDAO;
import com.smartscale.model.Employee;
import com.smartscale.util.Clock;
import com.smartscale.util.ShowMessage;
import com.smartscale.util.Switch;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtSearchBar;

    ObservableList<Employee> employees;

    PreparedStatement ps = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        lblCurrentlyLoggedInText.setText("Currently logged in: " + LogInController.getUsername());
        populateComboBox();
        populateTable();
        searchBar();
    }

    public void backButtonOnAction() throws IOException {
        Switch.switchTo("views/admin.fxml","Admin", lblTimeAndDate);
    }

    public void populateTable(){

        colID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        colUserType.setCellValueFactory(new PropertyValueFactory<Employee, String>("userType"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
        colUsername.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));

        employees = EmployeeDAO.getEmployeesData();
        tableEmployees.setItems(employees);
    }

    public void addUser(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String query = "INSERT INTO users (firstname, lastname, username, password, role, email, phone) " +
                "VALUES (?,?,?,?,?,?,?)";

        if(areRequiredFieldsAreFilled()){
            try{

                String username = txtUsername.getText();

                ps = connection.prepareStatement(query);
                ps.setString(1, txtFirstName.getText().trim());
                ps.setString(2, txtLastName.getText().trim());
                ps.setString(3, txtUsername.getText().trim());
                ps.setString(4, txtPassword.getText().trim());
                ps.setString(5, comboUserType.getValue().trim());
                ps.setString(6, txtEmail.getText().trim());
                ps.setString(7, txtPhone.getText().trim());
                ps.execute();

                populateTable();
                clearTextFields();
                searchBar();
                ShowMessage.displayInformationDialog("User " + username + " was added successfully!");
                RecentActivityDAO.add(LogInController.getUsername() + " added a new user (" + username + ")");

            } catch (Exception e){
                ShowMessage.displayErrorDialog(e.getMessage());
            }
        }
        else {
            ShowMessage.displayErrorDialog("Please fill all necessary fields (First Name, Last Name," +
                    "Username, Password, User Type and Phone)!");
        }

    }

    public void updateUser() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String query = "UPDATE users SET firstname = '" + txtFirstName.getText().trim() +
                "', lastname = '" + txtLastName.getText().trim() +
                "', username = '" + txtUsername.getText().trim() +
                "', password = '" + txtPassword.getText().trim() +
                "', role = '" + comboUserType.getSelectionModel().getSelectedItem() +
                "', email = '" + txtEmail.getText().trim() +
                "', phone = '" + txtPhone.getText().trim() + "' WHERE accountID = " + txtID.getText();

        if(!txtID.getText().isEmpty()){
            try{

                String username = txtUsername.getText().trim();
                statement.execute(query);
                populateTable();
                clearTextFields();
                searchBar();
                ShowMessage.displayInformationDialog("Update was successful! " + "(Username: " + username + ")");
                RecentActivityDAO.add(LogInController.getUsername() + " updated user (" + username + ")");

            } catch (Exception e){
                ShowMessage.displayErrorDialog(e.getMessage());
            }
        } else {
            ShowMessage.displayErrorDialog("Please choose a user to update!");
        }

    }

    public void deleteUser() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String query = "DELETE FROM users WHERE accountID = " + txtID.getText();

        if(!txtID.getText().isEmpty()){
            try{

                String username = txtUsername.getText();
                statement.execute(query);
                populateTable();
                clearTextFields();
                searchBar();
                ShowMessage.displayInformationDialog("User (" + username + ") was deleted!");
                RecentActivityDAO.add(LogInController.getUsername() + " deleted user (" + username + ")");

            } catch (Exception e){
                ShowMessage.displayErrorDialog(e.getMessage());
            }
        } else {
            ShowMessage.displayErrorDialog("Please choose a user to delete!");
        }

    }

    public void clearTextFields(){

        txtID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        comboUserType.getSelectionModel().clearSelection();

    }

    public void tableToTextFields(){
        Employee employee = tableEmployees.getSelectionModel().getSelectedItem();

        try {
            txtID.setText(String.valueOf(employee.getId()));
            txtFirstName.setText(employee.getFirstName());
            txtLastName.setText(employee.getLastName());
            txtEmail.setText(employee.getEmail());
            txtPhone.setText(employee.getPhone());
            comboUserType.getSelectionModel().select(employee.getUserType());
            txtUsername.setText(employee.getUsername());
            txtPassword.setText(employee.getPassword());
        } catch (Exception ignored){

        }

    }

    public void populateComboBox(){
        comboUserType.getItems().addAll(
                "admin", "employee");

        comboUserType.setPromptText("Please select one");
    }

    public void searchBar(){
        FilteredList<Employee> filteredList = new FilteredList<>(employees, b -> true);
        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(employee -> {

            if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                return  true;
            }

            String searchKeyword = newValue.toLowerCase();

            if(employee.getFirstName().toLowerCase().contains(searchKeyword)){
                return true; // match found
            } else if (employee.getLastName().toLowerCase().contains(searchKeyword)){
                return true;
            } else if (employee.getEmail().toLowerCase().contains(searchKeyword)){
                return true;
            }
            else return false;
        }));

        SortedList<Employee> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableEmployees.comparatorProperty());
        tableEmployees.setItems(sortedList);
    }

    private boolean areRequiredFieldsAreFilled() {
        return !txtFirstName.getText().isEmpty() &&
                !txtLastName.getText().isEmpty() &&
                !txtUsername.getText().isEmpty() &&
                !txtPassword.getText().isEmpty() &&
                !txtPhone.getText().isEmpty() &&
                comboUserType.getValue() != null;
    }
}
