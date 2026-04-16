package com.bookmystay.model;

/**
 * Suite Room class.
 */
public class SuiteRoom extends Room {
    public SuiteRoom() {
        super("SUITE", 8000.0, 3, 1000);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + roomType + " | Price: " + price + " | Beds: " + numberOfBeds + " | Size: " + sizeSqFt + " sqft");
    }
}
