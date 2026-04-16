package com.bookmystay.model;

/**
 * Single Room class.
 */
public class SingleRoom extends Room {
    public SingleRoom() {
        super("SINGLE", 2000.0, 1, 200);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + roomType + " | Price: " + price + " | Beds: " + numberOfBeds + " | Size: " + sizeSqFt + " sqft");
    }
}
