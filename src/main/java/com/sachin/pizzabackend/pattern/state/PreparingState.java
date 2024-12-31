package com.sachin.pizzabackend.pattern.state;

import com.sachin.pizzabackend.model.Order;

public class PreparingState implements OrderState {

    @Override
    public void handlePaymentSuccess(Order order) {
        throw new IllegalStateException("Payment is already successful.");
    }

    @Override
    public void cancelOrder(Order order) {
        throw new IllegalStateException("Order cannot be cancelled while being prepared.");
    }

    @Override
    public void prepareOrder(Order order) {
        throw new IllegalStateException("Order is already being prepared.");
    }

    @Override
    public void deliverOrder(Order order) {
        order.setState(new OutForDeliveryState());
        order.setStatus(Order.OrderStatus.OUT_FOR_DELIVERY);
        System.out.println("Order is now out for delivery.");
    }
}
