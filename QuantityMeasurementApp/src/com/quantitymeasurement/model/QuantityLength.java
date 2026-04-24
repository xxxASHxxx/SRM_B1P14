package com.quantitymeasurement.model;

import java.util.Objects;

/**
 * Generic quantity measurement applying DRY principle.
 *
 * @author Ashmit
 * @version 1.0
 */
public class QuantityLength {
    private final double value;
    private final Unit unit;

    public QuantityLength(double value, Unit unit) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Value must be a valid number");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit must not be null");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValueInBaseUnit() {
        return this.value * this.unit.getConversionFactor();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLength that = (QuantityLength) obj;
        return Math.abs(this.getValueInBaseUnit() - that.getValueInBaseUnit()) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValueInBaseUnit());
    }

    @Override
    public String toString() {
        return "QuantityLength(" + value + " " + unit + ")";
    }

    /**
     * Converts a value from sourceUnit to targetUnit.
     * @param value the numeric value to convert
     * @param sourceUnit the unit of the input value
     * @param targetUnit the unit to convert to
     * @return the converted value in targetUnit
     * @throws IllegalArgumentException if value is NaN/Infinite, or units are null
     */
    public static double convert(double value, Unit sourceUnit, Unit targetUnit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
        if (sourceUnit == null) throw new IllegalArgumentException("Source unit must not be null.");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit must not be null.");
        double baseValue = value * sourceUnit.getConversionFactor();
        return baseValue / targetUnit.getConversionFactor();
    }
}
