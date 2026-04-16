package com.quantitymeasurement;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import com.quantitymeasurement.model.Feet;

public class FeetEqualityTest {

    @Test
    public void givenZeroFeet_whenComparedWithZeroFeet_shouldReturnTrue() {
        Feet feet1 = new Feet(0.0);
        Feet feet2 = new Feet(0.0);
        assertTrue(feet1.equals(feet2));
    }

    @Test
    public void givenOneFeet_whenComparedWithOneFeet_shouldReturnTrue() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);
        assertTrue(feet1.equals(feet2));
    }

    @Test
    public void givenOneFeet_whenComparedWithTwoFeet_shouldReturnFalse() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(2.0);
        assertFalse(feet1.equals(feet2));
    }

    @Test
    public void givenOneFeet_whenComparedWithNull_shouldReturnFalse() {
        Feet feet1 = new Feet(1.0);
        assertFalse(feet1.equals(null));
    }

    @Test
    public void givenNegativeFeet_whenComparedWithSameNegativeFeet_shouldReturnTrue() {
        Feet feet1 = new Feet(-1.5);
        Feet feet2 = new Feet(-1.5);
        assertTrue(feet1.equals(feet2));
    }

    @Test
    public void givenDifferentPositiveValues_whenCompared_shouldReturnFalse() {
        Feet feet1 = new Feet(3.5);
        Feet feet2 = new Feet(4.2);
        assertFalse(feet1.equals(feet2));
    }

    @Test
    public void givenSameObjectReference_whenCompared_shouldReturnTrue() {
        Feet feet = new Feet(5.0);
        assertTrue(feet.equals(feet));
    }

    @Test
    public void givenNaN_whenInstantiated_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Feet(Double.NaN);
        });
    }
}
