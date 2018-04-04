package com.example.demo;

import io.github.opensanca.ServiceValidatorImport;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(ServiceValidatorImport.class)
public class AppConfig {

    public static void main (String[] args) throws Exception {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
