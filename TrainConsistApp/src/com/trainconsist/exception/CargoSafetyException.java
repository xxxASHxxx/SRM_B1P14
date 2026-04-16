package com.trainconsist.exception;

/**
 * Exception thrown when cargo is unsafely assigned to an incompatible bogie shape.
 */
public class CargoSafetyException extends RuntimeException {
    public CargoSafetyException(String message) {
        super(message);
    }
}
