package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.pattern.command.AddFeedbackCommand;
import com.sachin.pizzabackend.pattern.command.FeedbackInvoker;
import com.sachin.pizzabackend.model.Order;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private FeedbackInvoker feedbackInvoker = new FeedbackInvoker(); // Invoker for feedback commands

    // Add feedback to an order using the Command pattern
    public Order addFeedbackToOrder(Order order, String message, int rating) {
        // Create a command object that knows how to add feedback
        AddFeedbackCommand command = new AddFeedbackCommand(order, message, rating, this);
        feedbackInvoker.setFeedbackCommand(command); // Set the command
        feedbackInvoker.executeCommand(); // Execute the command
        return order;
    }
}
