package com.sachin.pizzabackend.controller;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.service.PizzaService;
import com.sachin.pizzabackend.service.UserService;
import com.sachin.pizzabackend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PizzaService pizzaService;

    // Register a new user
    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String phoneNumber,
                               @RequestParam String password) {
        return userService.registerUser(name, email, phoneNumber, password);
    }

    // User Login
    @PostMapping("/login")
    public String loginUser(@RequestParam String identifier, @RequestParam String password) {
        boolean isAuthenticated = authenticationService.loginUser(identifier, password);

        if (isAuthenticated) {
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }

    // User Logout
    @PostMapping("/logout")
    public String logoutUser(@RequestParam String identifier) {
        boolean isLoggedOut = authenticationService.logoutUser(identifier);

        if (isLoggedOut) {
            return "Logout successful!";
        } else {
            return "User is not logged in!";
        }
    }

    // is User Logged in
    @PostMapping("/logged")
    public boolean isUserLoggedIn(@RequestParam String identifier) {

        return authenticationService.isUserLoggedIn(identifier);
    }

    // Get user details by email
    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // Get user favorites by email
    @GetMapping("/{email}/favorites")
    public List<Pizza> getUserFavorites(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null ? user.getFavorites() : null;
    }

    @PostMapping("/{email}/favorites")
    public void addPizzaToFavorites(@PathVariable String email, @RequestBody Pizza pizza) {
        // Log the type of pizza for debugging purposes
        System.out.println("Received pizza type: " + pizza.getType());

        // Check if the pizza is a default or custom pizza
        if ("Custom".equals(pizza.getType())) {
            // For custom pizzas, create the pizza using the custom pizza method
            Pizza savedPizza = pizzaService.createCustomPizza(
                    pizza.getName(),
                    pizza.getCrust(),
                    pizza.getSauce(),
                    pizza.getToppings(),
                    pizza.getCheese(),
                    pizza.isExtraCheese(),
                    pizza.getSize()
            );
            userService.addPizzaToFavorites(email, savedPizza);
        } else if ("Default".equals(pizza.getType())) {
            // For default pizzas, directly add it to favorites without customization
            userService.addPizzaToFavorites(email, pizza);
        } else {
            // Log an error and throw exception if type is invalid
            System.out.println("Invalid pizza type received: " + pizza.getType());
            throw new IllegalArgumentException("Pizza type must be 'Default' or 'Custom'");
        }
    }



    // Remove pizza from favorites
    @DeleteMapping("/{email}/favorites")
    public void removePizzaFromFavorites(@PathVariable String email, @RequestBody Pizza pizza) {
        userService.removePizzaFromFavorites(email, pizza);
    }

    // Add loyalty points to user
    @PostMapping("/{email}/addPoints")
    public void addPoints(@PathVariable String email, @RequestParam int points) {
        userService.addPoints(email, points);
    }

    // Get all users (for testing purposes)
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
