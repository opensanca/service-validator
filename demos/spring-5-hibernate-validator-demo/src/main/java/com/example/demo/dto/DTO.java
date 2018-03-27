package com.example.demo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class DTO {

    @NotNull
    private String text;

    @Max(10)
    private Integer number;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText () {
        return text;
    }

    public Integer getNumber () {
        return number;
    }
}
