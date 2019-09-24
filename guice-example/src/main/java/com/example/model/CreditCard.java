package com.example.model;

public class CreditCard {

    final CreditCardCategory category;

    public CreditCard(CreditCardCategory category) {
        this.category = category;
    }

    public CreditCardCategory getCategory() {
        return category;
    }
}
