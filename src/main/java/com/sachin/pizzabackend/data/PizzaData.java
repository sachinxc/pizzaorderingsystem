package com.sachin.pizzabackend.data;

import com.sachin.pizzabackend.model.Pizza;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PizzaData {

    // Predefined pizzas
    public static final Map<String, Pizza> DEFAULT_PIZZAS = new HashMap<>();

    static {
        DEFAULT_PIZZAS.put("Chicken Pizza", new Pizza.PizzaBuilder("Chicken Pizza", "Default")
                .size("Small").price(1000).rating(4.5).extraCheese(false).build());
        DEFAULT_PIZZAS.put("Cheese Pizza", new Pizza.PizzaBuilder("Cheese Pizza", "Default")
                .size("Small").price(500).rating(4.0).extraCheese(false).build());
        DEFAULT_PIZZAS.put("Veggie Pizza", new Pizza.PizzaBuilder("Veggie Pizza", "Default")
                .size("Small").price(200).rating(3.5).extraCheese(false).build());
    }

    // Price maps for customization options
    public static final Map<String, Double> CRUST_PRICES = Map.of(
            "Thin", 10.0, "Thick", 20.0, "Crisp", 30.0);
    public static final Map<String, Double> SAUCE_PRICES = Map.of(
            "Tomato", 5.0, "Pesto", 10.0, "Sweet", 15.0);
    public static final Map<String, Double> TOPPING_PRICES = Map.of(
            "Chicken", 10.0, "Pepperoni", 20.0, "Mushroom", 5.0);
    public static final Map<String, Double> CHEESE_PRICES = Map.of(
            "Mozzarella", 10.0, "Cheddar", 15.0);
    public static final double EXTRA_CHEESE_PRICE = 50.0;

    // Size options
    public static final List<String> SIZE_OPTIONS = List.of("Small", "Regular", "Large");
}
