package com.quantitymeasurement.app;

import com.quantitymeasurement.model.Feet;
import com.quantitymeasurement.model.Inches;

public class QuantityMeasurementApp {

    static boolean compareFeet(double a, double b) {
        Feet f1 = new Feet(a);
        Feet f2 = new Feet(b);
        return f1.equals(f2);
    }

    static boolean compareInches(double a, double b) {
        Inches i1 = new Inches(a);
        Inches i2 = new Inches(b);
        return i1.equals(i2);
    }

    public static void main(String[] args) {
        System.out.println("=== Quantity Measurement App ===");

        // UC1 and UC2 Demo
        System.out.println("Feet 1.0 equals Feet 1.0: " + compareFeet(1.0, 1.0));
        System.out.println("Feet 1.0 equals Feet 2.0: " + compareFeet(1.0, 2.0));

        System.out.println("Inches 1.0 equals Inches 1.0: " + compareInches(1.0, 1.0));
        System.out.println("Inches 1.0 equals Inches 2.0: " + compareInches(1.0, 2.0));
    }
}
