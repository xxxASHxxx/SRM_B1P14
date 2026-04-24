package com.quantitymeasurement.model;

/**
 * Enumeration for units of measurement.
 */
public enum Unit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.0328084);

    private final double conversionFactor;

    Unit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}
