package com.bswdi.dotenv;

/**
 * Signals that a dotenv exception has occurred.
 */
public class DotenvException extends RuntimeException {
    /**
     * Create a dotenv runtime exception with the specified detail message
     * @param message the detail message
     */
    public DotenvException(String message) {
        super(message);
    }

    /**
     * Creates a dotenv runtime exception
     * @param cause the cause
     */
    public DotenvException(Throwable cause) {
        super(cause);
    }
}
