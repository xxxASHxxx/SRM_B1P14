package com.bookmystay.exception;

/**
 * Custom runtime exception for invalid bookings.
 */
public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message) {
        super(message);
    }
}
