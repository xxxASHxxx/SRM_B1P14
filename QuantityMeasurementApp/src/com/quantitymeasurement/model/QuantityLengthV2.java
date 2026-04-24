package com.quantitymeasurement.model;

/**
 * Refactored QuantityLength that delegates all conversion logic to LengthUnit.
 * Applies Single Responsibility Principle \u2014 this class handles only value comparison and arithmetic.
 * Conversion logic resides in LengthUnit itself.
 *
 * @author QuantityMeasurementApp
 * @version UC8
 */
public class QuantityLengthV2 {

    private final double value;
    private final LengthUnit unit;

    /**
     * Constructs a QuantityLengthV2 with a value and unit.
     * @throws IllegalArgumentException if value is NaN or unit is null
     */
    public QuantityLengthV2(double value, LengthUnit unit) {
        if (Double.isNaN(value)) throw new IllegalArgumentException("Value must be a valid number.");
        if (unit == null) throw new IllegalArgumentException("Unit must not be null.");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public LengthUnit getUnit() { return unit; }

    /** Returns value converted to base unit (FEET) using LengthUnit delegation. */
    public double getValueInBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    /** Equals uses floating point tolerance via base unit comparison. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLengthV2 other = (QuantityLengthV2) obj;
        return Math.abs(this.getValueInBaseUnit() - other.getValueInBaseUnit()) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(Math.round(getValueInBaseUnit() * 10000.0) / 10000.0);
    }

    @Override
    public String toString() {
        return "QuantityLengthV2(" + value + " " + unit + ")";
    }

    /** Static convert using LengthUnit delegation. */
    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
        if (sourceUnit == null) throw new IllegalArgumentException("Source unit must not be null.");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit must not be null.");
        double base = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(base);
    }

    /** Add two lengths, result in first operand's unit. */
    public static QuantityLengthV2 add(QuantityLengthV2 l1, QuantityLengthV2 l2) {
        if (l1 == null) throw new IllegalArgumentException("l1 must not be null.");
        if (l2 == null) throw new IllegalArgumentException("l2 must not be null.");
        double sumInBase = l1.getValueInBaseUnit() + l2.getValueInBaseUnit();
        return new QuantityLengthV2(l1.unit.convertFromBaseUnit(sumInBase), l1.unit);
    }

    /** Add two lengths with explicit target unit. */
    public static QuantityLengthV2 add(QuantityLengthV2 l1, QuantityLengthV2 l2, LengthUnit targetUnit) {
        if (l1 == null) throw new IllegalArgumentException("l1 must not be null.");
        if (l2 == null) throw new IllegalArgumentException("l2 must not be null.");
        if (targetUnit == null) throw new IllegalArgumentException("targetUnit must not be null.");
        double sumInBase = l1.getValueInBaseUnit() + l2.getValueInBaseUnit();
        return new QuantityLengthV2(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }
}
