package com.sachin.pizzabackend.model;

public class Promotion {
    private String promotionId;
    private String promotionName;
    private double discount; // Discount is set as a percentage (e.g. 0.10 for 10%)
    private boolean isActive;

    public Promotion(String promotionId, String promotionName, double discount, boolean isActive) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.discount = discount;
        this.isActive = isActive;
    }

    // Getters and setters
    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
