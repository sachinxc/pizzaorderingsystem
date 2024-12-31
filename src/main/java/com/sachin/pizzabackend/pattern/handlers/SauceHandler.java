package com.sachin.pizzabackend.pattern.handlers;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.data.PizzaData;

public class SauceHandler implements PizzaHandler {

    private PizzaHandler nextHandler;

    @Override
    public Pizza handle(Pizza pizza, String size, boolean extraCheese) {
        if (pizza.getSauce() != null) {
            double saucePrice = PizzaData.SAUCE_PRICES.getOrDefault(pizza.getSauce(), 0.0);
            pizza = new Pizza.PizzaBuilder(pizza.getName(), pizza.getType())
                    .crust(pizza.getCrust())       // Preserve crust
                    .sauce(pizza.getSauce())
                    .cheese(pizza.getCheese())     // Preserve cheese
                    .toppings(pizza.getToppings()) // Preserve toppings
                    .size(size)
                    .price(pizza.getPrice() + saucePrice)
                    .rating(pizza.getRating())
                    .extraCheese(extraCheese)
                    .build();
        }

        if (nextHandler != null) {
            return nextHandler.handle(pizza, size, extraCheese);
        }

        return pizza;
    }


    @Override
    public void setNext(PizzaHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
