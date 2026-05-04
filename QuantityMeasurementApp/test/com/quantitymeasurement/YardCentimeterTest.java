package com.quantitymeasurement;

import com.quantitymeasurement.model.Quantity;
import com.quantitymeasurement.model.LengthUnit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class YardCentimeterTest {

    // YARD EQUALITY
    @Test
    public void given1YardAnd1Yard_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> yard1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> yard2 = new Quantity<>(1.0, LengthUnit.YARD);
        assertTrue(yard1.equals(yard2));
    }

    @Test
    public void given1YardAnd2Yard_whenCompared_thenShouldReturnFalse() {
        Quantity<LengthUnit> yard1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> yard2 = new Quantity<>(2.0, LengthUnit.YARD);
        assertFalse(yard1.equals(yard2));
    }

    @Test
    public void given0YardAnd0Yard_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> yard1 = new Quantity<>(0.0, LengthUnit.YARD);
        Quantity<LengthUnit> yard2 = new Quantity<>(0.0, LengthUnit.YARD);
        assertTrue(yard1.equals(yard2));
    }

    // CENTIMETER EQUALITY
    @Test
    public void given1CentimeterAnd1Centimeter_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> cm1 = new Quantity<>(1.0, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> cm2 = new Quantity<>(1.0, LengthUnit.CENTIMETER);
        assertTrue(cm1.equals(cm2));
    }

    @Test
    public void given1CentimeterAnd2Centimeter_whenCompared_thenShouldReturnFalse() {
        Quantity<LengthUnit> cm1 = new Quantity<>(1.0, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> cm2 = new Quantity<>(2.0, LengthUnit.CENTIMETER);
        assertFalse(cm1.equals(cm2));
    }

    // CROSS-UNIT: YARD ↔ FEET
    @Test
    public void given1YardAnd3Feet_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        assertTrue(yard.equals(feet));
    }

    @Test
    public void given3FeetAnd1Yard_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        assertTrue(feet.equals(yard));
    }

    @Test
    public void given2YardAnd6Feet_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> yard = new Quantity<>(2.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet = new Quantity<>(6.0, LengthUnit.FEET);
        assertTrue(yard.equals(feet));
    }

    // CROSS-UNIT: YARD ↔ INCH
    @Test
    public void given1YardAnd36Inch_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCH);
        assertTrue(yard.equals(inch));
    }

    @Test
    public void given36InchAnd1Yard_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCH);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        assertTrue(inch.equals(yard));
    }

    // CROSS-UNIT: CENTIMETER ↔ INCH
    @Test
    public void given2_54CentimeterAnd1Inch_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> cm = new Quantity<>(2.54, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> inch = new Quantity<>(1.0, LengthUnit.INCH);
        assertTrue(cm.equals(inch));
    }

    @Test
    public void given1InchAnd2_54Centimeter_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> inch = new Quantity<>(1.0, LengthUnit.INCH);
        Quantity<LengthUnit> cm = new Quantity<>(2.54, LengthUnit.CENTIMETER);
        assertTrue(inch.equals(cm));
    }

    // CROSS-UNIT: CENTIMETER ↔ FEET
    @Test
    public void given30_48CentimeterAnd1Feet_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> cm = new Quantity<>(30.48, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(cm.equals(feet));
    }

    // REGRESSION
    @Test
    public void given12InchAnd1Feet_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(inch.equals(feet));
    }

    @Test
    public void given1FeetAnd1Feet_whenCompared_thenShouldReturnTrue() {
        Quantity<LengthUnit> feet1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> feet2 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(feet1.equals(feet2));
    }
}
