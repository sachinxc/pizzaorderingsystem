package com.sachin.pizzabackend.pattern.state;

import com.sachin.pizzabackend.model.Order;

public class CancelledState implements OrderState {

    @Override
    public void handlePaymentSuccess(Order order) {
        throw new IllegalStateException("Payment cannot be processed for a cancelled order.");
    }

    @Override
    public void cancelOrder(Order order) {
        throw new IllegalStateException("Order is already cancelled.");
    }

    @Override
    public void prepareOrder(Order order) {
        throw new IllegalStateException("Cancelled orders cannot be prepared.");
    }

    @Override
    public void deliverOrder(Order order) {
        throw new IllegalStateException("Cancelled orders cannot be delivered.");
    }
}
