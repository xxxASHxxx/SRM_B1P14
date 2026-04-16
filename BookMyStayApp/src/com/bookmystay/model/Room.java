package com.bookmystay.model;

/**
 * Abstract class representing a Room.
 */
public abstract class Room {
    protected String roomType;
    protected double price;
    protected int numberOfBeds;
    protected int sizeSqFt;

    public Room(String roomType, double price, int numberOfBeds, int sizeSqFt) {
        this.roomType = roomType;
        this.price = price;
        this.numberOfBeds = numberOfBeds;
        this.sizeSqFt = sizeSqFt;
    }

    public abstract void displayDetails();

    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public int getNumberOfBeds() { return numberOfBeds; }
    public int getSizeSqFt() { return sizeSqFt; }
}
