package com.smartscale.database;

import com.smartscale.model.RecentActivity;
import com.smartscale.util.ShowMessage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;

public class RecentActivityDAO {

    public static void add(String activityMessage) throws SQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String query = "INSERT INTO recent_activity (activity_message) VALUES (\"" + activityMessage + "\")";

        try {
            statement.execute(query);

        } catch (Exception e){
            ShowMessage.displayErrorDialog(e.getMessage());
        }
    }

    public static ObservableList<RecentActivity> getData(){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<RecentActivity> recentActivityData = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT activity_datetime, activity_message " +
                    "FROM recent_activity " +
                    "ORDER BY activity_id DESC " +
                    "LIMIT 200");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Timestamp timestamp = rs.getTimestamp("activity_datetime");
                String s = new SimpleDateFormat("MMM d, yyyy 'at' hh:mm:ss aaa").format(timestamp);
                SimpleStringProperty datetime = new SimpleStringProperty(s);

                recentActivityData.add(new RecentActivity(datetime, rs.getString("activity_message")));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return recentActivityData;
    }
}
