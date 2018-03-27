package com.example.demo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class DTO {

    @NotNull
    String text;

    @Max(10)
    Integer number;

    public void setNumber (Integer number) {
        this.number = number;
    }

    public void setText (String text) {
        this.text = text;
    }
}
