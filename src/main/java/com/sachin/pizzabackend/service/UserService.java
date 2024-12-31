package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.pattern.command.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    // In-memory storage for users
    private static final Map<String, User> users = new HashMap<>();

    private UserCommandInvoker invoker = new UserCommandInvoker();

    // Register a new user
    public String registerUser(String name, String email, String phoneNumber, String password) {
        if (users.containsKey(email)) {
            return "User with this email already exists."; // Return a message if email is taken
        }
        User user = new User(name, email, phoneNumber, password, new ArrayList<>(), 0);
        users.put(email, user);
        return "User registered successfully with email: " + email; // Return a success message
    }


    // Get user by email
    public User getUserByEmail(String email) {
        return users.get(email);
    }

    // Add pizza to favorites using Command Pattern
    public void addPizzaToFavorites(String email, Pizza pizza) {
        Command addPizzaCommand = new AddPizzaToFavoritesCommand(this, email, pizza);
        invoker.executeCommand(addPizzaCommand);
    }

    // Remove pizza from favorites using Command Pattern
    public void removePizzaFromFavorites(String email, Pizza pizza) {
        Command removePizzaCommand = new RemovePizzaFromFavoritesCommand(this, email, pizza);
        invoker.executeCommand(removePizzaCommand);
    }

    // Add points using Command Pattern
    public void addPoints(String email, int points) {
        Command addPointsCommand = new AddPointsCommand(this, email, points);
        invoker.executeCommand(addPointsCommand);
    }

    // Get all users (For testing purposes)
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
