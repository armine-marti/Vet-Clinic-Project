package org.example.vetclinic.exception;

/**
 * Custom exception thrown when a user is not found in the system.
 * This exception extends {@link RuntimeException} and is used to signal
 * that an operation attempted to access a user that does not exist.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@link UserNotFoundException} with the specified detail message.
     *
     * @param message the detail message, which provides more information about the exception
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link UserNotFoundException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method)
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link UserNotFoundException} with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method)
     * @param enableSuppression  whether or not suppression is enabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
