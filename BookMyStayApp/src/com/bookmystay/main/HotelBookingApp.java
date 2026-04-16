package com.bookmystay.main;

import com.bookmystay.model.DoubleRoom;
import com.bookmystay.model.Room;
import com.bookmystay.model.SingleRoom;
import com.bookmystay.model.SuiteRoom;

/**
 * Main application entry point for Book My Stay.
 *
 * @author Ashmit
 * @version 1.0
 */
public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay - Hotel Booking System v1.0");
        System.out.println("---------------------------------------------------");

        // UC2: Basic Room Types and Static Availability
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // UC3: Centralized Room Inventory with HashMap
        com.bookmystay.inventory.RoomInventory inventory = new com.bookmystay.inventory.RoomInventory();

        System.out.println("Room Details & Availability:");
        singleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(singleRoom.getRoomType()));

        doubleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(doubleRoom.getRoomType()));

        suiteRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(suiteRoom.getRoomType()));
        
        System.out.println("---------------------------------------------------");
        inventory.displayInventory();

        System.out.println("---------------------------------------------------");
        // UC4: Room Search and Availability Check
        java.util.List<Room> allRooms = java.util.Arrays.asList(singleRoom, doubleRoom, suiteRoom);
        com.bookmystay.service.SearchService searchService = new com.bookmystay.service.SearchService(inventory, allRooms);
        searchService.searchAvailableRooms();

        System.out.println("---------------------------------------------------");
        // UC5: Booking Request Queue with FIFO
        com.bookmystay.queue.BookingRequestQueue queue = new com.bookmystay.queue.BookingRequestQueue();
        com.bookmystay.model.Reservation r1 = new com.bookmystay.model.Reservation("R001", "Alice", "SINGLE");
        com.bookmystay.model.Reservation r2 = new com.bookmystay.model.Reservation("R002", "Bob", "DOUBLE");
        com.bookmystay.model.Reservation r3 = new com.bookmystay.model.Reservation("R003", "Charlie", "SUITE");

        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);
        
        queue.displayQueue();

        System.out.println("---------------------------------------------------");
        // UC6: Reservation Confirmation and Room Allocation
        com.bookmystay.service.BookingService bookingService = new com.bookmystay.service.BookingService(inventory, queue);
        while (queue.getSize() > 0) {
            bookingService.processNextBooking();
        }

        System.out.println("---------------------------------------------------");
        // UC7: Add-On Service Selection
        com.bookmystay.service.AddOnServiceManager addOnManager = new com.bookmystay.service.AddOnServiceManager();
        com.bookmystay.model.AddOnService breakfast = new com.bookmystay.model.AddOnService("Breakfast", 500.0);
        com.bookmystay.model.AddOnService spa = new com.bookmystay.model.AddOnService("Spa", 1500.0);
        
        addOnManager.addService("R001", breakfast);
        addOnManager.addService("R001", spa);

        addOnManager.displayServicesForReservation("R001");
        System.out.println("Total Add-On Cost for R001: Rs. " + addOnManager.getTotalCost("R001"));

        System.out.println("---------------------------------------------------");
        // UC8: Booking History and Reporting
        com.bookmystay.service.BookingHistoryService historyService = new com.bookmystay.service.BookingHistoryService();
        if ("CONFIRMED".equals(r1.getStatus()) || "FAILED".equals(r1.getStatus())) historyService.addToHistory(r1);
        if ("CONFIRMED".equals(r2.getStatus()) || "FAILED".equals(r2.getStatus())) historyService.addToHistory(r2);
        if ("CONFIRMED".equals(r3.getStatus()) || "FAILED".equals(r3.getStatus())) historyService.addToHistory(r3);
        
        historyService.displayHistory();

        com.bookmystay.service.BookingReportService reportService = new com.bookmystay.service.BookingReportService(historyService);
        reportService.generateReport();

        System.out.println("---------------------------------------------------");
        // UC10: Booking Cancellation and Inventory Rollback
        com.bookmystay.service.CancellationService cancellationService = new com.bookmystay.service.CancellationService(inventory, historyService);
        try {
            cancellationService.cancelBooking("R001");
        } catch (com.bookmystay.exception.InvalidBookingException e) {
            System.out.println(e.getMessage());
        }

        inventory.displayInventory();
    }
}

