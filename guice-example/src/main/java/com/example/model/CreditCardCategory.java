package com.example.model;

public enum CreditCardCategory {
    GOLD(100),
    PLATINUM(1000),
    BLACK(10000);

    private int limit;

    CreditCardCategory(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
