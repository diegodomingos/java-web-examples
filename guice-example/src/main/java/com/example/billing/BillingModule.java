package com.example.billing;

import com.example.processor.CreditCardProcessor;
import com.example.processor.PaypalCreditCardProcessor;
import com.example.transaction.StandardOutputTransactionLog;
import com.example.transaction.TransactionLog;
import com.google.inject.AbstractModule;

public class BillingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransactionLog.class).to(StandardOutputTransactionLog.class);
        bind(CreditCardProcessor.class).to(PaypalCreditCardProcessor.class);
        bind(BillingService.class).to(RealBillingService.class);
    }
}
