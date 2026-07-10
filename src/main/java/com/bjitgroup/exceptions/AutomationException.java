package com.bjitgroup.exceptions;

/**
 * Unchecked exception for framework-level failures.
 */
public class AutomationException extends RuntimeException {

    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}


