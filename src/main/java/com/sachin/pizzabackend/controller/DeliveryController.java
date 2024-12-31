package com.sachin.pizzabackend.controller;

import com.sachin.pizzabackend.model.Delivery;
import com.sachin.pizzabackend.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // Get available delivery options (Pickup or Delivery)
    @GetMapping("/options")
    public String[] getDeliveryOptions() {
        return deliveryService.getDeliveryOptions();
    }

    // Get available pickup locations
    @GetMapping("/pickupLocations")
    public String[] getPickupLocations() {
        return deliveryService.getPickupLocations();
    }

    // Create a delivery
    @PostMapping("/create")
    public String createDelivery(@RequestParam String deliveryOption,
                                 @RequestParam(required = false) String pickupLocation,
                                 @RequestParam(required = false) String deliveryAddress) {
        // Create Delivery object based on input
        Delivery delivery = deliveryService.createDelivery(deliveryOption, pickupLocation, deliveryAddress);

        // Validate delivery option and address
        if (!deliveryService.isValidDeliveryOption(delivery)) {
            return "Invalid delivery option or address/pickup location.";
        }

        return "Delivery created successfully!";
    }
}
