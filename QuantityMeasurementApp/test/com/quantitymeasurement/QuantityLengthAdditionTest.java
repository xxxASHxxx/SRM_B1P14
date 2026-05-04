package com.quantitymeasurement;

import com.quantitymeasurement.model.Quantity;
import com.quantitymeasurement.model.LengthUnit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class QuantityLengthAdditionTest {

    // SAME UNIT ADDITION
    @Test
    public void given1FeetAnd1Feet_whenAdded_shouldReturn2Feet() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> expected = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, f2);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given12InchAnd12Inch_whenAdded_shouldReturn24Inch() {
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> i2 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> expected = new Quantity<>(24.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = Quantity.add(i1, i2);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given1YardAnd1Yard_whenAdded_shouldReturn2Yard() {
        Quantity<LengthUnit> y1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> y2 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> expected = new Quantity<>(2.0, LengthUnit.YARD);
        Quantity<LengthUnit> result = Quantity.add(y1, y2);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    // CROSS-UNIT ADDITION
    @Test
    public void given1FeetAnd12Inch_whenAdded_shouldReturn2Feet() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> expected = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, i1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given12InchAnd1Feet_whenAdded_shouldReturn24Inch() {
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> expected = new Quantity<>(24.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = Quantity.add(i1, f1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given1YardAnd3Feet_whenAdded_shouldReturn2Yard() {
        Quantity<LengthUnit> y1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> f1 = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> expected = new Quantity<>(2.0, LengthUnit.YARD);
        Quantity<LengthUnit> result = Quantity.add(y1, f1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given3FeetAnd1Yard_whenAdded_shouldReturn6Feet() {
        Quantity<LengthUnit> f1 = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> y1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> expected = new Quantity<>(6.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, y1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given1FeetAnd30_48Centimeter_whenAdded_shouldReturn2Feet() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> c1 = new Quantity<>(30.48, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> expected = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, c1);
        assertEquals(expected.getValue(), result.getValue(), 0.01);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given0FeetAnd12Inch_whenAdded_shouldReturn1Feet() {
        Quantity<LengthUnit> f1 = new Quantity<>(0.0, LengthUnit.FEET);
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> expected = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, i1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    // VALIDATION
    @Test
    public void givenNullFirstOperand_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.add(null, new Quantity<>(1.0, LengthUnit.FEET)));
    }

    @Test
    public void givenNullSecondOperand_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.add(new Quantity<>(1.0, LengthUnit.FEET), null));
    }

    // EXPLICIT TARGET UNIT ADDITION
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

    @Test
    public void given1FeetAnd1Feet_whenAddedToInch_shouldReturn24Inch() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, f2, LengthUnit.INCH);
        assertEquals(24.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void given12InchAnd12Inch_whenAddedToFeet_shouldReturn2Feet() {
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> i2 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = Quantity.add(i1, i2, LengthUnit.FEET);
        assertEquals(2.0, result.getValue(), 0.001);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    public void given100CentimeterAnd1Feet_whenAddedToInch_shouldReturnExpected() {
        Quantity<LengthUnit> c100 = new Quantity<>(100.0, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(c100, f1, LengthUnit.INCH);
        // 100 cm = 100 * 0.3937 = 39.37 inch
        // 1 feet = 12 inch
        // 39.37 + 12 = 51.37 inch
        assertEquals(51.37, result.getValue(), 0.01);
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    public void given1FeetAnd1Feet_whenAddedToYard_shouldReturn0_667Yard() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, f2, LengthUnit.YARD);
        assertEquals(0.6667, result.getValue(), 0.001);
        assertEquals(LengthUnit.YARD, result.getUnit());
    }

    @Test
    public void given1FeetAnd1Feet_whenAddedToCentimeter_shouldReturn60_96Centimeter() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = Quantity.add(f1, f2, LengthUnit.CENTIMETER);
        assertEquals(60.96, result.getValue(), 0.1);
        assertEquals(LengthUnit.CENTIMETER, result.getUnit());
    }

    // EXPLICIT TARGET UNIT VALIDATION
    @Test
    public void givenNullFirstOperandWithTargetUnit_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.add(null, new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCH));
    }

    @Test
    public void givenNullSecondOperandWithTargetUnit_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.add(new Quantity<>(1.0, LengthUnit.FEET), null, LengthUnit.INCH));
    }

    @Test
    public void givenNullTargetUnit_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.add(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(1.0, LengthUnit.INCH), null));
    }
}
