package com.sachin.pizzabackend.controller;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.service.FeedbackService;
import com.sachin.pizzabackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FeedbackService feedbackService;

    // Add feedback to an order
    @PostMapping("/{orderId}")
    public Order addFeedback(@PathVariable String orderId, @RequestParam String message, @RequestParam int rating) {
        Order order = orderService.getOrderById(orderId);
        return feedbackService.addFeedbackToOrder(order, message, rating);
    }
}

