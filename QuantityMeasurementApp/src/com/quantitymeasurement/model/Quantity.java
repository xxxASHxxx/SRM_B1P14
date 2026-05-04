package com.quantitymeasurement.model;

import java.util.Objects;

/**
 * Generic Quantity class that works with any measurement category (U extends IMeasurable).
 * Replaces category-specific classes like QuantityLength and QuantityWeight.
 *
 * @author QuantityMeasurementApp
 * @version UC10
 * @param <U> the specific unit type (e.g., LengthUnit, WeightUnit)
 */
public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Value must be a valid, finite number.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit must not be null.");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double getValueInBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity<?> other = (Quantity<?>) obj;
        if (this.unit.getClass() != other.unit.getClass()) return false;
        return Math.abs(this.getValueInBaseUnit() - other.getValueInBaseUnit()) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Double.hashCode(Math.round(getValueInBaseUnit() * 10000.0) / 10000.0), unit.getClass());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + " " + unit.getUnitName() + ")";
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null.");
        }
        double baseValue = this.unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
        // Round to two decimal places if needed, but standard precision is typically maintained
        return new Quantity<>(convertedValue, targetUnit);
    }

    public static <U extends IMeasurable> double convert(double value, U sourceUnit, U targetUnit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
        if (sourceUnit == null || targetUnit == null) throw new IllegalArgumentException("Units must not be null.");
        double base = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(base);
    }

    public static <U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("Quantities must not be null.");
        if (q1.unit.getClass() != q2.unit.getClass()) throw new IllegalArgumentException("Cannot add different measurement categories.");
        double sumInBase = q1.getValueInBaseUnit() + q2.getValueInBaseUnit();
        return new Quantity<>(q1.unit.convertFromBaseUnit(sumInBase), q1.unit);
    }

    public static <U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        if (q1 == null || q2 == null || targetUnit == null) throw new IllegalArgumentException("Arguments must not be null.");
        if (q1.unit.getClass() != q2.unit.getClass() || q1.unit.getClass() != targetUnit.getClass()) {
            throw new IllegalArgumentException("Measurement categories must match.");
        }
        double sumInBase = q1.getValueInBaseUnit() + q2.getValueInBaseUnit();
        return new Quantity<>(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("Quantities must not be null.");
        if (q1.unit.getClass() != q2.unit.getClass()) throw new IllegalArgumentException("Cannot subtract different measurement categories.");
        double diffInBase = q1.getValueInBaseUnit() - q2.getValueInBaseUnit();
        return new Quantity<>(q1.unit.convertFromBaseUnit(diffInBase), q1.unit);
    }

    public static <U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        if (q1 == null || q2 == null || targetUnit == null) throw new IllegalArgumentException("Arguments must not be null.");
        if (q1.unit.getClass() != q2.unit.getClass() || q1.unit.getClass() != targetUnit.getClass()) {
            throw new IllegalArgumentException("Measurement categories must match.");
        }
        double diffInBase = q1.getValueInBaseUnit() - q2.getValueInBaseUnit();
        return new Quantity<>(targetUnit.convertFromBaseUnit(diffInBase), targetUnit);
    }

    public static <U extends IMeasurable> double divide(Quantity<U> q1, Quantity<U> q2) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("Quantities must not be null.");
        if (q1.unit.getClass() != q2.unit.getClass()) throw new IllegalArgumentException("Cannot divide different measurement categories.");
        if (q2.getValueInBaseUnit() == 0.0) throw new ArithmeticException("Division by zero.");
        return q1.getValueInBaseUnit() / q2.getValueInBaseUnit();
    }
}
