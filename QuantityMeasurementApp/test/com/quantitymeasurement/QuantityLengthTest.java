package com.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import com.quantitymeasurement.model.Quantity;
import com.quantitymeasurement.model.LengthUnit;


public class QuantityLengthTest {

    // FEET EQUALITY (regression from UC1)
    @Test
    public void givenZeroFeet_whenComparedWithZeroFeet_shouldReturnTrue() {
        Quantity<LengthUnit> f1 = new Quantity<>(0.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(0.0, LengthUnit.FEET);
        assertTrue(f1.equals(f2));
    }

    @Test
    public void givenOneFeet_whenComparedWithOneFeet_shouldReturnTrue() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(f1.equals(f2));
    }

    @Test
    public void givenOneFeet_whenComparedWithTwoFeet_shouldReturnFalse() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(2.0, LengthUnit.FEET);
        assertFalse(f1.equals(f2));
    }

    @Test
    public void givenSameObjectReferenceFeet_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(f1.equals(f1));
    }

    @Test
    public void givenNegativeFeet_whenComparedWithSameNegativeFeet_shouldReturnTrue() {
        Quantity<LengthUnit> f1 = new Quantity<>(-1.5, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(-1.5, LengthUnit.FEET);
        assertTrue(f1.equals(f2));
    }

    // INCHES EQUALITY (regression from UC2)
    @Test
    public void givenZeroInches_whenComparedWithZeroInches_shouldReturnTrue() {
        Quantity<LengthUnit> i1 = new Quantity<>(0.0, LengthUnit.INCH);
        Quantity<LengthUnit> i2 = new Quantity<>(0.0, LengthUnit.INCH);
        assertTrue(i1.equals(i2));
    }

    @Test
    public void givenOneInch_whenComparedWithOneInch_shouldReturnTrue() {
        Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCH);
        Quantity<LengthUnit> i2 = new Quantity<>(1.0, LengthUnit.INCH);
        assertTrue(i1.equals(i2));
    }

    @Test
    public void givenOneInch_whenComparedWithTwoInches_shouldReturnFalse() {
        Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCH);
        Quantity<LengthUnit> i2 = new Quantity<>(2.0, LengthUnit.INCH);
        assertFalse(i1.equals(i2));
    }

    @Test
    public void givenSameObjectReferenceInch_whenCompared_shouldReturnTrue() {
        Quantity<LengthUnit> i1 = new Quantity<>(1.0, LengthUnit.INCH);
        assertTrue(i1.equals(i1));
    }

    // CROSS-UNIT EQUALITY (new in UC3)
    @Test
    public void givenTwelveInches_whenComparedWithOneFoot_shouldReturnTrue() {
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(i1.equals(f1));
    }

    @Test
    public void givenOneFoot_whenComparedWithTwelveInches_shouldReturnTrue() {
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        assertTrue(f1.equals(i1));
    }

    @Test
    public void givenSixInches_whenComparedWithOneFoot_shouldReturnFalse() {
        Quantity<LengthUnit> i1 = new Quantity<>(6.0, LengthUnit.INCH);
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(i1.equals(f1));
    }

    @Test
    public void givenTwentyFourInches_whenComparedWithTwoFeet_shouldReturnTrue() {
        Quantity<LengthUnit> i1 = new Quantity<>(24.0, LengthUnit.INCH);
        Quantity<LengthUnit> f1 = new Quantity<>(2.0, LengthUnit.FEET);
        assertTrue(i1.equals(f1));
    }

    // INVALID INPUT
    @Test
    public void givenNaN_whenInstantiated_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(Double.NaN, LengthUnit.FEET);
        });
    }

    @Test
    public void givenNullUnit_whenInstantiated_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(1.0, null);
        });
    }
}
