package com.example.demo;

import io.github.opensanca.annotation.ServiceValidation;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    @ServiceValidation
    public void doSomething(DTO dto){

    }
}
