package com.quantitymeasurement.model;

/**
 * Enum representing supported weight units with built-in conversion logic.
 * Each unit is responsible for converting to and from the base unit (KILOGRAM).
 *
 * @author QuantityMeasurementApp
 * @version UC9
 */
public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactor; // factor to convert this unit to base unit (KILOGRAM)

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    /**
     * Returns the conversion factor for this unit relative to KILOGRAM (base unit).
     */
    public double getConversionFactor() {
        return conversionFactor;
    }

    /**
     * Converts a value expressed in this unit to the base unit (KILOGRAM).
     * @param value the numeric value in this unit
     * @return the equivalent value in KILOGRAM
     */
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    /**
     * Converts a value expressed in the base unit (KILOGRAM) to this unit.
     * @param baseValue the numeric value in KILOGRAM
     * @return the equivalent value in this unit
     */
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}
