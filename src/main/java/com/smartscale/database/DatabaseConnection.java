package com.smartscale.database;

import com.smartscale.model.Employee;
import com.smartscale.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public static ObservableList<Employee> getEmployeesData(){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                employees.add(new Employee(Integer.parseInt(rs.getString("accountID")), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("role"), rs.getString("email"), rs.getString("phone"),
                        rs.getString("username"), rs.getString("password")));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return employees;
    }

    public static ObservableList<Product> getProductsData(){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<Product> products = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                products.add(new Product(rs.getInt("product_ID"), rs.getString("product_name"),
                        rs.getString("product_category"), rs.getDouble("product_price"), rs.getString("image_URL")));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return products;
    }
}
