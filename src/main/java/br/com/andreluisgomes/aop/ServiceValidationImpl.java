package br.com.andreluisgomes.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

@Aspect
@Component
public class ServiceValidationImpl {

    @Before("@annotation(ServiceValidation)")
    public void valid(JoinPoint joinPoint) throws Throwable {

        Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            for (Annotation paramAnnotation : parameterAnnotations[argIndex]) {
                if ((paramAnnotation instanceof Valid)) {
                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                    Validator validator = factory.getValidator();
                    Set<ConstraintViolation<Object>> violations = validator.validate(args[argIndex]);

                    if (!violations.isEmpty())
                        throw new RuntimeException();
                }
            }
        }
    }
}
