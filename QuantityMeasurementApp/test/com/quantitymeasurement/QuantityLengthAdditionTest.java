package com.quantitymeasurement;

import com.quantitymeasurement.model.QuantityLength;
import com.quantitymeasurement.model.Unit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class QuantityLengthAdditionTest {

    // SAME UNIT ADDITION
    @Test
    public void given1FeetAnd1Feet_whenAdded_shouldReturn2Feet() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength expected = new QuantityLength(2.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, f2);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given12InchAnd12Inch_whenAdded_shouldReturn24Inch() {
        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength i2 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength expected = new QuantityLength(24.0, Unit.INCH);
        QuantityLength result = QuantityLength.add(i1, i2);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given1YardAnd1Yard_whenAdded_shouldReturn2Yard() {
        QuantityLength y1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength y2 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength expected = new QuantityLength(2.0, Unit.YARD);
        QuantityLength result = QuantityLength.add(y1, y2);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    // CROSS-UNIT ADDITION
    @Test
    public void given1FeetAnd12Inch_whenAdded_shouldReturn2Feet() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength expected = new QuantityLength(2.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, i1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given12InchAnd1Feet_whenAdded_shouldReturn24Inch() {
        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength expected = new QuantityLength(24.0, Unit.INCH);
        QuantityLength result = QuantityLength.add(i1, f1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given1YardAnd3Feet_whenAdded_shouldReturn2Yard() {
        QuantityLength y1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength f1 = new QuantityLength(3.0, Unit.FEET);
        QuantityLength expected = new QuantityLength(2.0, Unit.YARD);
        QuantityLength result = QuantityLength.add(y1, f1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given3FeetAnd1Yard_whenAdded_shouldReturn6Feet() {
        QuantityLength f1 = new QuantityLength(3.0, Unit.FEET);
        QuantityLength y1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength expected = new QuantityLength(6.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, y1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given1FeetAnd30_48Centimeter_whenAdded_shouldReturn2Feet() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength c1 = new QuantityLength(30.48, Unit.CENTIMETER);
        QuantityLength expected = new QuantityLength(2.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, c1);
        assertEquals(expected.getValue(), result.getValue(), 0.01);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    @Test
    public void given0FeetAnd12Inch_whenAdded_shouldReturn1Feet() {
        QuantityLength f1 = new QuantityLength(0.0, Unit.FEET);
        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength expected = new QuantityLength(1.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, i1);
        assertEquals(expected.getValue(), result.getValue(), 0.001);
        assertEquals(expected.getUnit(), result.getUnit());
    }

    // VALIDATION
    @Test
    public void givenNullFirstOperand_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.add(null, new QuantityLength(1.0, Unit.FEET)));
    }

    @Test
    public void givenNullSecondOperand_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.add(new QuantityLength(1.0, Unit.FEET), null));
    }

    // EXPLICIT TARGET UNIT ADDITION
    @Test
    public void given1FeetAnd12Inch_whenAddedToYard_shouldReturn0_667Yard() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength i12 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength result = QuantityLength.add(f1, i12, Unit.YARD);
        assertEquals(0.6667, result.getValue(), 0.001);
        assertEquals(Unit.YARD, result.getUnit());
    }

    @Test
    public void given1YardAnd3Feet_whenAddedToInch_shouldReturn72Inch() {
        QuantityLength y1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength f3 = new QuantityLength(3.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(y1, f3, Unit.INCH);
        assertEquals(72.0, result.getValue(), 0.001);
        assertEquals(Unit.INCH, result.getUnit());
    }

    @Test
    public void given1FeetAnd1Feet_whenAddedToInch_shouldReturn24Inch() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, f2, Unit.INCH);
        assertEquals(24.0, result.getValue(), 0.001);
        assertEquals(Unit.INCH, result.getUnit());
    }

    @Test
    public void given12InchAnd12Inch_whenAddedToFeet_shouldReturn2Feet() {
        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength i2 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength result = QuantityLength.add(i1, i2, Unit.FEET);
        assertEquals(2.0, result.getValue(), 0.001);
        assertEquals(Unit.FEET, result.getUnit());
    }

    @Test
    public void given100CentimeterAnd1Feet_whenAddedToInch_shouldReturnExpected() {
        QuantityLength c100 = new QuantityLength(100.0, Unit.CENTIMETER);
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(c100, f1, Unit.INCH);
        // 100 cm = 100 * 0.3937 = 39.37 inch
        // 1 feet = 12 inch
        // 39.37 + 12 = 51.37 inch
        assertEquals(51.37, result.getValue(), 0.01);
        assertEquals(Unit.INCH, result.getUnit());
    }

    @Test
    public void given1FeetAnd1Feet_whenAddedToYard_shouldReturn0_667Yard() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, f2, Unit.YARD);
        assertEquals(0.6667, result.getValue(), 0.001);
        assertEquals(Unit.YARD, result.getUnit());
    }

    @Test
    public void given1FeetAnd1Feet_whenAddedToCentimeter_shouldReturn60_96Centimeter() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength result = QuantityLength.add(f1, f2, Unit.CENTIMETER);
        assertEquals(60.96, result.getValue(), 0.1);
        assertEquals(Unit.CENTIMETER, result.getUnit());
    }

    // EXPLICIT TARGET UNIT VALIDATION
    @Test
    public void givenNullFirstOperandWithTargetUnit_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.add(null, new QuantityLength(1.0, Unit.FEET), Unit.INCH));
    }

    @Test
    public void givenNullSecondOperandWithTargetUnit_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.add(new QuantityLength(1.0, Unit.FEET), null, Unit.INCH));
    }

    @Test
    public void givenNullTargetUnit_whenAdded_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.add(new QuantityLength(1.0, Unit.FEET), new QuantityLength(1.0, Unit.INCH), null));
    }
}
