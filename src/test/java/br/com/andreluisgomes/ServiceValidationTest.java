package br.com.andreluisgomes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceValidationTest {

    @Autowired
    private MyComponent component;

    @Autowired
    private MyService service;

    @Test(expected = RuntimeException.class)
    public void shouldValidateJavax() {

        component.defaultValidation(new DTO());
    }

    @Test(expected = RuntimeException.class)
    public void shouldValidateNullSafe() {

        component.defaultValidation(null);
    }

    @Test
    public void shouldValidateJavaxAndNullByDefault() {

        DTO dto = new DTO();
        dto.setText("TEST");
        DTO result = component.defaultValidation(dto);

        assertThat(result.getText()).isEqualToIgnoringCase("TEST");
    }

    @Test
    public void shouldNotValidateNullJustJavax() {

        DTO dto = new DTO();
        dto.setText("TEST");
        DTO result = component.nullSafeFalse(dto);

        assertThat(result).isInstanceOf(DTO.class);
        assertThat(result.getText()).isNotNull();
    }

    @Test
    public void shouldNotValidateNullJustJavaxPassingNull() {

        DTO result = component.nullSafeFalse(null);
        assertThat(result).isNull();
    }

    @Test
    public void shouldNotValidateJavaxJustNull() {

        DTO dto = new DTO();
        DTO result = component.javaxValidationFalse(dto);

        assertThat(result.getText()).isNull();
    }

    @Test(expected = RuntimeException.class)
    public void shouldValidateNullForJavaTypes() {

        service.getStringByName(null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldValidateNullForMultiplesJavaTypes() {

        service.getLong(null, "2");
    }
}



