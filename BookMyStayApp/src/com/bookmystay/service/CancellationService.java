package com.bookmystay.service;

import com.bookmystay.exception.InvalidBookingException;
import com.bookmystay.inventory.RoomInventory;
import com.bookmystay.model.Reservation;
import java.util.Stack;

/**
 * Service to cancel bookings and rollback inventory.
 */
public class CancellationService {
    private RoomInventory inventory;
    private BookingHistoryService historyService;
    private Stack<String> recentlyReleasedRoomIds;

    public CancellationService(RoomInventory inventory, BookingHistoryService historyService) {
        this.inventory = inventory;
        this.historyService = historyService;
        this.recentlyReleasedRoomIds = new Stack<>();
    }

    public void cancelBooking(String reservationId) {
        Reservation target = null;
        for (Reservation r : historyService.getAllBookings()) {
            if (r.getReservationId().equals(reservationId)) {
                target = r;
                break;
            }
        }

        if (target == null || !"CONFIRMED".equals(target.getStatus())) {
            throw new InvalidBookingException("Reservation " + reservationId + " not found or not CONFIRMED.");
        }

        String roomId = target.getRoomId();
        if (roomId == null) roomId = "UNKNOWN_ROOM";
        recentlyReleasedRoomIds.push(roomId);

        int currentCount = inventory.getAvailability(target.getRoomType());
        inventory.updateAvailability(target.getRoomType(), currentCount + 1);

        target.setStatus("CANCELLED");
        System.out.println("Booking " + reservationId + " cancelled successfully. Room ID " + roomId + " pushed to rollback stack.");
    }
}
