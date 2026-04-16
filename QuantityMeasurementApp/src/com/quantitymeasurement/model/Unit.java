package com.quantitymeasurement.model;

/**
 * Enumeration for units of measurement.
 */
public enum Unit {
    FEET(1.0),
    INCH(1.0 / 12.0);

    private final double conversionFactor;

    Unit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}
