package com.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import com.quantitymeasurement.model.Inches;
import com.quantitymeasurement.model.Feet;

public class InchesEqualityTest {

    @Test
    public void givenZeroInches_whenComparedWithZeroInches_shouldReturnTrue() {
        Inches inches1 = new Inches(0.0);
        Inches inches2 = new Inches(0.0);
        assertTrue(inches1.equals(inches2));
    }

    @Test
    public void givenOneInch_whenComparedWithOneInch_shouldReturnTrue() {
        Inches inches1 = new Inches(1.0);
        Inches inches2 = new Inches(1.0);
        assertTrue(inches1.equals(inches2));
    }

    @Test
    public void givenOneInch_whenComparedWithTwoInches_shouldReturnFalse() {
        Inches inches1 = new Inches(1.0);
        Inches inches2 = new Inches(2.0);
        assertFalse(inches1.equals(inches2));
    }

    @Test
    public void givenOneInch_whenComparedWithNull_shouldReturnFalse() {
        Inches inches1 = new Inches(1.0);
        assertFalse(inches1.equals(null));
    }

    @Test
    public void givenNegativeInches_whenComparedWithSameNegativeInches_shouldReturnTrue() {
        Inches inches1 = new Inches(-2.5);
        Inches inches2 = new Inches(-2.5);
        assertTrue(inches1.equals(inches2));
    }

    @Test
    public void givenDifferentPositiveInches_whenCompared_shouldReturnFalse() {
        Inches inches1 = new Inches(4.5);
        Inches inches2 = new Inches(5.5);
        assertFalse(inches1.equals(inches2));
    }

    @Test
    public void givenSameObjectReference_whenCompared_shouldReturnTrue() {
        Inches inches = new Inches(10.0);
        assertTrue(inches.equals(inches));
    }

    @Test
    public void givenFeetEquality_whenReTested_shouldReturnTrue() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);
        assertTrue(feet1.equals(feet2));
    }
    
    @Test
    public void givenNaN_whenInstantiated_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Inches(Double.NaN);
        });
    }
}
