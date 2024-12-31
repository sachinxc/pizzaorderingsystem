package com.sachin.pizzabackend.pattern.strategy;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.Payment;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.utils.DiscountCalculator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public Payment processPayment(Order order, User user, double amount, boolean usePoints, String paymentType, String cardNumber, String walletKey, String expiryDate, String cvv) {
        // Validate card details
        validateCardDetails(cardNumber, expiryDate, cvv);

        // Apply discount logic using the DiscountCalculator
        double discount = DiscountCalculator.calculateDiscount(order, user, usePoints);
        double finalAmount = amount - discount;

        // Create payment object
        Payment payment = new Payment("PAY" + System.currentTimeMillis(), order, "card", paymentType, finalAmount, discount);
        payment.setPaymentStatus("Successful");
        order.setStatus(Order.OrderStatus.PLACED);

        return payment;
    }

    // Validate card number (16 digits), expiry date (MM/YY), and CVV (3 digits)
    private void validateCardDetails(String cardNumber, String expiryDate, String cvv) {
        if (cardNumber == null || cardNumber.isEmpty() || !cardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Invalid card number. It should be 16 digits.");
        }

        if (expiryDate == null || expiryDate.isEmpty() || !isValidExpiryDate(expiryDate)) {
            throw new IllegalArgumentException("Invalid expiry date. It should be in MM/YY format.");
        }

        if (cvv == null || cvv.isEmpty() || !cvv.matches("\\d{3}")) {
            throw new IllegalArgumentException("Invalid CVV. It should be 3 digits.");
        }
    }

    // Check if the expiry date is in MM/YY format
    private boolean isValidExpiryDate(String expiryDate) {
        Pattern pattern = Pattern.compile("^(0[1-9]|1[0-2])\\/([0-9]{2})$");
        Matcher matcher = pattern.matcher(expiryDate);
        return matcher.matches();
    }
}
