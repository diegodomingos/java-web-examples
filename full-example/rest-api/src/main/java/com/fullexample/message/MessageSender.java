package com.fullexample.message;

public interface MessageSender {
    public void init() throws Exception;
    public boolean isReady();
    public void send(String message) throws Exception;
}
