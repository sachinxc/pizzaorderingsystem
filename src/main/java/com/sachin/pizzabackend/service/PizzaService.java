package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.data.PizzaData;
import com.sachin.pizzabackend.pattern.decorator.*;
import com.sachin.pizzabackend.pattern.handlers.*;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PizzaService {

    // Predefined pizzas from constants class
    private static final Map<String, Pizza> DEFAULT_PIZZAS = PizzaData.DEFAULT_PIZZAS;

    // Size options
    private static final List<String> SIZE_OPTIONS = PizzaData.SIZE_OPTIONS;

    // Price maps for customization options (for custom pizzas only)
    private static final Map<String, Double> CRUST_PRICES = PizzaData.CRUST_PRICES;
    private static final Map<String, Double> SAUCE_PRICES = PizzaData.SAUCE_PRICES;
    private static final Map<String, Double> TOPPING_PRICES = PizzaData.TOPPING_PRICES;
    private static final Map<String, Double> CHEESE_PRICES = PizzaData.CHEESE_PRICES;
    private static final double EXTRA_CHEESE_PRICE = PizzaData.EXTRA_CHEESE_PRICE;

    // Handlers for crust, sauce, toppings
    private PizzaHandler handlerChain;

    public PizzaService() {
        // Set up the chain of responsibility for handling pizza customizations
        PizzaHandler crustHandler = new CrustHandler();
        PizzaHandler sauceHandler = new SauceHandler();
        PizzaHandler cheeseHandler = new CheeseHandler();
        PizzaHandler toppingHandler = new ToppingHandler();

        crustHandler.setNext(sauceHandler);
        sauceHandler.setNext(toppingHandler);
        toppingHandler.setNext(cheeseHandler);

        handlerChain = crustHandler;
    }

    // Method to get predefined pizzas
    public List<Pizza> getDefaultPizzas() {
        return new ArrayList<>(DEFAULT_PIZZAS.values());
    }

    // Method to get a pizza by name only
    public Pizza getPizzaByName(String pizzaName) {
        Pizza pizza = DEFAULT_PIZZAS.get(pizzaName);
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza not found: " + pizzaName);
        }
        return pizza;
    }

    // Method to get a pizza by name with dynamic size handling and extra cheese option
    public Pizza getPizzaByNameAndSize(String pizzaName, String size, boolean extraCheese) {
        if (!SIZE_OPTIONS.contains(size)) {
            throw new IllegalArgumentException("Invalid size: " + size);
        }

        Pizza pizza = DEFAULT_PIZZAS.get(pizzaName);
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza not found: " + pizzaName);
        }

        // Use handlers to process pizza customization
        pizza = handlerChain.handle(pizza, size, extraCheese);

        // Calculate final price based on the customizations and size
        double finalPrice = calculatePrice(pizza.getPrice(), size, extraCheese);

        // Apply extra cheese decorator if needed
        if (extraCheese) {
            pizza = new ExtraCheeseDecorator(pizza);
        }

        // Return the pizza with the final calculated price
        return new Pizza.PizzaBuilder(pizza.getName(), pizza.getType())
                .crust(pizza.getCrust())
                .sauce(pizza.getSauce())
                .toppings(pizza.getToppings())
                .cheese(pizza.getCheese())
                .extraCheese(pizza.isExtraCheese())
                .size(size)
                .price(finalPrice)
                .rating(pizza.getRating()) // Keep the same rating
                .build();
    }

    // Method to create a custom pizza with user-defined options
    public Pizza createCustomPizza(String name, String crust, String sauce, List<String> toppings, String cheese, boolean extraCheese, String size) {
        Pizza pizza = new Pizza.PizzaBuilder(name, "Custom")
                .crust(crust)
                .sauce(sauce)
                .toppings(toppings)
                .cheese(cheese)
                .size(size)
                .price(0.0) // We will calculate the price using handlers
                .build();

        // Use handlers to process customization
        pizza = handlerChain.handle(pizza, size, extraCheese);

        // Calculate final price based on the customizations and size
        double finalPrice = calculatePrice(pizza.getPrice(), size, extraCheese);

        // Apply extra cheese decorator if needed
        if (extraCheese) {
            pizza = new ExtraCheeseDecorator(pizza);
        }

        // Return the pizza with the final calculated price
        return new Pizza.PizzaBuilder(pizza.getName(), pizza.getType())
                .crust(pizza.getCrust())
                .sauce(pizza.getSauce())
                .cheese(pizza.getCheese())
                .toppings(pizza.getToppings())
                .extraCheese(pizza.isExtraCheese())
                .size(size)
                .price(finalPrice)
                .rating(pizza.getRating()) // Keep the same rating
                .build();
    }

    // price calculation logic based on size and extra cheese
    private double calculatePrice(double basePrice, String size, boolean extraCheese) {
        double sizePriceMultiplier = switch (size) {
            case "Regular" -> 2.0;
            case "Large" -> 4.0;
            default -> 1.0; // Small is default
        };
        double extraCheesePrice = extraCheese ? EXTRA_CHEESE_PRICE : 0.0;
        return (basePrice * sizePriceMultiplier) + extraCheesePrice;
    }

    // Method to get available crust options
    public Map<String, Double> getCrustOptions() {
        return CRUST_PRICES;
    }

    // Method to get available sauce options
    public Map<String, Double> getSauceOptions() {
        return SAUCE_PRICES;
    }

    // Method to get available topping options
    public Map<String, Double> getToppingOptions() {
        return TOPPING_PRICES;
    }

    // Method to get available cheese options
    public Map<String, Double> getCheeseOptions() {
        return CHEESE_PRICES;
    }

    // Method to get available size options
    public List<String> getSizeOptions() {
        return SIZE_OPTIONS;
    }
}
