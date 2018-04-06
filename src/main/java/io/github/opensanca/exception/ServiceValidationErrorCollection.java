package io.github.opensanca.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceValidationErrorCollection extends HashMap<String, List<String>> {

    public void addError(final String valuePath, final String errorMessage) {
        if (!containsKey(valuePath)) {
            put(valuePath, new ArrayList<>());
        }
        get(valuePath).add(errorMessage);
    }

}
