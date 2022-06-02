package com.smartscale.controller;

import com.smartscale.database.ProductDAO;
import com.smartscale.model.Vegetable;
import com.smartscale.util.Clock;
import com.smartscale.util.GetReceipt;
import com.smartscale.util.ShowMessage;
import com.smartscale.util.Switch;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class VegetablesController implements Initializable{
    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label labelTotal;
    @FXML
    private Label labelDollarKg;
    @FXML
    private Label labelPageNumber;
    @FXML
    private TextField txtKg;
    @FXML
    private GridPane gridVegetables;
    @FXML
    private Button btnDisplayMessage;
    @FXML
    private Button btnChosenProduct;
    @FXML
    private Button btnGetReceipt;
    @FXML
    private Button btnPreviousPage, btnNextPage;
    @FXML
    private AnchorPane anchorTop, anchorCategoryAndPages;
    @FXML
    private FlowPane flowPaneCalculate, flowPaneButtonsTop, flowPaneButtonsBottom;

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private boolean isDisabled = true;
    ObservableList<Vegetable> vegetables = ProductDAO.getVegetablesData();
    List<Vegetable> veggiesList = vegetables.stream().toList();

    private static int currentPage = 1;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        populateGridPane();
        ShowMessage.displayChooseProductMessage(btnDisplayMessage);
        activateGetReceipt();
        backgroundClicked();
    }

    public void buttonFruitsOnAction() throws IOException {
        Switch.switchTo("views/fruits.fxml", lblTimeAndDate);
    }

    public void buttonSignInOnAction() throws IOException {

        Switch.switchTo("views/login.fxml",lblTimeAndDate);
    }

    public void calculateTotal(){

        try {
            double kg = Double.parseDouble(txtKg.getText());
            double productPrice = Double.parseDouble(labelDollarKg.getText());
            double total = kg * productPrice;
            labelTotal.setText(String.valueOf(df.format(total)));

            activateGetReceipt();


        } catch (NumberFormatException nfe) {
            txtKg.setPromptText("0.00");
            labelTotal.setText("0.00");
        }

    }

    public void buttonGetReceiptOnAction() {
        GetReceipt.generate(btnChosenProduct,txtKg,labelDollarKg,labelTotal);
        clearFields();

    }

    public void activateGetReceipt() {

        labelTotal.textProperty().addListener((ov, t, t1) -> {
            String total = labelTotal.getText();

            if (!total.equals("0.00")) {
                ShowMessage.displayGetReceiptMessage(btnDisplayMessage);
                btnGetReceipt.setDisable(false);
                isDisabled = false;
                btnGetReceipt.setCursor(Cursor.HAND);
                btnGetReceipt.setStyle("""
                        -fx-background-color: white;
                        -fx-border-radius: 60px;
                        -fx-background-radius: 6;
                        -fx-effect: dropshadow(three-pass-box, grey, 10, 0, 2, 2);
                        -fx-text-fill: #0f852e""");
            }

            else{
                btnGetReceipt.setDisable(true);
                isDisabled = true;
                ShowMessage.displayEnterKgMessage(btnDisplayMessage);
            }
        });

        btnGetReceipt.setOnMouseEntered(e -> {

            if(!isDisabled) {
                btnGetReceipt.setStyle("""
                        -fx-background-color: #0f852e;
                        -fx-border-radius: 60px;
                        -fx-background-radius: 6;
                        -fx-effect: dropshadow(three-pass-box, grey, 10, 0, 2, 2);
                        -fx-text-fill: white""");
            }
        });

        btnGetReceipt.setOnMouseExited(e -> {

            if(!isDisabled) {
                btnGetReceipt.setStyle("""
                        -fx-background-color: white;
                        -fx-border-radius: 60px;
                        -fx-background-radius: 6;
                        -fx-effect: dropshadow(three-pass-box, grey, 10, 0, 2, 2);
                        -fx-text-fill: #0f852e""");
            }
        });
    }

    public List<List<Vegetable>> partitionListIntoSublists(){
        int partitionSize = 10;
        return Lists.partition(veggiesList, partitionSize);
    }

    public void nextButtonClicked(){
        currentPage++;
        populateGridPane();
    }

    public void previousButtonClicked(){
        currentPage--;
        populateGridPane();
    }

    public void populateGridPane() {

        gridVegetables.getChildren().clear();
        List<List<Vegetable>> currentPartition = partitionListIntoSublists();

        int column = 0;
        int row = 0;
        int size = currentPartition.size();

        String pagesText = "Page " + currentPage + " / " + size;
        labelPageNumber.setText(pagesText);

        checkPreviousAndNextButtons(currentPage, size);

        for (Vegetable vegetable : currentPartition.get(currentPage - 1)) {

            if (column == 5) {
                column = 0;
                row++;
            }

            Button button = new Button();
            button.setText(vegetable.getVegetableName());
            button.setPrefSize(200, 200);
            gridVegetables.add(button, column++, row);

            String name = vegetable.getVegetableName();
            Image vegetableImage;

            if(vegetable.getVegetableImageURL().isEmpty()){
                vegetableImage = new Image("D:\\git\\supermarket-smart-scale\\src\\main\\resources\\com\\smartscale\\images\\products\\no-image.jpg");
            }
            else {
                vegetableImage = new Image(vegetable.getVegetableImageURL());
            }
            addImageAndTextToButton(button, name, vegetableImage);

            button.setOnAction(actionEvent -> labelDollarKg.setText(vegetable.getVegetablePrice().toString()));

            button.setOnMouseEntered(e -> button.setCursor(Cursor.HAND));

            button.setOnMouseExited(e -> button.setCursor(Cursor.DEFAULT));

            button.setOnMouseClicked(e -> {

                txtKg.setDisable(false);
                txtKg.clear();
                labelTotal.setText("0.00");
                btnChosenProduct.setText(vegetable.getVegetableName());
                ShowMessage.displayEnterKgMessage(btnDisplayMessage);

            });
        }

    }

    public void backgroundClicked(){

        Pane[] currentPane = {anchorTop, anchorCategoryAndPages, flowPaneCalculate, flowPaneButtonsTop, flowPaneButtonsBottom,
                gridVegetables};

        for(Pane pane: currentPane){
            pane.setOnMouseClicked(e ->{
            clearFields();
            });
        }

    }

    private void checkPreviousAndNextButtons(int currentPage, int sizeOfPartitionedList){
        if((currentPage == 1 && sizeOfPartitionedList != 1) || (currentPage > 1 && sizeOfPartitionedList != currentPage)){
            btnNextPage.setDisable(false);
        }
        else {
            btnNextPage.setDisable(true);
        }

        if(currentPage > 1 && sizeOfPartitionedList != 1){
            btnPreviousPage.setDisable(false);
        }
        else {
            btnPreviousPage.setDisable(true);
        }
    }

    private void addImageAndTextToButton(Button button, String name, Image image){
        ImageView view = new ImageView(image);
        view.setFitHeight(98);
        view.setFitWidth(130);
        view.setSmooth(true);
        button.setGraphic(view);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setText(name);
        button.setAlignment(Pos.BOTTOM_CENTER);

        button.setStyle("-fx-font-weight: bold;" +
                "-fx-padding: 0 0 10 0;" );
    }

    private void clearFields(){
        txtKg.clear();
        txtKg.setDisable(true);
        labelDollarKg.setText("0.00");
        labelTotal.setText("0.00");
        btnChosenProduct.setText("");
        btnGetReceipt.setDisable(true);
        ShowMessage.displayChooseProductMessage(btnDisplayMessage);
    }
}