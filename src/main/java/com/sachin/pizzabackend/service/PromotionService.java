package com.sachin.pizzabackend.service;

import com.sachin.pizzabackend.model.Promotion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    // promotions data predifined
    private List<Promotion> promotions = List.of(
            new Promotion("promo1", "New Year Offer", 0.10, true),
            new Promotion("promo2", "Weekend Special", 0.15, false),
            new Promotion("promo3", "Loyalty Bonus", 0.05, true)
    );

    // Get all active promotions
    public List<Promotion> getActivePromotions() {
        return promotions.stream()
                .filter(Promotion::isActive)
                .toList();
    }
}
