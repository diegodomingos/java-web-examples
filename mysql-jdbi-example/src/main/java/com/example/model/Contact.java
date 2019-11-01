package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @JsonProperty
    private int id;
    @JsonProperty
    @NotBlank
    @Length(min=2, max=255)
    private String firstName;
    @JsonProperty
    @NotBlank
    @Length(min=2, max=255)
    private String lastName;
    @JsonProperty
    @NotBlank
    @Length(min=2, max=30)
    private String phone;
}
