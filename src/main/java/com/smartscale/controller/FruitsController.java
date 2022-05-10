package com.smartscale.controller;

import com.smartscale.database.FruitsDAO;
import com.smartscale.model.Fruit;
import com.smartscale.model.Product;
import com.smartscale.util.Clock;
import com.smartscale.util.Switch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

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

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private boolean isClicked = false;
    ObservableList<Fruit> fruits = FruitsDAO.getFruitsData();
    List<Fruit> fruitsList = fruits.stream().toList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        populateGridPane();
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

        } catch (NumberFormatException nfe) {
            txtKg.setPromptText("0.00");
            labelTotal.setText("0.00");
        }

    }

    public void populateGridPane() {

        String pagesText = "Page 1 / " + 1;
        labelPageNumber.setText(pagesText);
        int column = 0;
        int row = 0;
        int size = fruits.size();

        for (Fruit fruit : fruitsList) {

            if (column == 5) {
                column = 0;
                row++;
            }

            Button button = new Button();
            gridFruits.add(button, column++, row);
            button.setText(fruit.getFruitName());
            button.setPrefSize(200, 200);
            buttonStyle(button);

            button.setOnAction(actionEvent -> {
                labelDollarKg.setText(fruit.getFruitPrice().toString());
            });

            button.setOnMouseEntered(e -> {
                button.setStyle("""
                        -fx-border-color: #0f852e;
                        -fx-border-width: 5px;
                        """);

                button.setCursor(Cursor.HAND);
            });

            button.setOnMouseExited(e -> {
                if(isClicked){
                    button.setStyle("""
                        -fx-border-color: #0f852e;
                        -fx-border-width: 5px;
                        """);

                    isClicked = false;
                }
                else {
                    buttonStyle(button);
                }
            });

            button.setOnMouseClicked(e -> {
                isClicked = true;
            });

        }
    }


    public void buttonStyle(Button button){
        button.setStyle("""
                     -fx-background-radius: 10px;
                     -fx-background-color: white;
                     -fx-border-radius: 60px;
                     -fx-background-radius: 6;
                     -fx-effect: dropshadow(three-pass-box, grey, 10, 0, 2, 2);
                    """);
    }



}