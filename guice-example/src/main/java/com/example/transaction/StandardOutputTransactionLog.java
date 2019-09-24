package com.example.transaction;

import com.example.model.Receipt;

public class StandardOutputTransactionLog implements TransactionLog {
    public void logReceipt(Receipt receipt) {
        System.out.println(receipt.toString());
    }
}
