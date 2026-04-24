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
}
