package com.sachin.pizzabackend.model;

public class Payment {
    private String paymentId;
    private String orderId;  // To store only the orderId
    private double finalAmount;
    private String paymentMethod;
    private String paymentType;
    private double discount;
    private String paymentStatus;

    public Payment(String paymentId, Order order, String paymentMethod, String paymentType, double finalAmount, double discount) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.paymentType = paymentType;
        this.finalAmount = finalAmount;
        this.discount = discount;
        this.paymentStatus = "Pending"; // Default status
        if (order != null) {
            this.orderId = order.getOrderId(); // Store only orderId
        }
    }

    // Getter and Setter for orderId
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // Other Getters and Setters for the required fields
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
