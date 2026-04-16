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
    }
}

