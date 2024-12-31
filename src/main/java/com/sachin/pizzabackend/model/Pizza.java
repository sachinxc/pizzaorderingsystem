package com.sachin.pizzabackend.model;

import java.util.List;
import java.util.Objects;

public class Pizza {
    private String name;
    private String type;  // "Default" or "Custom"
    private String crust;
    private String sauce;
    private List<String> toppings;
    private String cheese;
    private boolean extraCheese;
    private String size;
    private double price;
    private double rating;

    // Default constructor
    public Pizza() {
    }

    public Pizza(PizzaBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
        this.extraCheese = builder.extraCheese;
        this.size = builder.size;
        this.price = builder.price;
        this.rating = builder.rating;
    }

    // Getters for all the fields
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCrust() {
        return crust;
    }

    public String getSauce() {
        return sauce;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public String getCheese() {
        return cheese;
    }

    public boolean isExtraCheese() {
        return extraCheese;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return extraCheese == pizza.extraCheese &&
                Double.compare(pizza.price, price) == 0 &&
                Double.compare(pizza.rating, rating) == 0 &&
                Objects.equals(name, pizza.name) &&
                Objects.equals(type, pizza.type) &&
                Objects.equals(crust, pizza.crust) &&
                Objects.equals(sauce, pizza.sauce) &&
                Objects.equals(toppings, pizza.toppings) &&
                Objects.equals(cheese, pizza.cheese) &&
                Objects.equals(size, pizza.size);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(name, type, crust, sauce, toppings, cheese, extraCheese, size, price, rating);
    }

    // Builder class
    public static class PizzaBuilder {
        private String name;
        private String type;
        private String crust;
        private String sauce;
        private List<String> toppings;
        private String cheese;
        private boolean extraCheese;
        private String size;
        private double price;
        private double rating;

        public PizzaBuilder(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public PizzaBuilder crust(String crust) {
            this.crust = crust;
            return this;
        }

        public PizzaBuilder sauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public PizzaBuilder toppings(List<String> toppings) {
            this.toppings = toppings;
            return this;
        }

        public PizzaBuilder cheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        public PizzaBuilder extraCheese(boolean extraCheese) {
            this.extraCheese = extraCheese;
            return this;
        }

        public PizzaBuilder size(String size) {
            this.size = size;
            return this;
        }

        public PizzaBuilder price(double price) {
            this.price = price;
            return this;
        }

        public PizzaBuilder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }
}
