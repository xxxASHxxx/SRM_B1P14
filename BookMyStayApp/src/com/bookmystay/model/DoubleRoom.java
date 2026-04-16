package com.bookmystay.model;

/**
 * Double Room class.
 */
public class DoubleRoom extends Room {
    public DoubleRoom() {
        super("DOUBLE", 3500.0, 2, 400);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + roomType + " | Price: " + price + " | Beds: " + numberOfBeds + " | Size: " + sizeSqFt + " sqft");
    }
}
