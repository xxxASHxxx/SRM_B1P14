package com.quantitymeasurement;

import com.quantitymeasurement.model.LengthUnit;

import com.quantitymeasurement.model.Quantity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class QuantityLengthV2Test {

    // EQUALITY (same unit)
    @Test
    public void given1FeetAnd1Feet_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(f1.equals(f2));
    }

    @Test
    public void given1FeetAnd2Feet_whenCompared_shouldReturnFalse() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(2.0, LengthUnit.FEET);
        assertFalse(f1.equals(f2));
    }

    @Test
    public void given12InchAnd12Inch_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> i2 = new Quantity<>(12.0, LengthUnit.INCH);
        assertTrue(i1.equals(i2));
    }

    // CROSS-UNIT EQUALITY
    @Test
    public void given12InchAnd1Feet_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(i1.equals(f1));
    }

    @Test
    public void given1YardAnd3Feet_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> y1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> f1 = new Quantity<>(3.0, LengthUnit.FEET);
        assertTrue(y1.equals(f1));
    }

    @Test
    public void given1YardAnd36Inch_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> y1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> i1 = new Quantity<>(36.0, LengthUnit.INCH);
        assertTrue(y1.equals(i1));
    }

    @Test
    public void given2_54CentimeterAnd1Inch_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> c1 = new Quantity<>(2.54, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCH);
        assertTrue(c1.equals(i1));
    }

    // CONVERSION via LengthUnit delegation
    @Test
    public void given1Feet_whenConvertedToInch_shouldReturn12() {
        assertEquals(12.0, Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.INCH), 0.001);
    }

    @Test
    public void given1Yard_whenConvertedToFeet_shouldReturn3() {
        assertEquals(3.0, Quantity.convert(1.0, LengthUnit.YARD, LengthUnit.FEET), 0.001);
    }

    @Test
    public void given3Feet_whenConvertedToBase_shouldReturn3() {
        assertEquals(3.0, LengthUnit.FEET.convertToBaseUnit(3.0), 0.001);
    }

    @Test
    public void given12Inch_whenConvertedToBase_shouldReturn1() {
        assertEquals(1.0, LengthUnit.INCH.convertToBaseUnit(12.0), 0.001);
    }

    @Test
    public void given3Base_whenConvertedToYard_shouldReturn1() {
        assertEquals(1.0, LengthUnit.YARD.convertFromBaseUnit(3.0), 0.001);
    }

    @Test
    public void given30_48Centimeter_whenConvertedToBase_shouldReturn1() {
        assertEquals(1.0, LengthUnit.CENTIMETER.convertToBaseUnit(30.48), 0.01);
    }

    // ADDITION (first operand unit)
    @Test
    public void given1FeetAnd12Inch_whenAdded_shouldReturn2Feet() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i12 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = Quantity.add(f1, i12);
        assertEquals(2.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void given12InchAnd1Feet_whenAdded_shouldReturn24Inch() {
        Quantity<LengthUnit> i12 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(i12, f1);
        assertEquals(24.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    // ADDITION (explicit target unit)
    @Test
    public void given1FeetAnd12Inch_whenAddedToYard_shouldReturn0_667Yard() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i12 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = Quantity.add(f1, i12, LengthUnit.YARD);
        assertEquals(0.6667, result.getValue(), 0.001);
        assertEquals(LengthUnit.YARD, result.getUnit());
    }

    @Test
    public void given1YardAnd3Feet_whenAddedToInch_shouldReturn72Inch() {
        Quantity<LengthUnit> y1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> f3 = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(y1, f3, LengthUnit.INCH);
        assertEquals(72.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    // INVALID INPUT
    @Test
    public void givenNaNValue_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    @Test
    public void givenNullUnit_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
    }

    @Test
    public void givenNaNConvert_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH));
    }

    @Test
    public void givenNullOperandAdd_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.add(null, new Quantity<>(1.0, LengthUnit.FEET)));
    }

    @Test
    public void givenNullTargetUnitAdd_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.add(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(1.0, LengthUnit.INCH), null));
    }

    // BACKWARD COMPATIBILITY REGRESSION
    @Test
    public void given0FeetAnd0Feet_shouldReturnTrue() {
        assertTrue(new Quantity<>(0.0, LengthUnit.FEET).equals(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    @Test
    public void givenSameObjectReference_shouldReturnTrue() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(f1.equals(f1));
    }

    @Test
    public void given6InchAnd1Feet_shouldReturnFalse() {
        assertFalse(new Quantity<>(6.0, LengthUnit.INCH).equals(new Quantity<>(1.0, LengthUnit.FEET)));
    }
}
