package com.trainconsist.model;

/**
 * Represents a train bogie with an associated capacity.
 */
public class Bogie {
    public String name;
    public int capacity;

    public Bogie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return name + " (" + capacity + " seats)";
    }
}
