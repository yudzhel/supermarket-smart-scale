package com.smartscale.util;

import com.smartscale.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Switch {

    // Switches to the given view, label is used to access the current stage

    public static void switchTo(String fxmlFileName, String title, Label label) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
        Stage stage = (Stage) label.getScene().getWindow();
        stage.setTitle(title);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().addAll(Objects.requireNonNull(Main.class.getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
