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

    private static <U extends IMeasurable> double performArithmetic(Quantity<U> q1, Quantity<U> q2, ArithmeticOperation op) {
        if (q1 == null || q2 == null) throw new IllegalArgumentException("Quantities must not be null.");
        if (q1.unit.getClass() != q2.unit.getClass()) throw new IllegalArgumentException("Cannot perform arithmetic on different measurement categories.");
        
        double base1 = q1.getValueInBaseUnit();
        double base2 = q2.getValueInBaseUnit();

        switch (op) {
            case ADD:
                return base1 + base2;
            case SUBTRACT:
                return base1 - base2;
            case DIVIDE:
                if (base2 == 0.0) throw new ArithmeticException("Division by zero.");
                return base1 / base2;
            default:
                throw new UnsupportedOperationException("Unsupported operation.");
        }
    }

    public static <U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2) {
        double resultInBase = performArithmetic(q1, q2, ArithmeticOperation.ADD);
        return new Quantity<>(q1.unit.convertFromBaseUnit(resultInBase), q1.unit);
    }

    public static <U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        double resultInBase = performArithmetic(q1, q2, ArithmeticOperation.ADD);
        if (targetUnit == null) throw new IllegalArgumentException("Target unit must not be null.");
        if (q1.unit.getClass() != targetUnit.getClass()) throw new IllegalArgumentException("Measurement categories must match.");
        return new Quantity<>(targetUnit.convertFromBaseUnit(resultInBase), targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2) {
        double resultInBase = performArithmetic(q1, q2, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(q1.unit.convertFromBaseUnit(resultInBase), q1.unit);
    }

    public static <U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        double resultInBase = performArithmetic(q1, q2, ArithmeticOperation.SUBTRACT);
        if (targetUnit == null) throw new IllegalArgumentException("Target unit must not be null.");
        if (q1.unit.getClass() != targetUnit.getClass()) throw new IllegalArgumentException("Measurement categories must match.");
        return new Quantity<>(targetUnit.convertFromBaseUnit(resultInBase), targetUnit);
    }

    public static <U extends IMeasurable> double divide(Quantity<U> q1, Quantity<U> q2) {
        return performArithmetic(q1, q2, ArithmeticOperation.DIVIDE);
    }
}
