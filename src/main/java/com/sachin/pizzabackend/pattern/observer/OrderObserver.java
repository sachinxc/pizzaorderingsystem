package com.sachin.pizzabackend.pattern.observer;

public interface OrderObserver {
    void updateOrderStatus(String orderId, String status);
}
