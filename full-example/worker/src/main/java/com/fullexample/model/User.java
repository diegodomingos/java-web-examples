package com.fullexample.model;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
}
