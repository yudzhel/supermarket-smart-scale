package com.smartscale.database;

import com.smartscale.model.Employee;
import com.smartscale.model.Logbook;
import com.smartscale.util.MessageDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;

public class LogbookDAO {

    public static void add(String logMessage) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String query = "INSERT INTO logbook (log_message) VALUES (\"" + logMessage + "\")";

        try {
            statement.execute(query);

        } catch (Exception e){
            MessageDialog.displayError(e.getMessage());
        }
    }

    public static ObservableList<Logbook> getData(){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<Logbook> logbookData = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT log_datetime, log_message FROM logbook");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Timestamp timestamp = rs.getTimestamp("log_datetime");
                String s = new SimpleDateFormat("MMM d, yyyy 'at' hh:mm:ss aaa").format(timestamp);
                SimpleStringProperty var = new SimpleStringProperty(s);

                logbookData.add(new Logbook(var, rs.getString("log_message")));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return logbookData;
    }
}
