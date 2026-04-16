package com.trainconsist.exception;

/**
 * Exception thrown when a bogie is provided with an invalid capacity.
 */
public class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String message) {
        super(message);
    }
}
