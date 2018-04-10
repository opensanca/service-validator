package io.github.opensanca;

import io.github.opensanca.aop.ServiceValidationAspectImpl;
import io.github.opensanca.exception.ServiceValidationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static io.github.opensanca.aop.ServiceValidationAspectImpl.NULLSAFE_VIOLATION_MESSAGE;
import static io.github.opensanca.matchers.ServiceValidatorErrorMatcher.errorCollectionHasSize;
import static io.github.opensanca.matchers.ServiceValidatorViolationsMatcher.errorCollectionHasViolation;

@RunWith(MockitoJUnitRunner.class)
public class ServiceValidationMockitoTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private MyComponent component;

    @InjectMocks
    private MyService service = new MyServiceImpl();

    @Mock
    private ServiceValidationAspectImpl serviceValidationAspect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceValidationAspectImpl aspect = new ServiceValidationAspectImpl();

        AspectJProxyFactory componentFactory = new AspectJProxyFactory(component);
        componentFactory.addAspect(aspect);
        component = componentFactory.getProxy();

        AspectJProxyFactory serviceFactory = new AspectJProxyFactory(service);
        serviceFactory.addAspect(aspect);
        service = serviceFactory.getProxy();
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
    public void shouldValidateNullForJavaTypes() {
        exception.expect(ServiceValidationException.class);
        exception.expect(errorCollectionHasSize(1));
        exception.expect(errorCollectionHasViolation("String", NULLSAFE_VIOLATION_MESSAGE));

        service.getStringByName(null);
    }
}
