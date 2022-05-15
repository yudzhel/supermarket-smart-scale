package com.smartscale.model;

public class Vegetable {
    private int vegetableID;
    private String vegetableName;
    private Double vegetablePrice;
    private String vegetableImageURL;

    public Vegetable(int vegetableID, String vegetableName, Double vegetablePrice, String vegetableImageURL) {
        this.vegetableID = vegetableID;
        this.vegetableName = vegetableName;
        this.vegetablePrice = vegetablePrice;
        this.vegetableImageURL = vegetableImageURL;
    }

    public int getVegetableID() {
        return vegetableID;
    }

    public void setVegetableID(int vegetableID) {
        this.vegetableID = vegetableID;
    }

    public String getVegetableName() {
        return vegetableName;
    }

    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    public Double getVegetablePrice() {
        return vegetablePrice;
    }

    public void setVegetablePrice(Double vegetablePrice) {
        this.vegetablePrice = vegetablePrice;
    }

    public String getVegetableImageURL() {
        return vegetableImageURL;
    }

    public void setVegetableImageURL(String vegetableImageURL) {
        this.vegetableImageURL = vegetableImageURL;
    }

    @Override
    public String toString() {
        return "Vegetable{" +
                "vegetableID=" + vegetableID +
                ", vegetableName='" + vegetableName + '\'' +
                ", vegetablePrice=" + vegetablePrice +
                ", vegetableImageURL='" + vegetableImageURL + '\'' +
                '}';
    }
}
