package com.fullexample.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String firstName;
    private String lastName;

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
