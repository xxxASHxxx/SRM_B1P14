package com.quantitymeasurement;

import com.quantitymeasurement.model.Quantity;
import com.quantitymeasurement.model.LengthUnit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class QuantityLengthConversionTest {

    // BASIC CONVERSIONS
    @Test
    public void given1Feet_whenConvertedToInch_shouldReturn12() {
        assertEquals(12.0, Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.INCH), 0.001);
    }

    @Test
    public void given12Inch_whenConvertedToFeet_shouldReturn1() {
        assertEquals(1.0, Quantity.convert(12.0, LengthUnit.INCH, LengthUnit.FEET), 0.001);
    }

    @Test
    public void given1Yard_whenConvertedToFeet_shouldReturn3() {
        assertEquals(3.0, Quantity.convert(1.0, LengthUnit.YARD, LengthUnit.FEET), 0.001);
    }

    @Test
    public void given3Feet_whenConvertedToYard_shouldReturn1() {
        assertEquals(1.0, Quantity.convert(3.0, LengthUnit.FEET, LengthUnit.YARD), 0.001);
    }

    @Test
    public void given1Yard_whenConvertedToInch_shouldReturn36() {
        assertEquals(36.0, Quantity.convert(1.0, LengthUnit.YARD, LengthUnit.INCH), 0.001);
    }

    @Test
    public void given36Inch_whenConvertedToYard_shouldReturn1() {
        assertEquals(1.0, Quantity.convert(36.0, LengthUnit.INCH, LengthUnit.YARD), 0.001);
    }

    @Test
    public void given2_54Centimeter_whenConvertedToInch_shouldReturn1() {
        assertEquals(1.0, Quantity.convert(2.54, LengthUnit.CENTIMETER, LengthUnit.INCH), 0.001);
    }

    @Test
    public void given1Inch_whenConvertedToCentimeter_shouldReturn2_54() {
        assertEquals(2.54, Quantity.convert(1.0, LengthUnit.INCH, LengthUnit.CENTIMETER), 0.001);
    }

    @Test
    public void given30_48Centimeter_whenConvertedToFeet_shouldReturn1() {
        assertEquals(1.0, Quantity.convert(30.48, LengthUnit.CENTIMETER, LengthUnit.FEET), 0.001);
    }

    @Test
    public void given1Feet_whenConvertedToCentimeter_shouldReturn30_48() {
        assertEquals(30.48, Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.CENTIMETER), 0.01);
    }

    // SAME UNIT (identity)
    @Test
    public void given5Feet_whenConvertedToFeet_shouldReturn5() {
        assertEquals(5.0, Quantity.convert(5.0, LengthUnit.FEET, LengthUnit.FEET), 0.001);
    }

    @Test
    public void given10Inch_whenConvertedToInch_shouldReturn10() {
        assertEquals(10.0, Quantity.convert(10.0, LengthUnit.INCH, LengthUnit.INCH), 0.001);
    }

    // INVALID INPUT
    @Test
    public void givenNaNValue_whenConverted_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH));
    }

    @Test
    public void givenNullSourceUnit_whenConverted_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.convert(1.0, null, LengthUnit.INCH));
    }

    @Test
    public void givenNullTargetUnit_whenConverted_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.convert(1.0, LengthUnit.FEET, null));
    }
}
