package com.bookmystay.service;

import com.bookmystay.inventory.RoomInventory;
import com.bookmystay.model.Reservation;
import com.bookmystay.queue.BookingRequestQueue;
import java.util.UUID;

/**
 * Processor for concurrent booking requests.
 */
public class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue queue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(BookingRequestQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        Reservation reservation = null;
        synchronized (queue) {
            reservation = queue.pollNext();
        }

        if (reservation == null) {
            return;
        }

        String threadName = Thread.currentThread().getName();
        String roomType = reservation.getRoomType();

        synchronized (inventory) {
            int availability = inventory.getAvailability(roomType);
            if (availability > 0) {
                String roomId = roomType + "-" + UUID.randomUUID().toString().substring(0, 8);
                inventory.updateAvailability(roomType, availability - 1);
                reservation.setStatus("CONFIRMED");
                reservation.setRoomId(roomId);
                System.out.println(threadName + " processed " + reservation.getReservationId() + " -> SUCCESS! Room ID: " + roomId);
            } else {
                reservation.setStatus("FAILED");
                System.out.println(threadName + " processed " + reservation.getReservationId() + " -> FAILED! No " + roomType + " available.");
            }
        }
    }
}
