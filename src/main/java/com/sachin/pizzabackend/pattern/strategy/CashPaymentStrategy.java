package com.sachin.pizzabackend.pattern.strategy;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.Payment;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.utils.DiscountCalculator;

public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment processPayment(Order order, User user, double amount, boolean usePoints, String paymentType, String cardNumber, String walletKey, String expiryDate, String cvv) {
        // No additional validation for cash payments
        // Apply discount logic using the DiscountCalculator
        double discount = DiscountCalculator.calculateDiscount(order, user, usePoints);
        double finalAmount = amount - discount;

        Payment payment = new Payment("PAY" + System.currentTimeMillis(), order, "cash", null, finalAmount, discount);
        payment.setPaymentStatus("Successful");
        order.setStatus(Order.OrderStatus.PLACED);

        return payment;
    }
}
