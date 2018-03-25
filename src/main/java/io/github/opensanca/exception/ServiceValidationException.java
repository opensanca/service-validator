package io.github.opensanca.exception;


/**
 * Thrown when one or more parameters of
 * an annotated method is invalid, i.e.
 * is null or do not pass JSR-303 validation.
 */
public class ServiceValidationException extends RuntimeException {

    private ServiceValidationErrorCollection errors;

    public ServiceValidationException(ServiceValidationErrorCollection errors) {
        this.errors = errors;
    }

    public ServiceValidationErrorCollection getErrors() {
        return errors;
    }
}
