package com.sachin.pizzabackend.controller;

import com.sachin.pizzabackend.model.Order;
import com.sachin.pizzabackend.model.Pizza;
import com.sachin.pizzabackend.model.PizzaItem;
import com.sachin.pizzabackend.model.User;
import com.sachin.pizzabackend.service.OrderService;
import com.sachin.pizzabackend.service.PizzaService;
import com.sachin.pizzabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private UserService userService;

    // Create a new order (cart)
    @PostMapping("/create")
    public Order createOrder(@RequestParam String email,
                             @RequestParam String deliveryOption,
                             @RequestParam(required = false) String pickupLocation,
                             @RequestParam(required = false) String deliveryAddress) {
        User user = userService.getUserByEmail(email);
        List<PizzaItem> pizzas = List.of(); // Empty cart initially
        return orderService.createOrder(user, pizzas, deliveryOption, pickupLocation,
                deliveryOption.equals("Delivery") ? deliveryAddress : null);
    }

    // method to get order details by givig user email
    @GetMapping("/user/{email}")
    public List<Order> getOrdersByUserEmail(@PathVariable String email) {
        return orderService.getOrdersByUserEmail(email);
    }

    // Endpoint to get all orders
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();  // Fetch all orders from the service
    }

    @PostMapping("/{orderId}/addPizza")
    public Order addPizza(@PathVariable String orderId,
                          @RequestParam String pizzaName,
                          @RequestParam int quantity,
                          @RequestParam String email,
                          @RequestParam(required = false) String pizzaSize,
                          @RequestParam(required = false, defaultValue = "false") boolean extraCheese) {

        // Fetch the user from their email
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + email);
        }

        // Try to get pizza from user's favorites first
        Pizza pizza = user.getFavorites().stream()
                .filter(favPizza -> favPizza.getName().equalsIgnoreCase(pizzaName))
                .findFirst()
                .orElse(null);

        // If pizza is not found in favorites, or if a specific size is required, fetch by name and size
        if (pizza == null) {
            if (pizzaSize == null || pizzaSize.isBlank()) {
                throw new IllegalArgumentException("Pizza size must be provided for non-favorite pizzas.");
            }
            pizza = pizzaService.getPizzaByNameAndSize(pizzaName, pizzaSize, extraCheese);
            if (pizza == null) {
                throw new IllegalArgumentException("Pizza not found: " + pizzaName + " with size: " + pizzaSize);
            }
        }

        // Retrieve the order by its ID
        Order order = orderService.getOrderById(orderId);

        // Add the pizza to the order
        return orderService.addPizzaToOrder(order, pizza, quantity);
    }


    // Adjust pizza quantity in the order
    @PostMapping("/{orderId}/adjustPizzaQuantity")
    public Order adjustPizzaQuantity(@PathVariable String orderId,
                                     @RequestParam String pizzaName,
                                     @RequestParam int newQuantity) {
        // Retrieve the order by its ID
        Order order = orderService.getOrderById(orderId);

        // Adjust the pizza quantity
        return orderService.adjustPizzaQuantity(order, pizzaName, newQuantity);
    }

    // Remove pizza from order
    @PostMapping("/{orderId}/removePizza")
    public Order removePizza(@PathVariable String orderId, @RequestParam String pizzaName) {
        // Retrieve the order by its ID
        Order order = orderService.getOrderById(orderId);

        // Remove the pizza from the order
        return orderService.removePizzaFromOrder(order, pizzaName);
    }

    // Get order details (for review)
    @GetMapping("/{orderId}")
    public Order getOrderDetails(@PathVariable String orderId) {
        // Retrieve and return order details by order ID
        return orderService.getOrderById(orderId);
    }

}
