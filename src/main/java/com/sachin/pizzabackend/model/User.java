package com.sachin.pizzabackend.model;

import com.sachin.pizzabackend.pattern.observer.OrderObserver;

import java.util.List;

public class User implements OrderObserver {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private List<Pizza> favorites;  // A list to store the favorite pizzas
    private int points;  // Loyalty points

    public User(String name, String email, String phoneNumber, String password, List<Pizza> favorites, int points) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.favorites = favorites;
        this.points = points;
    }

    // Implement the updateOrderStatus method from OrderObserver
    @Override
    public void updateOrderStatus(String orderId, String status) {
        System.out.println("User " + name + " is notified that Order " + orderId + " is now: " + status);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pizza> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Pizza> favorites) {
        this.favorites = favorites;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    // Method to add a pizza to favorites
    public void addFavorite(Pizza pizza) {
        this.favorites.add(pizza);
    }

    // Method to remove a pizza from favorites
    public void removeFavorite(Pizza pizza) {
        this.favorites.remove(pizza);
    }

    // Method to convert User to UserSummary (returns only name and email)
    public UserSummary toUserSummary() {
        return new UserSummary(this.name, this.email, this.phoneNumber);
    }

    // Inner class for UserSummary, which excludes sensitive data
    public static class UserSummary {
        private String name;
        private String email;
        private String phoneNumber;

        public UserSummary(String name, String email, String phoneNumber) {
            this.name = name;
            this.email = email;
            this.phoneNumber =phoneNumber;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {
            return email;
        }

        // Method to convert User to UserSummary (for excluding sensitive data)
        public UserSummary toUserSummary() {
            return new UserSummary(this.name, this.email, this.phoneNumber);
        }
    }
}
