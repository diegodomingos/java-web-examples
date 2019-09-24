package com.example.transaction;

import com.example.model.Receipt;

public interface TransactionLog {

    void logReceipt(Receipt receipt);
}
