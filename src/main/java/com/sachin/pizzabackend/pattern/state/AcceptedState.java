package com.sachin.pizzabackend.pattern.state;

import com.sachin.pizzabackend.model.Order;

public class AcceptedState implements OrderState {

    @Override
    public void handlePaymentSuccess(Order order) {
        throw new IllegalStateException("Payment has already been processed.");
    }

    @Override
    public void cancelOrder(Order order) {
        order.setState(new CancelledState());
        order.setStatus(Order.OrderStatus.CANCELLED);
        System.out.println("Order has been cancelled after being accepted.");
    }

    @Override
    public void prepareOrder(Order order) {
        order.setState(new PreparingState());
        order.setStatus(Order.OrderStatus.PREPARING);
        System.out.println("Order is now being prepared.");
    }

    @Override
    public void deliverOrder(Order order) {
        throw new IllegalStateException("Order cannot be delivered without preparation.");
    }
}
