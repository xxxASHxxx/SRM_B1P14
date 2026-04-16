package com.trainconsist.model;

import com.trainconsist.exception.InvalidCapacityException;

/**
 * Represents a passenger bogie.
 */
public class PassengerBogie {
    public String bogieType;
    public int capacity;

    public PassengerBogie(String bogieType, int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero. Got: " + capacity);
        }
        this.bogieType = bogieType;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return bogieType + " [capacity=" + capacity + "]";
    }
}
