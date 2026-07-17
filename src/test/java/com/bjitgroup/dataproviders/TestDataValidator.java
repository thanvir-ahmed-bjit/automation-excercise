package com.bjitgroup.dataproviders;

import com.bjitgroup.exceptions.AutomationException;
import com.bjitgroup.models.UserData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * Validates test data models before they are used by test methods.
 */
public final class TestDataValidator {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = FACTORY.getValidator();

    private TestDataValidator() { /* utility */ }

    public static UserData validate(UserData userData) {
        Set<ConstraintViolation<UserData>> violations = VALIDATOR.validate(userData);
        if (violations.isEmpty()) {
            return userData;
        }

        StringBuilder message = new StringBuilder("Invalid test data:");
        for (ConstraintViolation<UserData> violation : violations) {
            message.append(System.lineSeparator())
                    .append(violation.getPropertyPath())
                    .append(": ")
                    .append(violation.getMessage());
        }
        throw new AutomationException(message.toString());
    }
}

