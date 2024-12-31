package com.sachin.pizzabackend.pattern.command;

import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.service.UserService;

public class AddPointsCommand implements Command {
    private UserService userService;
    private String email;
    private int points;

    public AddPointsCommand(UserService userService, String email, int points) {
        this.userService = userService;
        this.email = email;
        this.points = points;
    }

    @Override
    public void execute() {
        // Logic addPoints method
        User user = userService.getUserByEmail(email);
        if (user != null) {
            user.setPoints(user.getPoints() + points);  // Adding points to user's account
            System.out.println("Added " + points + " points to user: " + email);
        } else {
            System.out.println("User not found for email: " + email);
        }
    }
}
