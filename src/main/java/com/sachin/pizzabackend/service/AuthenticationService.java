package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    private Set<String> loggedInUsers = new HashSet<>(); // To keep track of logged-in users

    public boolean loginUser(String identifier, String password) {
        // Find user by either email
        User user = userService.getUserByEmail(identifier);

        if (user != null && user.getPassword().equals(password)) {
            loggedInUsers.add(identifier); // Mark user as logged in
            System.out.println(loggedInUsers);
            return true; // Authentication successful
        }
        return false; // Authentication failed
    }

    public boolean logoutUser(String identifier) {
        return loggedInUsers.remove(identifier); // Remove user from logged-in set
    }

    public boolean isUserLoggedIn(String identifier) {
        return loggedInUsers.contains(identifier); // Check if user is logged in
    }
}