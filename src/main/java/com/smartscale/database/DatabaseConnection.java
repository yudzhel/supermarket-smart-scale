package com.smartscale.database;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getConnection(){
        String dbName = "supermarket";
        String dbUser = "root";
        String dbPassword = "admin";
        String url = "jdbc:mysql://localhost/" + dbName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,dbUser,dbPassword);


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
