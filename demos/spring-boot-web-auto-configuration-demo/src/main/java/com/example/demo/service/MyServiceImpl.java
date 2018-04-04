package com.example.demo.service;

import com.example.demo.dto.DTO;
import io.github.opensanca.annotation.ServiceValidation;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {

    @Override
    @ServiceValidation
    public void doSomething (DTO dto) {

    }
}
