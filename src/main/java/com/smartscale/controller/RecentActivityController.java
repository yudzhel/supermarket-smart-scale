package com.smartscale.controller;

import com.smartscale.database.RecentActivityDAO;
import com.smartscale.model.RecentActivity;
import com.smartscale.util.Clock;
import com.smartscale.util.Switch;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecentActivityController implements Initializable {

    @FXML
    private Label lblTimeAndDate;
    @FXML
    private Label lblCurrentlyLoggedInText;

    @FXML
    private TableView<RecentActivity> tableRecentActivity;

    @FXML
    private TableColumn<RecentActivity, String> colActivity;

    @FXML
    private TableColumn<RecentActivity, String> colDate;

    ObservableList<RecentActivity> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Clock.initClock(lblTimeAndDate);
        lblCurrentlyLoggedInText.setText("Currently logged in: " + LogInController.getUsername());
        populateTable();
    }

    public void backButtonOnAction() throws IOException {
        Switch.switchTo("views/admin.fxml","Admin", lblTimeAndDate);
    }

    private void populateTable(){

        colDate.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        colActivity.setCellValueFactory(new PropertyValueFactory<RecentActivity, String>("message"));

        list = RecentActivityDAO.getData();
        tableRecentActivity.setItems(list);

    }
}
