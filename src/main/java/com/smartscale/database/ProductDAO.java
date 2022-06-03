package com.smartscale.database;

import com.smartscale.model.Fruit;
import com.smartscale.model.Product;
import com.smartscale.model.Vegetable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO {

    public static ObservableList<Fruit> getFruitsData(){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<Fruit> fruits = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE product_category = 'fruit'");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                fruits.add(new Fruit(rs.getInt("product_ID"), rs.getString("product_name"),
                        rs.getDouble("product_price"), rs.getString("image_URL")));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return fruits;
    }

    public static ObservableList<Vegetable> getVegetablesData(){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        ObservableList<Vegetable> vegetables = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE product_category = 'vegetable'");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                vegetables.add(new Vegetable(rs.getInt("product_ID"), rs.getString("product_name"),
                        rs.getDouble("product_price"), rs.getString("image_URL")));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return vegetables;
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
