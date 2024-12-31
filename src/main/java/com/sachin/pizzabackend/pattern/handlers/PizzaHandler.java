package com.sachin.pizzabackend.pattern.handlers;

import com.sachin.pizzabackend.model.Pizza;

public interface PizzaHandler {
    Pizza handle(Pizza pizza, String size, boolean extraCheese);
    void setNext(PizzaHandler nextHandler);
}
