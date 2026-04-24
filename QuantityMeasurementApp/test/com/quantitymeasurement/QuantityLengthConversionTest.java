package com.quantitymeasurement;

import com.quantitymeasurement.model.QuantityLength;
import com.quantitymeasurement.model.Unit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class QuantityLengthConversionTest {

    // BASIC CONVERSIONS
    @Test
    public void given1Feet_whenConvertedToInch_shouldReturn12() {
        assertEquals(12.0, QuantityLength.convert(1.0, Unit.FEET, Unit.INCH), 0.001);
    }

    @Test
    public void given12Inch_whenConvertedToFeet_shouldReturn1() {
        assertEquals(1.0, QuantityLength.convert(12.0, Unit.INCH, Unit.FEET), 0.001);
    }

    @Test
    public void given1Yard_whenConvertedToFeet_shouldReturn3() {
        assertEquals(3.0, QuantityLength.convert(1.0, Unit.YARD, Unit.FEET), 0.001);
    }

    @Test
    public void given3Feet_whenConvertedToYard_shouldReturn1() {
        assertEquals(1.0, QuantityLength.convert(3.0, Unit.FEET, Unit.YARD), 0.001);
    }

    @Test
    public void given1Yard_whenConvertedToInch_shouldReturn36() {
        assertEquals(36.0, QuantityLength.convert(1.0, Unit.YARD, Unit.INCH), 0.001);
    }

    @Test
    public void given36Inch_whenConvertedToYard_shouldReturn1() {
        assertEquals(1.0, QuantityLength.convert(36.0, Unit.INCH, Unit.YARD), 0.001);
    }

    @Test
    public void given2_54Centimeter_whenConvertedToInch_shouldReturn1() {
        assertEquals(1.0, QuantityLength.convert(2.54, Unit.CENTIMETER, Unit.INCH), 0.001);
    }

    @Test
    public void given1Inch_whenConvertedToCentimeter_shouldReturn2_54() {
        assertEquals(2.54, QuantityLength.convert(1.0, Unit.INCH, Unit.CENTIMETER), 0.001);
    }

    @Test
    public void given30_48Centimeter_whenConvertedToFeet_shouldReturn1() {
        assertEquals(1.0, QuantityLength.convert(30.48, Unit.CENTIMETER, Unit.FEET), 0.001);
    }

    @Test
    public void given1Feet_whenConvertedToCentimeter_shouldReturn30_48() {
        assertEquals(30.48, QuantityLength.convert(1.0, Unit.FEET, Unit.CENTIMETER), 0.01);
    }

    // SAME UNIT (identity)
    @Test
    public void given5Feet_whenConvertedToFeet_shouldReturn5() {
        assertEquals(5.0, QuantityLength.convert(5.0, Unit.FEET, Unit.FEET), 0.001);
    }

    @Test
    public void given10Inch_whenConvertedToInch_shouldReturn10() {
        assertEquals(10.0, QuantityLength.convert(10.0, Unit.INCH, Unit.INCH), 0.001);
    }

    // INVALID INPUT
    @Test
    public void givenNaNValue_whenConverted_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(Double.NaN, Unit.FEET, Unit.INCH));
    }

    @Test
    public void givenNullSourceUnit_whenConverted_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(1.0, null, Unit.INCH));
    }

    @Test
    public void givenNullTargetUnit_whenConverted_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(1.0, Unit.FEET, null));
    }
}
