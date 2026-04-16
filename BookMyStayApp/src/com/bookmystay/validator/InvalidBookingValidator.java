package com.bookmystay.validator;

import com.bookmystay.exception.InvalidBookingException;
import com.bookmystay.inventory.RoomInventory;
import com.bookmystay.model.Reservation;

/**
 * Validator for bookings.
 */
public class InvalidBookingValidator {

    public static void validateRoomType(String roomType, RoomInventory inventory) {
        if (!"SINGLE".equals(roomType) && !"DOUBLE".equals(roomType) && !"SUITE".equals(roomType)) {
            throw new InvalidBookingException("Room type '" + roomType + "' is not found/invalid.");
        }
    }

    public static void validateAvailability(String roomType, RoomInventory inventory) {
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
    }

    public static void validateReservation(Reservation r) {
        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be null or empty.");
        }
    }
}
