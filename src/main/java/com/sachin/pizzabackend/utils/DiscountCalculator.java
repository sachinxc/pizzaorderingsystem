package com.sachin.pizzabackend.utils;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.User;

public class DiscountCalculator {

    public static double calculateDiscount(Order order, User user, boolean usePoints) {
        double discount = 0;

        if (usePoints) {
            int userPoints = user.getPoints();
            if (userPoints >= 200) {
                discount = order.getTotalPrice() * 0.15;
                user.setPoints(userPoints - 200);
            } else if (userPoints >= 100) {
                discount = order.getTotalPrice() * 0.10;
                user.setPoints(userPoints - 100);
            } else if (userPoints >= 50) {
                discount = order.getTotalPrice() * 0.05;
                user.setPoints(userPoints - 50);
            }
        }

        return discount;
    }
}
