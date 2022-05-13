package com.smartscale.controller;

import com.smartscale.database.FruitsDAO;
import com.smartscale.model.Fruit;
import com.smartscale.util.Clock;
import com.smartscale.util.ShowMessage;
import com.smartscale.util.Switch;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class FruitsController implements Initializable{
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
    private GridPane gridFruits;
    @FXML
    private Button btnDisplayMessage;
    @FXML
    private Button btnChosenProduct;
    @FXML
    private Button btnGetReceipt;
    @FXML
    private AnchorPane anchorTop, anchorCategoryAndPages;
    @FXML
    private FlowPane flowPaneCalculate, flowPaneButtonsTop, flowPaneButtonsBottom;

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private boolean isDisabled = true;
    ObservableList<Fruit> fruits = FruitsDAO.getFruitsData();
    List<Fruit> fruitsList = fruits.stream().toList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        populateGridPane();
        ShowMessage.displayChooseProductMessage(btnDisplayMessage);
        activateGetReceipt();
        backgroundClicked();
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

    public void populateGridPane() {

        String pagesText = "Page 1 / " + 1;
        labelPageNumber.setText(pagesText);
        int column = 0;
        int row = 0;
        //int size = fruits.size();

        for (Fruit fruit : fruitsList) {

            if (column == 5) {
                column = 0;
                row++;
            }

            Button button = new Button();
            button.setText(fruit.getFruitName());
            button.setPrefSize(200, 200);
            gridFruits.add(button, column++, row);

            button.setOnAction(actionEvent -> labelDollarKg.setText(fruit.getFruitPrice().toString()));

            button.setOnMouseEntered(e -> button.setCursor(Cursor.HAND));

            button.setOnMouseExited(e -> button.setCursor(Cursor.DEFAULT));

            button.setOnMouseClicked(e -> {

                txtKg.setDisable(false);
                txtKg.clear();
                labelTotal.setText("0.00");
                btnChosenProduct.setText(fruit.getFruitName());
                ShowMessage.displayEnterKgMessage(btnDisplayMessage);

                });
                }

        }

    public void backgroundClicked(){

        Pane[] currentPane = {anchorTop, anchorCategoryAndPages, flowPaneCalculate, flowPaneButtonsTop, flowPaneButtonsBottom,
        gridFruits};

        for(Pane pane: currentPane){
            pane.setOnMouseClicked(e ->{
                txtKg.clear();
                txtKg.setDisable(true);
                btnChosenProduct.setText("");
                btnGetReceipt.setDisable(true);
            });
        }

    }



}