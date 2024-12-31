package com.sachin.pizzabackend.pattern.handlers;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.data.PizzaData;

public class ToppingHandler implements PizzaHandler {

    private PizzaHandler nextHandler;

    @Override
    public Pizza handle(Pizza pizza, String size, boolean extraCheese) {
        if (pizza.getToppings() != null) {
            double toppingPrice = pizza.getToppings().stream()
                    .mapToDouble(topping -> PizzaData.TOPPING_PRICES.getOrDefault(topping, 0.0))
                    .sum();
            pizza = new Pizza.PizzaBuilder(pizza.getName(), pizza.getType())
                    .crust(pizza.getCrust())       // Preserve crust
                    .sauce(pizza.getSauce())       // Preserve sauce
                    .cheese(pizza.getCheese())     // Preserve cheese
                    .toppings(pizza.getToppings())
                    .size(size)
                    .price(pizza.getPrice() + toppingPrice)
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
