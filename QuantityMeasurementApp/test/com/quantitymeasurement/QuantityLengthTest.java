package com.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import com.quantitymeasurement.model.QuantityLength;
import com.quantitymeasurement.model.Unit;

public class QuantityLengthTest {

    // FEET EQUALITY (regression from UC1)
    @Test
    public void givenZeroFeet_whenComparedWithZeroFeet_shouldReturnTrue() {
        QuantityLength f1 = new QuantityLength(0.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(0.0, Unit.FEET);
        assertTrue(f1.equals(f2));
    }

    @Test
    public void givenOneFeet_whenComparedWithOneFeet_shouldReturnTrue() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(1.0, Unit.FEET);
        assertTrue(f1.equals(f2));
    }

    @Test
    public void givenOneFeet_whenComparedWithTwoFeet_shouldReturnFalse() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(2.0, Unit.FEET);
        assertFalse(f1.equals(f2));
    }

    @Test
    public void givenSameObjectReferenceFeet_whenCompared_shouldReturnTrue() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        assertTrue(f1.equals(f1));
    }

    @Test
    public void givenNegativeFeet_whenComparedWithSameNegativeFeet_shouldReturnTrue() {
        QuantityLength f1 = new QuantityLength(-1.5, Unit.FEET);
        QuantityLength f2 = new QuantityLength(-1.5, Unit.FEET);
        assertTrue(f1.equals(f2));
    }

    // INCHES EQUALITY (regression from UC2)
    @Test
    public void givenZeroInches_whenComparedWithZeroInches_shouldReturnTrue() {
        QuantityLength i1 = new QuantityLength(0.0, Unit.INCH);
        QuantityLength i2 = new QuantityLength(0.0, Unit.INCH);
        assertTrue(i1.equals(i2));
    }

    @Test
    public void givenOneInch_whenComparedWithOneInch_shouldReturnTrue() {
        QuantityLength i1 = new QuantityLength(1.0, Unit.INCH);
        QuantityLength i2 = new QuantityLength(1.0, Unit.INCH);
        assertTrue(i1.equals(i2));
    }

    @Test
    public void givenOneInch_whenComparedWithTwoInches_shouldReturnFalse() {
        QuantityLength i1 = new QuantityLength(1.0, Unit.INCH);
        QuantityLength i2 = new QuantityLength(2.0, Unit.INCH);
        assertFalse(i1.equals(i2));
    }

    @Test
    public void givenSameObjectReferenceInch_whenCompared_shouldReturnTrue() {
        QuantityLength i1 = new QuantityLength(1.0, Unit.INCH);
        assertTrue(i1.equals(i1));
    }

    // CROSS-UNIT EQUALITY (new in UC3)
    @Test
    public void givenTwelveInches_whenComparedWithOneFoot_shouldReturnTrue() {
        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        assertTrue(i1.equals(f1));
    }

    @Test
    public void givenOneFoot_whenComparedWithTwelveInches_shouldReturnTrue() {
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        assertTrue(f1.equals(i1));
    }

    @Test
    public void givenSixInches_whenComparedWithOneFoot_shouldReturnFalse() {
        QuantityLength i1 = new QuantityLength(6.0, Unit.INCH);
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        assertFalse(i1.equals(f1));
    }

    @Test
    public void givenTwentyFourInches_whenComparedWithTwoFeet_shouldReturnTrue() {
        QuantityLength i1 = new QuantityLength(24.0, Unit.INCH);
        QuantityLength f1 = new QuantityLength(2.0, Unit.FEET);
        assertTrue(i1.equals(f1));
    }

    // INVALID INPUT
    @Test
    public void givenNaN_whenInstantiated_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(Double.NaN, Unit.FEET);
        });
    }

    @Test
    public void givenNullUnit_whenInstantiated_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(1.0, null);
        });
    }
}
