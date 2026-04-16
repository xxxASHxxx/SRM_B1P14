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
        return Double.compare(this.getValueInBaseUnit(), that.getValueInBaseUnit()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValueInBaseUnit());
    }

    @Override
    public String toString() {
        return "QuantityLength(" + value + " " + unit + ")";
    }
}
