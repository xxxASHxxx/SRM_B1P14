package com.quantitymeasurement;

import com.quantitymeasurement.model.Quantity;
import com.quantitymeasurement.model.LengthUnit;
import com.quantitymeasurement.model.VolumeUnit;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityArithmeticTest {

    @Test
    public void givenQuantities_WhenSubtracted_ShouldReturnDifference() {
        Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> diff = Quantity.subtract(q1, q2);
        assertEquals(new Quantity<>(3.0, LengthUnit.FEET), diff);
    }

    @Test
    public void givenQuantitiesWithDifferentUnits_WhenSubtracted_ShouldReturnDifferenceInFirstUnit() {
        Quantity<LengthUnit> q3 = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> q4 = new Quantity<>(0.5, LengthUnit.FEET);
        Quantity<LengthUnit> diff2 = Quantity.subtract(q3, q4);
        assertEquals(new Quantity<>(6.0, LengthUnit.INCH), diff2);
    }

    @Test
    public void givenQuantities_WhenDivided_ShouldReturnRatio() {
        Quantity<VolumeUnit> v1 = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(2.0, VolumeUnit.LITRE);
        double ratio = Quantity.divide(v1, v2);
        assertEquals(5.0, ratio, 0.0001);
    }

    @Test
    public void givenQuantitiesWithDifferentUnits_WhenDivided_ShouldReturnRatio() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        double ratio = Quantity.divide(v1, v2);
        assertEquals(2.0, ratio, 0.0001);
    }

    @Test(expected = ArithmeticException.class)
    public void givenQuantities_WhenDividedByZero_ShouldThrowArithmeticException() {
        Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);
        Quantity.divide(q1, q2);
    }
}
