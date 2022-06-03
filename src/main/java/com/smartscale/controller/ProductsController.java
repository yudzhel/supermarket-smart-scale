package com.smartscale.controller;

import com.smartscale.database.DatabaseConnection;
import com.smartscale.database.LogbookDAO;
import com.smartscale.database.ProductDAO;
import com.smartscale.model.Product;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.sql.*;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblCurrentlyLoggedInText;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TableColumn<Product, Integer> colProductID;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, String> colImageURL;

    @FXML
    private ComboBox<String> comboCategory;



    @FXML
    private TextField txtImageURL;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductID;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtSearchBar;


    ObservableList<Product> products;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        lblCurrentlyLoggedInText.setText("Currently logged in: " + LogInController.getName());
        populateComboBox();
        populateTable();
        searchBar();
    }

    public void backButtonOnAction() throws IOException {

        if(LogInController.getRole().equals("administrator")){
            Switch.switchTo("views/admin.fxml", lblTimeAndDate);
        }
        else {
            Switch.switchTo("views/employee.fxml", lblTimeAndDate);
        }

    }

    public void addProduct(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement ps;

        String query = "INSERT INTO products (product_name, product_category, product_price, image_URL) " +
                "VALUES (?,?,?,?)";

        if(areRequiredFieldsAreFilled()){
            try{

                String productName = txtProductName.getText().trim();

                ps = connection.prepareStatement(query);
                ps.setString(1, txtProductName.getText().trim());
                ps.setString(2, comboCategory.getValue().trim());
                ps.setString(3, txtPrice.getText().trim());
                ps.setString(4, txtImageURL.getText().trim());
                ps.execute();

                populateTable();
                clearTextFields();
                searchBar();
                ShowMessage.displayInformationDialog("Product " + productName + " was added successfully!");
                LogbookDAO.add(LogInController.getName() + " added a new product (" + productName + ")");
            }
            catch (Exception e){
                ShowMessage.displayErrorDialog(e.getMessage());
            }
        }
        else {
            ShowMessage.displayErrorDialog("Please fill all necessary fields (Product Name, Category and Price!)");
        }
    }

    public void updateProduct() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String query = "UPDATE products SET product_name = '" + txtProductName.getText().trim() +
                "', product_category = '" + comboCategory.getSelectionModel().getSelectedItem() +
                "', product_price = " + txtPrice.getText().trim() +
                ", image_URL = '" + txtImageURL.getText().trim() +
                 "' WHERE product_ID = " + txtProductID.getText().trim();

        if(!txtProductID.getText().isEmpty()){
            try{

                String id = txtProductID.getText();
                statement.execute(query);
                populateTable();
                clearTextFields();
                searchBar();
                ShowMessage.displayInformationDialog("Update was successful! " + "(Product ID: " + id + ")");
                LogbookDAO.add(LogInController.getName() + " updated a product (ID: " + id + ")");

            } catch (Exception e){
                ShowMessage.displayErrorDialog(e.getMessage());
            }
        }
        else {
            ShowMessage.displayErrorDialog("Please choose a product to update!");
        }

    }

    public void deleteProduct() throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String query = "DELETE FROM products WHERE product_ID = " + txtProductID.getText();

        if(!txtProductID.getText().isEmpty()){
            try{

                String id = txtProductID.getText();
                statement.execute(query);
                populateTable();
                clearTextFields();
                searchBar();
                ShowMessage.displayInformationDialog("Product (" + id + ") was deleted!");
                LogbookDAO.add(LogInController.getName() + " deleted a product (" + id + ")");

            } catch (Exception e){
                ShowMessage.displayErrorDialog(e.getMessage());
            }
        }
        else {
            ShowMessage.displayErrorDialog("Please choose a product to delete!");
        }

    }

    private void populateTable(){

        colProductID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("productCategory"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));
        colImageURL.setCellValueFactory(new PropertyValueFactory<Product, String>("imageURL"));


        products = ProductDAO.getProductsData();
        tableProducts.setItems(products);
    }

    public void clearTextFields(){

        txtProductID.setText("");
        txtProductName.setText("");
        txtPrice.setText("");
        txtImageURL.setText("");
        comboCategory.getSelectionModel().clearSelection();

    }

    public void tableToTextFields(){
        Product product = tableProducts.getSelectionModel().getSelectedItem();

        try {
            txtProductID.setText(String.valueOf(product.getProductID()));
            txtProductName.setText(product.getProductName());
            txtPrice.setText(String.valueOf(product.getProductPrice()));
            txtImageURL.setText(product.getImageURL());
            comboCategory.getSelectionModel().select(product.getProductCategory());
        } catch (Exception ignored){

        }

    }

    public void populateComboBox(){
        comboCategory.getItems().addAll(
                "fruit", "vegetable");

        comboCategory.setPromptText("Please select one");
    }

    public void searchBar(){
        FilteredList<Product> filteredProducts = new FilteredList<>(products, b -> true);
        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate(product -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return  true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(product.getProductName().toLowerCase().contains(searchKeyword)){
                    return true; // match found
                }
                else return false;
            });
        });

        SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);
        sortedProducts.comparatorProperty().bind(tableProducts.comparatorProperty());
        tableProducts.setItems(sortedProducts);
    }

    private boolean areRequiredFieldsAreFilled() {
        return !txtProductName.getText().isEmpty() &&
                !txtPrice.getText().isEmpty() &&
                comboCategory.getValue() != null;
    }

    public void getImageURLPath() {

        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose image...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png")
            );


            File file = fileChooser.showOpenDialog(lblTimeAndDate.getScene().getWindow());
            URI text = Path.of(file.getPath()).toUri();

            txtImageURL.setText(text.toString());

        } catch(NullPointerException npe){
            txtImageURL.clear();
        }

    }
}
