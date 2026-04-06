package com.finance.exception;

/**
 * AccessDeniedException
 * Thrown when user doesn't have permission to perform an action
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }
}