package com.example.demo.component;

import com.example.demo.dto.DTO;
import io.github.opensanca.annotation.ServiceValidation;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    @ServiceValidation
    public void doSomething (DTO dto) {

    }
}
