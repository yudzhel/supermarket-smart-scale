package com.smartscale.model;

public class Product {
    private int productID;
    private double productPrice;
    private String productName, productCategory, imageURL;

    public Product(int productID, String productName, String productCategory, double productPrice, String imageURL) {
        this.productID = productID;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.imageURL = imageURL;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
