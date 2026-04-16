package com.quantitymeasurement.model;

import java.util.Objects;

/**
 * Represents a length measured in Inches.
 *
 * @author Ashmit
 * @version 1.0
 */
public class Inches {
    private double value;

    public Inches(double value) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("NaN is not allowed for value");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Inches inches = (Inches) obj;
        return Double.compare(inches.value, this.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Inches(" + value + ")";
    }
}
