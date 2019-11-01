package com.example.model;

import lombok.Data;

import java.security.Principal;
import java.util.Set;

@Data
public class User implements Principal {
    private final String name;
}
