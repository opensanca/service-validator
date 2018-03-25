package com.example.demo;

import io.github.opensanca.annotation.ServiceValidation;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements  MyService {

    @Override
    @ServiceValidation
    public void doSomething(DTO dto) {

    }
}
