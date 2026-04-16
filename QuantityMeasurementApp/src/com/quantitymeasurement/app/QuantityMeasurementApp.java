package com.quantitymeasurement.app;

import com.quantitymeasurement.model.Feet;

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        System.out.println("=== Quantity Measurement App ===");

        // UC1 Demo
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("Objects: " + f1 + ", " + f2 + ", " + f3);
        System.out.println("Equality of 1.0 feet and 1.0 feet: " + f1.equals(f2));
        System.out.println("Equality of 1.0 feet and 2.0 feet: " + f1.equals(f3));
    }
}
