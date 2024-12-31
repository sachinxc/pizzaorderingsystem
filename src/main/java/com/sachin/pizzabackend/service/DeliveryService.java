package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.model.Delivery;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    // Validate delivery options and pickup locations
    public boolean isValidDeliveryOption(Delivery delivery) {
        if (!delivery.isValidDelivery()) {
            return false;
        }

        if ("Pickup".equalsIgnoreCase(delivery.getDeliveryOption())) {
            return delivery.isValidPickupLocation(); // Ensure valid pickup location
        } else if ("Delivery".equalsIgnoreCase(delivery.getDeliveryOption())) {
            return delivery.isValidAddress(); // Ensure valid delivery address
        }

        return false;
    }

    // Fetch available delivery options (Pickup or Delivery)
    public String[] getDeliveryOptions() {
        return new String[]{"Pickup", "Delivery"};
    }

    // Fetch available pickup locations (Red, Green, Blue centers)
    public String[] getPickupLocations() {
        return new String[]{"Red Pizza Center", "Green Pizza Center", "Blue Pizza Center"};
    }

    // Handle delivery logic (check delivery address and pickup location)
    public Delivery createDelivery(String deliveryOption, String pickupLocation, String deliveryAddress) {
        return new Delivery(deliveryOption, pickupLocation, deliveryAddress);
    }
}
