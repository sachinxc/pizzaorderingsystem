package com.sachin.pizzabackend.model;

public class Delivery {
    private String deliveryOption; // "Pickup" or "Delivery"
    private String deliveryAddress; // Address if delivery option is chosen
    private String pickupLocation;  // Pickup location if "Pickup" is chosen

    // Constructor for delivery option (Pickup or Delivery)
    public Delivery(String deliveryOption, String pickupLocation, String deliveryAddress) {
        this.deliveryOption = deliveryOption;
        this.pickupLocation = pickupLocation;
        this.deliveryAddress = deliveryOption.equals("Delivery") ? deliveryAddress : null;
    }

    // Getters and setters
    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    // Validates delivery option
    public boolean isValidDelivery() {
        return "Pickup".equalsIgnoreCase(deliveryOption) || "Delivery".equalsIgnoreCase(deliveryOption);
    }

    // Validates pickup location
    public boolean isValidPickupLocation() {
        return "Red".equalsIgnoreCase(pickupLocation) ||
                "Green".equalsIgnoreCase(pickupLocation) ||
                "Blue".equalsIgnoreCase(pickupLocation);
    }

    // Validates delivery address (should not be empty for delivery option)
    public boolean isValidAddress() {
        return "Delivery".equalsIgnoreCase(deliveryOption) && deliveryAddress != null && !deliveryAddress.isEmpty();
    }
}
