package com.smartscale.model;

public class Fruit {
    private int fruitID;
    private String fruitName;
    private Double fruitPrice;
    private String fruitImageURL;

    public Fruit(int fruitID, String fruitName, Double fruitPrice, String fruitImageURL) {
        this.fruitID = fruitID;
        this.fruitName = fruitName;
        this.fruitPrice = fruitPrice;
        this.fruitImageURL = fruitImageURL;
    }

    public int getFruitID() {
        return fruitID;
    }

    public void setFruitID(int fruitID) {
        this.fruitID = fruitID;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public Double getFruitPrice() {
        return fruitPrice;
    }

    public void setFruitPrice(Double fruitPrice) {
        this.fruitPrice = fruitPrice;
    }

    public String getFruitImageURL() {
        return fruitImageURL;
    }

    public void setFruitImageURL(String fruitImageURL) {
        this.fruitImageURL = fruitImageURL;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "fruitID=" + fruitID +
                ", fruitName='" + fruitName + '\'' +
                ", fruitPrice=" + fruitPrice +
                ", fruitImageURL='" + fruitImageURL + '\'' +
                '}';
    }
}
