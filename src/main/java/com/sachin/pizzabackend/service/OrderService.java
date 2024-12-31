package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.model.PizzaItem;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.model.Delivery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList; // For in-memory storage

@Service
public class OrderService {

    private List<Order> orders = new ArrayList<>();  // initialize the order list

    // Atomic counter to generate unique order IDs
    private static final AtomicInteger orderIdCounter = new AtomicInteger(1);

    // Get an order by ID
    public Order getOrderById(String orderId) {
        Optional<Order> order = orders.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst();

        return order.orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }

    // Method to get all orders belonging to a user by their email
    public List<Order> getOrdersByUserEmail(String email) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUser().getEmail().equals(email)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    // Method to retrieve all orders from all users
    public List<Order> getAllOrders() {
        return orders;  // Return the list of all orders
    }

    // Create a new order
    public Order createOrder(User user, List<PizzaItem> pizzas, String deliveryOption, String pickupLocation, String deliveryAddress) {
        // Generate order ID using the counter
        String orderId = String.valueOf(orderIdCounter.getAndIncrement());

        // Create Delivery object based on the provided details
        Delivery delivery = new Delivery(deliveryOption, pickupLocation, deliveryAddress);

        // Validate delivery option and address/pickup location using Delivery class
        if (!delivery.isValidDelivery()) {
            throw new IllegalArgumentException("Invalid delivery option or address/pickup location.");
        }

        // Create a mutable pizzas list
        List<PizzaItem> pizzaItems = new ArrayList<>(pizzas);  // Using ArrayList to ensure it's mutable

        // Create new order
        Order order = new Order(orderId, user, pizzaItems, delivery);

        // Save the order to the orders list
        orders.add(order);

        // Return the created order, which now contains the orderId
        return order;
    }

    // Add pizza to the order
    public Order addPizzaToOrder(Order order, Pizza pizza, int quantity) {
        PizzaItem pizzaItem = new PizzaItem(pizza, quantity);
        order.getPizzaItems().add(pizzaItem);  // Use getPizzaItems() instead of getPizzas()
        order.setTotalPrice(order.calculateTotalPrice());
        order.setOrderPoints(order.calculatePoints());
        return order;
    }

    // Adjust pizza quantity in the order
    public Order adjustPizzaQuantity(Order order, String pizzaName, int newQuantity) {
        for (PizzaItem pizzaItem : order.getPizzaItems()) {
            if (pizzaItem.getPizza().getName().equals(pizzaName)) {  // Use pizzaName to identify the pizza
                pizzaItem.setQuantity(newQuantity);
                break;
            }
        }
        order.setTotalPrice(order.calculateTotalPrice());
        order.setOrderPoints(order.calculatePoints());
        return order;
    }

    // Remove pizza from the order
    public Order removePizzaFromOrder(Order order, String pizzaName) {
        order.getPizzaItems().removeIf(pizzaItem -> pizzaItem.getPizza().getName().equals(pizzaName));  // Use pizzaName for removal
        order.setTotalPrice(order.calculateTotalPrice());
        order.setOrderPoints(order.calculatePoints());
        return order;
    }
}
