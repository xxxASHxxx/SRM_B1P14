package com.bookmystay.service;

import com.bookmystay.model.Reservation;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to maintain booking history.
 */
public class BookingHistoryService {
    private List<Reservation> history;

    public BookingHistoryService() {
        history = new ArrayList<>();
    }

    public void addToHistory(Reservation res) {
        history.add(res);
    }

    public List<Reservation> getAllBookings() {
        return history;
    }

    public void displayHistory() {
        System.out.println("Booking History:");
        for (Reservation res : history) {
            System.out.println(res);
        }
    }
}
