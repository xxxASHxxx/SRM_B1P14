package com.quantitymeasurement.model;

/**
 * QuantityWeight class handling weight measurement logic.
 * Mirrors QuantityLengthV2 architecture.
 *
 * @author QuantityMeasurementApp
 * @version UC9
 */
public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    /**
     * Constructs a QuantityWeight with a value and unit.
     * @throws IllegalArgumentException if value is NaN or unit is null
     */
    public QuantityWeight(double value, WeightUnit unit) {
        if (Double.isNaN(value)) throw new IllegalArgumentException("Value must be a valid number.");
        if (unit == null) throw new IllegalArgumentException("Unit must not be null.");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public WeightUnit getUnit() { return unit; }

    /** Returns value converted to base unit (KILOGRAM) using WeightUnit delegation. */
    public double getValueInBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    /** Equals uses floating point tolerance via base unit comparison. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityWeight other = (QuantityWeight) obj;
        return Math.abs(this.getValueInBaseUnit() - other.getValueInBaseUnit()) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(Math.round(getValueInBaseUnit() * 10000.0) / 10000.0);
    }

    @Override
    public String toString() {
        return "QuantityWeight(" + value + " " + unit + ")";
    }

    /** Static convert using WeightUnit delegation. */
    public static double convert(double value, WeightUnit sourceUnit, WeightUnit targetUnit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
        if (sourceUnit == null) throw new IllegalArgumentException("Source unit must not be null.");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit must not be null.");
        double base = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(base);
    }

    /** Convert this quantity to a target unit. */
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit must not be null.");
        double convertedValue = convert(this.value, this.unit, targetUnit);
        return new QuantityWeight(convertedValue, targetUnit);
    }

    /** Add two weights, result in first operand's unit. */
    public static QuantityWeight add(QuantityWeight w1, QuantityWeight w2) {
        if (w1 == null) throw new IllegalArgumentException("w1 must not be null.");
        if (w2 == null) throw new IllegalArgumentException("w2 must not be null.");
        double sumInBase = w1.getValueInBaseUnit() + w2.getValueInBaseUnit();
        return new QuantityWeight(w1.unit.convertFromBaseUnit(sumInBase), w1.unit);
    }

    /** Add two weights with explicit target unit. */
    public static QuantityWeight add(QuantityWeight w1, QuantityWeight w2, WeightUnit targetUnit) {
        if (w1 == null) throw new IllegalArgumentException("w1 must not be null.");
        if (w2 == null) throw new IllegalArgumentException("w2 must not be null.");
        if (targetUnit == null) throw new IllegalArgumentException("targetUnit must not be null.");
        double sumInBase = w1.getValueInBaseUnit() + w2.getValueInBaseUnit();
        return new QuantityWeight(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }
}
