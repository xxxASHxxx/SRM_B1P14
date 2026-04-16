package com.bookmystay.service;

import com.bookmystay.model.Reservation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to maintain booking history.
 */
public class BookingHistoryService implements Serializable {
    private static final long serialVersionUID = 1L;
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
