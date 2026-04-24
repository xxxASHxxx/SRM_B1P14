package com.quantitymeasurement.model;

/**
 * Enum representing supported length units with built-in conversion logic.
 * Each unit is responsible for converting to and from the base unit (FEET).
 * Applying Single Responsibility Principle \u2014 conversion logic lives in the unit, not the quantity.
 *
 * @author QuantityMeasurementApp
 * @version UC8
 */
public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.0328084);

    private final double conversionFactor; // factor to convert this unit to base unit (FEET)

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    /**
     * Returns the conversion factor for this unit relative to FEET (base unit).
     */
    public double getConversionFactor() {
        return conversionFactor;
    }

    /**
     * Converts a value expressed in this unit to the base unit (FEET).
     * @param value the numeric value in this unit
     * @return the equivalent value in FEET
     */
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    /**
     * Converts a value expressed in the base unit (FEET) to this unit.
     * @param baseValue the numeric value in FEET
     * @return the equivalent value in this unit
     */
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }
}
