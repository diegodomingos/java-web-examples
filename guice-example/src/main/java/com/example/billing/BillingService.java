package com.example.billing;

import com.example.model.CreditCard;
import com.example.model.PizzaOrder;
import com.example.model.Receipt;

public interface BillingService {

    Receipt chargeOrder(PizzaOrder order, CreditCard creditCard);
}
