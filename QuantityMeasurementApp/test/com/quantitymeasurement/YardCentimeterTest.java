package com.quantitymeasurement;

import com.quantitymeasurement.model.QuantityLength;
import com.quantitymeasurement.model.Unit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class YardCentimeterTest {

    // YARD EQUALITY
    @Test
    public void given1YardAnd1Yard_whenCompared_thenShouldReturnTrue() {
        QuantityLength yard1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength yard2 = new QuantityLength(1.0, Unit.YARD);
        assertTrue(yard1.equals(yard2));
    }

    @Test
    public void given1YardAnd2Yard_whenCompared_thenShouldReturnFalse() {
        QuantityLength yard1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength yard2 = new QuantityLength(2.0, Unit.YARD);
        assertFalse(yard1.equals(yard2));
    }

    @Test
    public void given0YardAnd0Yard_whenCompared_thenShouldReturnTrue() {
        QuantityLength yard1 = new QuantityLength(0.0, Unit.YARD);
        QuantityLength yard2 = new QuantityLength(0.0, Unit.YARD);
        assertTrue(yard1.equals(yard2));
    }

    // CENTIMETER EQUALITY
    @Test
    public void given1CentimeterAnd1Centimeter_whenCompared_thenShouldReturnTrue() {
        QuantityLength cm1 = new QuantityLength(1.0, Unit.CENTIMETER);
        QuantityLength cm2 = new QuantityLength(1.0, Unit.CENTIMETER);
        assertTrue(cm1.equals(cm2));
    }

    @Test
    public void given1CentimeterAnd2Centimeter_whenCompared_thenShouldReturnFalse() {
        QuantityLength cm1 = new QuantityLength(1.0, Unit.CENTIMETER);
        QuantityLength cm2 = new QuantityLength(2.0, Unit.CENTIMETER);
        assertFalse(cm1.equals(cm2));
    }

    // CROSS-UNIT: YARD ↔ FEET
    @Test
    public void given1YardAnd3Feet_whenCompared_thenShouldReturnTrue() {
        QuantityLength yard = new QuantityLength(1.0, Unit.YARD);
        QuantityLength feet = new QuantityLength(3.0, Unit.FEET);
        assertTrue(yard.equals(feet));
    }

    @Test
    public void given3FeetAnd1Yard_whenCompared_thenShouldReturnTrue() {
        QuantityLength feet = new QuantityLength(3.0, Unit.FEET);
        QuantityLength yard = new QuantityLength(1.0, Unit.YARD);
        assertTrue(feet.equals(yard));
    }

    @Test
    public void given2YardAnd6Feet_whenCompared_thenShouldReturnTrue() {
        QuantityLength yard = new QuantityLength(2.0, Unit.YARD);
        QuantityLength feet = new QuantityLength(6.0, Unit.FEET);
        assertTrue(yard.equals(feet));
    }

    // CROSS-UNIT: YARD ↔ INCH
    @Test
    public void given1YardAnd36Inch_whenCompared_thenShouldReturnTrue() {
        QuantityLength yard = new QuantityLength(1.0, Unit.YARD);
        QuantityLength inch = new QuantityLength(36.0, Unit.INCH);
        assertTrue(yard.equals(inch));
    }

    @Test
    public void given36InchAnd1Yard_whenCompared_thenShouldReturnTrue() {
        QuantityLength inch = new QuantityLength(36.0, Unit.INCH);
        QuantityLength yard = new QuantityLength(1.0, Unit.YARD);
        assertTrue(inch.equals(yard));
    }

    // CROSS-UNIT: CENTIMETER ↔ INCH
    @Test
    public void given2_54CentimeterAnd1Inch_whenCompared_thenShouldReturnTrue() {
        QuantityLength cm = new QuantityLength(2.54, Unit.CENTIMETER);
        QuantityLength inch = new QuantityLength(1.0, Unit.INCH);
        assertTrue(cm.equals(inch));
    }

    @Test
    public void given1InchAnd2_54Centimeter_whenCompared_thenShouldReturnTrue() {
        QuantityLength inch = new QuantityLength(1.0, Unit.INCH);
        QuantityLength cm = new QuantityLength(2.54, Unit.CENTIMETER);
        assertTrue(inch.equals(cm));
    }

    // CROSS-UNIT: CENTIMETER ↔ FEET
    @Test
    public void given30_48CentimeterAnd1Feet_whenCompared_thenShouldReturnTrue() {
        QuantityLength cm = new QuantityLength(30.48, Unit.CENTIMETER);
        QuantityLength feet = new QuantityLength(1.0, Unit.FEET);
        assertTrue(cm.equals(feet));
    }

    // REGRESSION
    @Test
    public void given12InchAnd1Feet_whenCompared_thenShouldReturnTrue() {
        QuantityLength inch = new QuantityLength(12.0, Unit.INCH);
        QuantityLength feet = new QuantityLength(1.0, Unit.FEET);
        assertTrue(inch.equals(feet));
    }

    @Test
    public void given1FeetAnd1Feet_whenCompared_thenShouldReturnTrue() {
        QuantityLength feet1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength feet2 = new QuantityLength(1.0, Unit.FEET);
        assertTrue(feet1.equals(feet2));
    }
}
