package io.github.opensanca;

import static io.github.opensanca.aop.ServiceValidationAspectImpl.NULLSAFE_VIOLATION_MESSAGE;
import static io.github.opensanca.matchers.ServiceValidatorErrorMatcher.errorCollectionHasSize;
import static io.github.opensanca.matchers.ServiceValidatorViolationsMatcher.errorCollectionHasViolation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.opensanca.exception.ServiceValidationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceValidationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private MyComponent component;

    @Autowired
    private MyService service;

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
    }

    @Test
    public void shouldValidateJavax() {
        exception.expect(ServiceValidationException.class);
        exception.expect(errorCollectionHasSize(1));
        exception.expect(errorCollectionHasViolation("DTO.text", "may not be null"));
        exception.expect(errorCollectionHasViolation("DTO.text", "may not be empty"));

        component.defaultValidation(new DTO());
    }

    @Test
    public void shouldValidateNullSafe() {
        exception.expect(ServiceValidationException.class);
        exception.expect(errorCollectionHasSize(1));
        exception.expect(errorCollectionHasViolation("DTO", NULLSAFE_VIOLATION_MESSAGE));

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

    @Test
    public void shouldValidateNullForJavaTypes() {
        exception.expect(ServiceValidationException.class);
        exception.expect(errorCollectionHasSize(1));
        exception.expect(errorCollectionHasViolation("String", NULLSAFE_VIOLATION_MESSAGE));

        service.getStringByName(null);
    }

    @Test
    public void shouldValidateNullForMultiplesJavaTypes() {
        exception.expect(ServiceValidationException.class);
        exception.expect(errorCollectionHasSize(1));
        exception.expect(errorCollectionHasViolation("Long", NULLSAFE_VIOLATION_MESSAGE));

        service.getLong(null, "2");
    }

    @Test
    public void shouldCombineNullAndJavaxErrors() {
        exception.expect(ServiceValidationException.class);
        exception.expect(errorCollectionHasSize(2));
        exception.expect(errorCollectionHasViolation("DTO", NULLSAFE_VIOLATION_MESSAGE));
        exception.expect(errorCollectionHasViolation("DTO.text", "may not be null"));

        DTO invalidDTO = new DTO();
        DTO validDTO = new DTO();
        validDTO.setText("TEST");
        service.doSomethingElse(null, invalidDTO, validDTO);
    }

    @Test
    public void shouldJsonifyServiceValidationException() throws Exception {
        this.mockMvc.perform(get("/test"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("DTO").isArray())
            .andExpect(jsonPath("DTO[0]").value(NULLSAFE_VIOLATION_MESSAGE))
            .andExpect(jsonPath("$['DTO.text']").isArray())
            .andExpect(jsonPath("$['DTO.text']", hasSize(2)))
            .andExpect(jsonPath("$['DTO.text']", containsInAnyOrder("may not be null","may not be empty")));
    }

}



