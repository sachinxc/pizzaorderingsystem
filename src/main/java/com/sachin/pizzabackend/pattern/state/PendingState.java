package com.sachin.pizzabackend.pattern.state;

import com.sachin.pizzabackend.model.Order;

public class PendingState implements OrderState {

    @Override
    public void handlePaymentSuccess(Order order) {
        order.setState(new PlacedState());
        order.setStatus(Order.OrderStatus.PLACED);
        System.out.println("Order status changed to PLACED after payment success.");
    }

    @Override
    public void cancelOrder(Order order) {
        order.setState(new CancelledState());
        order.setStatus(Order.OrderStatus.CANCELLED);
        System.out.println("Order has been cancelled.");
    }

    @Override
    public void prepareOrder(Order order) {
        throw new IllegalStateException("Order cannot be prepared in PENDING state.");
    }

    @Override
    public void deliverOrder(Order order) {
        throw new IllegalStateException("Order cannot be delivered in PENDING state.");
    }
}
