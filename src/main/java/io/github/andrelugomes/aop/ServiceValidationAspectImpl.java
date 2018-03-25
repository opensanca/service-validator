package io.github.andrelugomes.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
import org.aspectj.lang.reflect.MethodSignature;
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
                    String parameterName = resolveParameterName(joinPoint, argIndex);
                    errors.addError(parameterName, NULLSAFE_VIOLATION_MESSAGE);
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
                        String parameterName = resolveParameterName(joinPoint, argIndex);
                        for (ConstraintViolation violation : violations) {
                            String valuePath = String.format("%s.%s", parameterName, violation.getPropertyPath());
                            errors.addError(valuePath, violation.getMessage());
                        }
                    }
                }

            }
        }

        if(!errors.isEmpty())
            throw new ServiceValidationException(errors);
    }

    private String resolveParameterName(JoinPoint joinPoint, int argIndex) {
        if (!(joinPoint.getSignature() instanceof MethodSignature)) {
            return String.format("args[%s]", argIndex);
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        return parameters[argIndex].getType().getSimpleName();
    }
}
