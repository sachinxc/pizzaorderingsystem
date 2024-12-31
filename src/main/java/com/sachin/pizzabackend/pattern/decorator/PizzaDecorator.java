package com.sachin.pizzabackend.pattern.decorator;

import com.sachin.pizzabackend.model.Pizza;

public abstract class PizzaDecorator extends Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        super(new Pizza.PizzaBuilder(pizza.getName(), pizza.getType())
                .crust(pizza.getCrust())
                .sauce(pizza.getSauce())
                .toppings(pizza.getToppings())
                .cheese(pizza.getCheese())
                .extraCheese(pizza.isExtraCheese())
                .size(pizza.getSize())
                .price(pizza.getPrice())
                .rating(pizza.getRating()));
        this.pizza = pizza;
    }

    @Override
    public abstract double getPrice();
}
