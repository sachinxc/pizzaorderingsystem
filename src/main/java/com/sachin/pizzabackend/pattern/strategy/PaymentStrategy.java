package com.sachin.pizzabackend.pattern.strategy;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.Payment;
import com.sachin.pizzabackend.model.User;

public interface PaymentStrategy {
    // Updated method signature to include expiryDate and cvv
    Payment processPayment(Order order, User user, double amount, boolean usePoints, String paymentType, String cardNumber, String walletKey, String expiryDate, String cvv);
}
