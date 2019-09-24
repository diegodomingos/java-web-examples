package com.example.processor;

import com.example.model.CreditCard;
import com.example.model.Receipt;

public interface CreditCardProcessor {

    Receipt charge(CreditCard card, double ammount);
}
