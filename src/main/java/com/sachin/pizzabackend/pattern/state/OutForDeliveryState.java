package com.sachin.pizzabackend.pattern.state;

import com.sachin.pizzabackend.model.Order;

public class OutForDeliveryState implements OrderState {

    @Override
    public void handlePaymentSuccess(Order order) {
        throw new IllegalStateException("Payment is already successful.");
    }

    @Override
    public void cancelOrder(Order order) {
        throw new IllegalStateException("Order cannot be cancelled while out for delivery.");
    }

    @Override
    public void prepareOrder(Order order) {
        throw new IllegalStateException("Order is already prepared.");
    }

    @Override
    public void deliverOrder(Order order) {
        order.setState(new DeliveredState());
        order.setStatus(Order.OrderStatus.DELIVERED);
        System.out.println("Order has been delivered.");
    }
}
