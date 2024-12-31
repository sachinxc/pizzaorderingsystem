package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.Payment;
import com.sachin.pizzabackend.model.Promotion;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.pattern.state.OrderState;
import com.sachin.pizzabackend.pattern.strategy.CashPaymentStrategy;
import com.sachin.pizzabackend.pattern.strategy.CardPaymentStrategy;
import com.sachin.pizzabackend.pattern.strategy.DigitalWalletPaymentStrategy;
import com.sachin.pizzabackend.pattern.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentStrategy cashPaymentStrategy = new CashPaymentStrategy();
    private final PaymentStrategy cardPaymentStrategy = new CardPaymentStrategy();
    private final PaymentStrategy digitalWalletPaymentStrategy = new DigitalWalletPaymentStrategy();

    @Autowired
    private PromotionService promotionService;

    public Payment processPayment(Order order, User user, String paymentMethod, String paymentType, double amount,
                                  boolean usePoints, String cardNumber, String walletKey, String expiryDate, String cvv) {
        PaymentStrategy strategy = choosePaymentStrategy(paymentMethod);
        Payment payment = strategy.processPayment(order, user, amount, usePoints, paymentType, cardNumber, walletKey, expiryDate, cvv);

        // Apply promotions after points discount has been applied
        applyPromotionsAfterDiscount(order, payment);

        // Add points to the user if payment is successful
        if ("Successful".equals(payment.getPaymentStatus())) {
            user.setPoints(user.getPoints() + order.getOrderPoints());
            updateOrderStateAfterPayment(order);
        }

        // Preserve only orderId in the payment object
        if (order != null) {
            payment.setOrderId(order.getOrderId());
            order.setPayment(payment);
        }

        return payment;
    }

    private PaymentStrategy choosePaymentStrategy(String paymentMethod) {
        switch (paymentMethod.toLowerCase()) {
            case "card":
                return cardPaymentStrategy;
            case "digital wallet":
                return digitalWalletPaymentStrategy;
            case "cash":
                return cashPaymentStrategy;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
    }

    private void applyPromotionsAfterDiscount(Order order, Payment payment) {
        List<Promotion> activePromotions = promotionService.getActivePromotions();

        double promotionDiscount = 0;
        for (Promotion promotion : activePromotions) {
            if (promotion.isActive()) {
                promotionDiscount = Math.max(promotionDiscount, payment.getFinalAmount() * promotion.getDiscount());
            }
        }

        double finalAmount = payment.getFinalAmount() - promotionDiscount;
        payment.setFinalAmount(finalAmount);
        payment.setDiscount(payment.getDiscount() + promotionDiscount);
    }

    private void updateOrderStateAfterPayment(Order order) {
        try {
            // pass on to the current state to handle the payment success logic
            OrderState currentState = order.getState();
            currentState.handlePaymentSuccess(order);
        } catch (IllegalStateException e) {
            // Handle exceptions from invalid state transitions, if necessary
            throw new IllegalStateException("Order state transition failed: " + e.getMessage(), e);
        }
    }
}
