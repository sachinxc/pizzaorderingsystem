package com.sachin.pizzabackend.pattern.command;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.service.UserService;

public class AddPizzaToFavoritesCommand implements Command {
    private UserService userService;
    private String email;
    private Pizza pizza;

    public AddPizzaToFavoritesCommand(UserService userService, String email, Pizza pizza) {
        this.userService = userService;
        this.email = email;
        this.pizza = pizza;
    }

    @Override
    public void execute() {
        // Logic addPizzaToFavorites method
        User user = userService.getUserByEmail(email);
        if (user != null) {
            user.addFavorite(pizza);  // Adding pizza to favorites
            System.out.println("Added pizza to favorites for user: " + email);
        } else {
            System.out.println("User not found for email: " + email);
        }
    }
}
