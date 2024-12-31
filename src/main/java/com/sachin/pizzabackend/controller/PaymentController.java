package com.sachin.pizzabackend.controller;

import com.sachin.pizzabackend.model.Payment;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.service.PaymentService;
import com.sachin.pizzabackend.service.OrderService;
import com.sachin.pizzabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Get available card types
    @GetMapping("/cardTypes")
    public List<String> getCardTypes() {
        return List.of("Visa", "MasterCard");
    }

    // Get available digital wallet types
    @GetMapping("/digitalWalletTypes")
    public List<String> getDigitalWalletTypes() {
        return List.of("XCrypto", "ShareCash");
    }

    // Process payment
    @PostMapping("/pay")
    public Payment processPayment(
            @RequestParam String orderId,
            @RequestParam String email,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String paymentType,
            @RequestParam(required = false) boolean usePoints,
            @RequestParam(required = false) String cardNumber,
            @RequestParam(required = false) String expiryDate,
            @RequestParam(required = false) String cvv,
            @RequestParam(required = false) String walletKey
    ) {
        User user = userService.getUserByEmail(email);
        Order order = orderService.getOrderById(orderId);
        return paymentService.processPayment(order, user, paymentMethod, paymentType, order.getTotalPrice(), usePoints, cardNumber, walletKey, expiryDate, cvv);
    }
}
