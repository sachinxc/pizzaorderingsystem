package com.sachin.pizzabackend.pattern.state;

import com.sachin.pizzabackend.model.Order;

public class DeliveredState implements OrderState {

    @Override
    public void handlePaymentSuccess(Order order) {
        throw new IllegalStateException("Payment is already successful.");
    }

    @Override
    public void cancelOrder(Order order) {
        throw new IllegalStateException("Order cannot be cancelled after delivery.");
    }

    @Override
    public void prepareOrder(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void deliverOrder(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }
}
