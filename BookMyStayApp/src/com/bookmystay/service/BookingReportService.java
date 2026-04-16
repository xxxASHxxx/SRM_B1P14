package com.bookmystay.service;

import com.bookmystay.model.Reservation;
import java.util.HashMap;
import java.util.Map;

/**
 * Service to generate booking reports.
 */
public class BookingReportService {
    private BookingHistoryService historyService;

    public BookingReportService(BookingHistoryService historyService) {
        this.historyService = historyService;
    }

    public void generateReport() {
        int confirmed = 0;
        int failed = 0;
        Map<String, Integer> roomTypeCounts = new HashMap<>();

        for (Reservation res : historyService.getAllBookings()) {
            if ("CONFIRMED".equals(res.getStatus())) {
                confirmed++;
                roomTypeCounts.put(res.getRoomType(), roomTypeCounts.getOrDefault(res.getRoomType(), 0) + 1);
            } else if ("FAILED".equals(res.getStatus())) {
                failed++;
            }
        }

        System.out.println("--- Booking Report ---");
        System.out.println("Total Confirmed Bookings: " + confirmed);
        System.out.println("Total Failed Bookings: " + failed);
        System.out.println("Confirmed Breakdown by Room Type:");
        for (Map.Entry<String, Integer> entry : roomTypeCounts.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("----------------------");
    }
}
