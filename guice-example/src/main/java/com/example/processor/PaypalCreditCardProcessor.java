package com.example.processor;

import com.example.model.CreditCard;
import com.example.model.Receipt;

public class PaypalCreditCardProcessor implements CreditCardProcessor {
    public Receipt charge(CreditCard card, double ammount) {
        if (ammount <= card.getCategory().getLimit()) {
            return Receipt.forSuccessfulCharge(ammount);
        }
        else {
            return Receipt.forDeclinedCharge("Limit exceeded", ammount);
        }
    }
}
