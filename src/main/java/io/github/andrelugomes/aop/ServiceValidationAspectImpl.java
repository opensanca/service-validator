package io.github.andrelugomes.aop;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import io.github.andrelugomes.annotation.ServiceValidation;
import io.github.andrelugomes.exception.ServiceValidationErrorCollection;
import io.github.andrelugomes.exception.ServiceValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceValidationAspectImpl {

    public static final String NULLSAFE_VIOLATION_MESSAGE = "Method arguments cannot be null!";

    @Pointcut("@annotation(serviceValidation)")
    public void annotationPointCutDefinition(ServiceValidation serviceValidation){ }

    @Before("annotationPointCutDefinition(serviceValidation)")
    public void valid(JoinPoint joinPoint, ServiceValidation serviceValidation) {

        ServiceValidationErrorCollection errors = new ServiceValidationErrorCollection();
        Object[] args = joinPoint.getArgs();

        if(serviceValidation.nullSafe()){
            for (int argIndex = 0; argIndex < args.length; argIndex++) {

                if(args[argIndex] == null) {
                    String valuePath = String.format("args[%s]", argIndex);
                    errors.addError(valuePath, NULLSAFE_VIOLATION_MESSAGE);
                }
            }
        }

        if(serviceValidation.javaxValidation()){
            for (int argIndex = 0; argIndex < args.length; argIndex++) {

                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Object arg = args[argIndex];

                if (arg != null) {
                    Set<ConstraintViolation<Object>> violations = validator.validate(arg);
                    if (!violations.isEmpty()) {
                        for (ConstraintViolation violation : violations) {
                            String valuePath = String.format("args[%s].%s", argIndex, violation.getPropertyPath());
                            errors.addError(valuePath, violation.getMessage());
                        }
                    }
                }

            }
        }

        if(!errors.isEmpty())
            throw new ServiceValidationException(errors);
    }
}
