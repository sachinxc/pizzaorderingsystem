package com.sachin.pizzabackend.pattern.command;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.service.UserService;

public class RemovePizzaFromFavoritesCommand implements Command {
    private UserService userService;
    private String email;
    private Pizza pizza;

    public RemovePizzaFromFavoritesCommand(UserService userService, String email, Pizza pizza) {
        this.userService = userService;
        this.email = email;
        this.pizza = pizza;
    }

    @Override
    public void execute() {
        // Logic removePizzaFromFavorites method
        User user = userService.getUserByEmail(email);
        if (user != null) {
            user.removeFavorite(pizza);  // Removing pizza from favorites
            System.out.println("Removed pizza from favorites for user: " + email);
        } else {
            System.out.println("User not found for email: " + email);
        }
    }
}
