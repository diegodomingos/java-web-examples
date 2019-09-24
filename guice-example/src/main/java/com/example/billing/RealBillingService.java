package com.example.billing;

import com.example.model.Receipt;
import com.example.transaction.TransactionLog;
import com.example.model.CreditCard;
import com.example.model.PizzaOrder;
import com.example.processor.CreditCardProcessor;
import com.google.inject.Inject;

public class RealBillingService implements BillingService {
    private final CreditCardProcessor processor;
    private final TransactionLog transactionLog;

    @Inject
    public RealBillingService(CreditCardProcessor processor, TransactionLog transactionLog) {
        this.processor = processor;
        this.transactionLog = transactionLog;
    }

    public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
        Receipt receipt = processor.charge(creditCard, order.getAmmount());
        transactionLog.logReceipt(receipt);
        return receipt;
    }
}
