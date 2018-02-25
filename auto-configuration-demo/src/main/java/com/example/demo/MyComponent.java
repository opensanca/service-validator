package com.example.demo;

import io.github.andrelugomes.annotation.ServiceValidation;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    @ServiceValidation
    public void doSomething(DTO dto){

    }
}
