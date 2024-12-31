package com.sachin.pizzabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sachin.pizzabackend.pattern.state.*;
import com.sachin.pizzabackend.pattern.observer.OrderObserver;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private User.UserSummary user;
    private List<PizzaItem> pizzaItems;  // List of PizzaItems (Pizza + quantity)
    private Delivery delivery;          // Delivery object to handle delivery logic
    @JsonIgnore
    private OrderState state;           // Current state (State Pattern)
    private OrderStatus status;         // Status for quick reference
    private double totalPrice;
    private long timestamp;             // Time of order placement
    private int orderPoints;            // Points based on total price
    private Payment payment;
    private Feedback feedback;          // Feedback object that holds message and rating

    // Observer-related fields
    private List<OrderObserver> observers = new ArrayList<>();

    // Constructor
    public Order(String orderId, User user, List<PizzaItem> pizzaItems, Delivery delivery) {
        this.orderId = orderId;
        this.user = user.toUserSummary();  // Convert the User to UserSummary
        this.pizzaItems = pizzaItems;
        this.delivery = delivery;
        this.payment = null;               // Default payment status
        this.status = OrderStatus.PENDING; // Default status as PENDING
        this.state = new PendingState();   // Initial state
        this.timestamp = System.currentTimeMillis(); // Current time
        this.totalPrice = calculateTotalPrice();     // Calculate total price
        this.orderPoints = calculatePoints();        // Calculate points
        this.feedback = null;                        // Default to no feedback
    }

    // Delegate methods for state transitions
    public void handlePaymentSuccess() {
        state.handlePaymentSuccess(this);
    }

    public void cancelOrder() {
        state.cancelOrder(this);
    }

    public void prepareOrder() {
        state.prepareOrder(this);
    }

    public void deliverOrder() {
        state.deliverOrder(this);
    }

    // Calculate the total price of the order
    public double calculateTotalPrice() {
        double total = 0;
        for (PizzaItem pizzaItem : pizzaItems) {
            total += pizzaItem.getPizza().getPrice() * pizzaItem.getQuantity();
        }
        return total;
    }

    // Calculate points based on the total price
    public int calculatePoints() {
        if (totalPrice <= 500) return 5;          // 0 - 500
        else if (totalPrice > 500 && totalPrice <= 1000) return 10; // 501 - 1000
        else if (totalPrice > 1000 && totalPrice <= 2000) return 20; // 1001 - 2000
        else if (totalPrice > 2000 && totalPrice <= 5000) return 30; // 2001 - 5000
        else return 50;                            // 5001 and above
    }

    // State management
    public void setState(OrderState state) {
        this.state = state;
        notifyObservers(); // Notify observers when state changes
    }

    public OrderState getState() {
        return state;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        notifyObservers(); // Notify observers when status changes
    }

    public OrderStatus getStatus() {
        return status;
    }

    // Observer methods
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.updateOrderStatus(orderId, status.toString());
        }
    }

    // Getter and setter for feedback
    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    // Other getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public User.UserSummary getUser() {
        return user;
    }

    public void setUser(User.UserSummary user) {
        this.user = user;
    }

    public List<PizzaItem> getPizzaItems() {
        return pizzaItems;
    }

    public void setPizzaItems(List<PizzaItem> pizzaItems) {
        this.pizzaItems = pizzaItems;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getOrderPoints() {
        return orderPoints;
    }

    public void setOrderPoints(int orderPoints) {
        this.orderPoints = orderPoints;
    }

    // Enum for order statuses (State Pattern)
    public enum OrderStatus {
        PENDING,        // Order has been placed but not yet accepted
        PLACED,         // Order has been placed
        ACCEPTED,       // Order has been accepted by the restaurant
        CANCELLED,      // Order was cancelled by user or restaurant
        PREPARING,      // Order is being prepared
        OUT_FOR_DELIVERY, // Order is on its way to the customer
        DELIVERED       // Order has been delivered
    }
}
