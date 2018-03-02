package io.github.andrelugomes;

import io.github.andrelugomes.annotation.ServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

@SpringBootApplication
public class DemoSpringBootWebApp {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBootWebApp.class, args);
    }
}

class DTO {

    @NotNull
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

@Component
class MyComponent {

    @ServiceValidation
    public DTO defaultValidation(final DTO dto) {
        return dto;
    }

    @ServiceValidation(nullSafe = false)
    public DTO nullSafeFalse(final DTO dto) {
        return dto;
    }

    @ServiceValidation(javaxValidation = false)
    public DTO javaxValidationFalse(final DTO dto) {
        return dto;
    }

    @ServiceValidation(nullSafe = false, javaxValidation = false)
    public DTO dontDoThat(final DTO dto) {
        return dto;
    }
}

interface MyService {

    DTO doSomething(DTO dto);

    String getStringByName(String name);

    Long getLong(Long number, String string);
}

@Service
class MyServiceImpl implements MyService {

    @Autowired
    private MyComponent component;

    @Override
    @ServiceValidation
    public DTO doSomething(final DTO dto) {
        return dto;
    }

    @Override
    @ServiceValidation
    public String getStringByName(String name) {
        return name;
    }

    @Override
    @ServiceValidation
    public Long getLong(Long number, String string) {
        return number + Long.valueOf(string);
    }
}
