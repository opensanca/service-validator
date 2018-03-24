package io.github.andrelugomes.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceValidationErrorCollection extends HashMap<String, List<String>> {

    public void addError(String valuePath, String errorMessage) {
        if (!containsKey(valuePath)) {
            put(valuePath, new ArrayList<>());
        }
        get(valuePath).add(errorMessage);
    }

}
