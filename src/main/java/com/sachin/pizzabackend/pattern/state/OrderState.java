package com.sachin.pizzabackend.pattern.state;

import com.sachin.pizzabackend.model.Order;

public interface OrderState {
    void handlePaymentSuccess(Order order);
    void cancelOrder(Order order);
    void prepareOrder(Order order);
    void deliverOrder(Order order);
}
