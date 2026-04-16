package com.bookmystay.queue;

import com.bookmystay.model.Reservation;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Queue to hold booking requests.
 */
public class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        requestQueue.add(r);
    }

    public Reservation peekNext() {
        return requestQueue.peek();
    }

    public Reservation pollNext() {
        return requestQueue.poll();
    }

    public int getSize() {
        return requestQueue.size();
    }

    public void displayQueue() {
        System.out.println("Booking Request Queue (" + getSize() + " pending):");
        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }
}
