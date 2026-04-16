package com.bookmystay.service;

import com.bookmystay.inventory.RoomInventory;
import com.bookmystay.model.Reservation;
import com.bookmystay.queue.BookingRequestQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Service to process booking requests.
 */
public class BookingService {
    private RoomInventory inventory;
    private BookingRequestQueue queue;
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory, BookingRequestQueue queue) {
        this.inventory = inventory;
        this.queue = queue;
        this.allocatedRooms = new HashMap<>();
    }

    public void processNextBooking() {
        Reservation reservation = queue.pollNext();
        if (reservation == null) {
            System.out.println("No pending requests to process.");
            return;
        }

        System.out.println("Processing " + reservation.getReservationId() + "...");
        try {
            com.bookmystay.validator.InvalidBookingValidator.validateReservation(reservation);
            com.bookmystay.validator.InvalidBookingValidator.validateRoomType(reservation.getRoomType(), inventory);
            com.bookmystay.validator.InvalidBookingValidator.validateAvailability(reservation.getRoomType(), inventory);

            String roomType = reservation.getRoomType();
            int availability = inventory.getAvailability(roomType);

            String roomId = roomType + "-" + UUID.randomUUID().toString().substring(0, 8);
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomId);

            inventory.updateAvailability(roomType, availability - 1);
            reservation.setStatus("CONFIRMED");

            System.out.println("  -> SUCCESS! Allocated Room ID: " + roomId);
        } catch (com.bookmystay.exception.InvalidBookingException e) {
            reservation.setStatus("FAILED");
            System.out.println("  -> FAILED! Reason: " + e.getMessage());
        }
    }
}
