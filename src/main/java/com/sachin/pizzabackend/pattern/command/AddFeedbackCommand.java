package com.sachin.pizzabackend.pattern.command;

import com.sachin.pizzabackend.model.Feedback;
import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.service.FeedbackService;

public class AddFeedbackCommand implements Command {
    private Order order;
    private String message;
    private int rating;
    private FeedbackService feedbackService;

    public AddFeedbackCommand(Order order, String message, int rating, FeedbackService feedbackService) {
        this.order = order;
        this.message = message;
        this.rating = rating;
        this.feedbackService = feedbackService;
    }

    @Override
    public void execute() {
        Feedback feedback = new Feedback(message, rating);  // Create feedback
        order.setFeedback(feedback);  // Set feedback in the order
    }
}
