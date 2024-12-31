package com.sachin.pizzabackend.pattern.handlers;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.data.PizzaData;

public class CrustHandler implements PizzaHandler {

    private PizzaHandler nextHandler;

    @Override
    public Pizza handle(Pizza pizza, String size, boolean extraCheese) {
        if (pizza.getCrust() != null) {
            double crustPrice = PizzaData.CRUST_PRICES.getOrDefault(pizza.getCrust(), 0.0);
            pizza = new Pizza.PizzaBuilder(pizza.getName(), pizza.getType())
                    .crust(pizza.getCrust())
                    .sauce(pizza.getSauce())    // Preserve existing sauce
                    .cheese(pizza.getCheese())  // Preserve existing cheese
                    .toppings(pizza.getToppings()) // Preserve existing toppings
                    .size(size)
                    .price(pizza.getPrice() + crustPrice)
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
