package com.example.demo;

import io.github.andrelugomes.annotation.ServiceValidation;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements  MyService {

    @Override
    @ServiceValidation
    public void doSomething(DTO dto) {

    }
}
