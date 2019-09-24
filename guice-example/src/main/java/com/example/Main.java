package com.example;

import com.example.billing.BillingModule;
import com.example.billing.BillingService;
import com.example.model.CreditCard;
import com.example.model.CreditCardCategory;
import com.example.model.PizzaOrder;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BillingModule());
        BillingService billingService = injector.getInstance(BillingService.class);

        billingService.chargeOrder(new PizzaOrder(2000), new CreditCard(CreditCardCategory.BLACK));
        billingService.chargeOrder(new PizzaOrder(2000), new CreditCard(CreditCardCategory.PLATINUM));
    }
}
