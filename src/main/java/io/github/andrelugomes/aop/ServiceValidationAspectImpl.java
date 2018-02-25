package io.github.andrelugomes.aop;

import io.github.andrelugomes.annotation.ServiceValidation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Set;

@Aspect
@Component
public class ServiceValidationAspectImpl {

    public static final String METHOD_ARGUMENTS_CANNOT_BE_NULL = "Method arguments cannot be null!";

    @Pointcut("@annotation(serviceValidation)")
    public void annotationPointCutDefinition(ServiceValidation serviceValidation){ }

    @After("annotationPointCutDefinition(serviceValidation)")
    public void valid(JoinPoint joinPoint, ServiceValidation serviceValidation) {

        Object[] args = joinPoint.getArgs();

        if(serviceValidation.nullSafe()){
            for (int argIndex = 0; argIndex < args.length; argIndex++) {

                if(args[argIndex] == null)
                    throw new RuntimeException(METHOD_ARGUMENTS_CANNOT_BE_NULL);
            }
        }

        if(serviceValidation.javaxValidation()){
            for (int argIndex = 0; argIndex < args.length; argIndex++) {

                Set<ConstraintViolation<Object>> violations = Collections.emptySet();
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Object arg = args[argIndex];
                if (arg != null)
                    violations = validator.validate(arg);

                if (!violations.isEmpty())
                    throw new RuntimeException();
            }
        }
    }
}
