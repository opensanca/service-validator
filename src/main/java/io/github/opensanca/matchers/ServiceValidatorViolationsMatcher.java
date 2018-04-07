package io.github.opensanca.matchers;

import java.util.List;

import io.github.opensanca.exception.ServiceValidationException;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.util.CollectionUtils;

public class ServiceValidatorViolationsMatcher extends TypeSafeMatcher<ServiceValidationException> {

    private String key;
    private String value;
    private List<String> constraints;
    private boolean hasErrorKey;
    private boolean hasErrorValue;

    public ServiceValidatorViolationsMatcher(final String key, final String value) {
        this.key = key;
        this.value = value;
        this.hasErrorKey = false;
        this.hasErrorValue = false;
    }

    public static ServiceValidatorViolationsMatcher errorCollectionHasViolation(final String key, final String value) {
        return new ServiceValidatorViolationsMatcher(key, value);
    }

    @Override
    protected boolean matchesSafely(final ServiceValidationException e) {
        constraints = e.getErrors().get(key);
        if (CollectionUtils.isEmpty(constraints)) {
            hasErrorKey = true;
            return false;
        } else {
            final boolean isValidationOk = constraints.contains(value);
            hasErrorValue = !isValidationOk;
            return isValidationOk;
        }
    }

    @Override
    public void describeTo(final Description description) {
        if (hasErrorKey) {
            description.appendValue(key.toString()).appendText(" key not found.");
        }
        if (hasErrorValue) {
            description.appendText("Found ").appendValue(constraints).appendText(" instead of ").appendValue(value);
        }
    }
}
