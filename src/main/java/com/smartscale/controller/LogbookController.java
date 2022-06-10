package com.smartscale.controller;

import com.mysql.cj.log.Log;
import com.smartscale.database.LogbookDAO;
import com.smartscale.model.Employee;
import com.smartscale.model.Logbook;
import com.smartscale.util.Clock;
import com.smartscale.util.Switch;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogbookController implements Initializable {

    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblCurrentlyLoggedInText;

    @FXML
    private TableView<Logbook> tableLogbook;

    @FXML
    private TableColumn<Logbook, String> colAction;

    @FXML
    private TableColumn<Logbook, String> colDate;

    ObservableList<Logbook> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        lblCurrentlyLoggedInText.setText("Currently logged in: " + LogInController.getName());
        populateTable();
    }

    public void backButtonOnAction() throws IOException {
        Switch.switchTo("views/admin.fxml","Admin", lblTimeAndDate);
    }

    private void populateTable(){

        colDate.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        colAction.setCellValueFactory(new PropertyValueFactory<Logbook, String>("message"));

        list = LogbookDAO.getData();
        tableLogbook.setItems(list);

    }
}
