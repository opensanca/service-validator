package com.example.demo;

import br.com.andreluisgomes.annotation.ServiceValidation;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    @ServiceValidation
    public void doSomething(DTO dto){

    }
}
