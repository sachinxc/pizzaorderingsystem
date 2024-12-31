package com.sachin.pizzabackend.pattern.strategy;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.Payment;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.utils.DiscountCalculator;

public class DigitalWalletPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment processPayment(Order order, User user, double amount, boolean usePoints, String paymentType, String cardNumber, String walletKey, String expiryDate, String cvv) {
        // Validate wallet key
        if (walletKey == null || walletKey.isEmpty()) {
            throw new IllegalArgumentException("Invalid wallet key.");
        }

        // Apply discount logic using the DiscountCalculator
        double discount = DiscountCalculator.calculateDiscount(order, user, usePoints);
        double finalAmount = amount - discount;

        // Create the payment object and set its status
        Payment payment = new Payment("PAY" + System.currentTimeMillis(), order, "digital wallet", paymentType, finalAmount, discount);
        payment.setPaymentStatus("Successful");
        order.setStatus(Order.OrderStatus.PLACED);

        return payment;
    }
}
