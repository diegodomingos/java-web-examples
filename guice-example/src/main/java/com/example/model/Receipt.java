package com.example.model;

public class Receipt {
    String message;

    public Receipt(String message) {
        this.message = message;
    }

    public static Receipt forSuccessfulCharge(double ammount) {
        return new Receipt("Successfully paid U$" + ammount);
    }

    public static Receipt forDeclinedCharge(String message, double ammount) {
        return new Receipt("Payment of U$" + ammount + " was denied. Reason: " + message);
    }

    @Override
    public String toString() {
        return message;
    }
}
