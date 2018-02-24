package com.example.demo;

import br.com.andreluisgomes.annotation.ServiceValidation;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements  MyService {

    @Override
    @ServiceValidation
    public void doSomething(DTO dto) {

    }
}
