package com.sachin.pizzabackend.controller;

import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    // Endpoint to get list of default pizzas
    @GetMapping("/default")
    public List<Pizza> getDefaultPizzas() {
        return pizzaService.getDefaultPizzas();
    }

    // Endpoint to get a default pizza with a specific size and extra cheese option
    @GetMapping("/default/{pizzaName}/{size}")
    public Pizza getPizzaByNameAndSize(
            @PathVariable String pizzaName,
            @PathVariable String size,
            @RequestParam(defaultValue = "false") boolean extraCheese) {
        return pizzaService.getPizzaByNameAndSize(pizzaName, size, extraCheese);
    }

    // Endpoint to create custom pizza with user-defined name
    @PostMapping("/custom")
    public Pizza createCustomPizza(@RequestBody Pizza customPizza) {
        return pizzaService.createCustomPizza(
                customPizza.getName(),
                customPizza.getCrust(),
                customPizza.getSauce(),
                customPizza.getToppings(),
                customPizza.getCheese(),
                customPizza.isExtraCheese(),
                customPizza.getSize()
        );
    }

    // Endpoint to get available crust options
    @GetMapping("/crusts")
    public Map<String, Double> getCrustOptions() {
        return pizzaService.getCrustOptions();
    }

    // Endpoint to get available sauce options
    @GetMapping("/sauces")
    public Map<String, Double> getSauceOptions() {
        return pizzaService.getSauceOptions();
    }

    // Endpoint to get available topping options
    @GetMapping("/toppings")
    public Map<String, Double> getToppingOptions() {
        return pizzaService.getToppingOptions();
    }

    // Endpoint to get available cheese options
    @GetMapping("/cheeses")
    public Map<String, Double> getCheeseOptions() {
        return pizzaService.getCheeseOptions();
    }

    // Endpoint for available sizes
    @GetMapping("/sizes")
    public List<String> getSizeOptions() {
        return pizzaService.getSizeOptions();
    }
}
