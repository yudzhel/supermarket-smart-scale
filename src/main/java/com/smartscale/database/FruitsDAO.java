package com.smartscale.database;

import com.smartscale.model.Fruit;
import com.smartscale.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FruitsDAO {

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
}
