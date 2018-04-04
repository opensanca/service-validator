package io.github.opensanca;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import io.github.opensanca.aop.ServiceValidationAspectImpl;
import io.github.opensanca.exception.ServiceValidationErrorCollection;
import io.github.opensanca.exception.ServiceValidationException;
import org.junit.Before;
import org.junit.Test;
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
        try {
            component.defaultValidation(new DTO());
            fail("Should've thrown ServiceValidationException");
        } catch (ServiceValidationException ex) {
            ServiceValidationErrorCollection errors = ex.getErrors();
            assertThat(errors).hasSize(1);
            assertThat(errors.get("DTO.text")).hasSize(1);
            assertThat(errors.get("DTO.text").get(0)).isEqualToIgnoringCase("may not be null");
        }
    }

    @Test
    public void shouldValidateNullSafe() {
        try {
            component.defaultValidation(null);
            fail("Should've thrown ServiceValidationException");
        } catch (ServiceValidationException ex) {
            ServiceValidationErrorCollection errors = ex.getErrors();
            assertThat(errors).hasSize(1);
            assertThat(errors.get("DTO")).hasSize(1);
            assertThat(errors.get("DTO").get(0)).isEqualToIgnoringCase(ServiceValidationAspectImpl.NULLSAFE_VIOLATION_MESSAGE);
        }
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
        try {
            service.getStringByName(null);
            fail("Should've thrown ServiceValidationException");
        } catch (ServiceValidationException ex) {
            ServiceValidationErrorCollection errors = ex.getErrors();
            assertThat(errors).hasSize(1);
            assertThat(errors.get("String")).hasSize(1);
            assertThat(errors.get("String").get(0)).isEqualToIgnoringCase(ServiceValidationAspectImpl.NULLSAFE_VIOLATION_MESSAGE);
        }
    }

    @Test
    public void shouldValidateNullForMultiplesJavaTypes() {
        try {
            service.getLong(null, "2");
            fail("Should've thrown ServiceValidationException");
        } catch (ServiceValidationException ex) {
            ServiceValidationErrorCollection errors = ex.getErrors();
            assertThat(errors).hasSize(1);
            assertThat(errors.get("Long")).hasSize(1);
            assertThat(errors.get("Long").get(0)).isEqualToIgnoringCase(ServiceValidationAspectImpl.NULLSAFE_VIOLATION_MESSAGE);
        }
    }

    @Test
    public void shouldCombineNullAndJavaxErrors() {
        try {
            DTO invalidDTO = new DTO();
            DTO validDTO = new DTO();
            validDTO.setText("TEST");
            service.doSomethingElse(null, invalidDTO, validDTO);
            fail("Should've thrown ServiceValidationException");
        } catch (ServiceValidationException ex) {
            ServiceValidationErrorCollection errors = ex.getErrors();
            assertThat(errors).hasSize(2);
            assertThat(errors.get("DTO")).hasSize(1);
            assertThat(errors.get("DTO").get(0)).isEqualToIgnoringCase(ServiceValidationAspectImpl.NULLSAFE_VIOLATION_MESSAGE);
            assertThat(errors.get("DTO.text")).hasSize(1);
            assertThat(errors.get("DTO.text").get(0)).isEqualToIgnoringCase("may not be null");
        }
    }

    @Test
    public void shouldJsonifyServiceValidationException() throws Exception {
        this.mockMvc.perform(get("/test"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("DTO").isArray())
            .andExpect(jsonPath("DTO[0]").value(ServiceValidationAspectImpl.NULLSAFE_VIOLATION_MESSAGE))
            .andExpect(jsonPath("$['DTO.text']").isArray())
            .andExpect(jsonPath("$['DTO.text'][0]").value("may not be null"));
    }

}



