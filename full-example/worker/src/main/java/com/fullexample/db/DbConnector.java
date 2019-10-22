package com.fullexample.db;

public interface DbConnector {
    public void connect();
    public void execute(String query);
    public void close();
}
