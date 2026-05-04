package com.quantitymeasurement;

import com.quantitymeasurement.model.Quantity;
import com.quantitymeasurement.model.VolumeUnit;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityVolumeTest {

    @Test
    public void given0LitreAnd0Litre_ShouldReturnEqual() {
        Quantity<VolumeUnit> l1 = new Quantity<>(0.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> l2 = new Quantity<>(0.0, VolumeUnit.LITRE);
        assertEquals(l1, l2);
    }

    @Test
    public void given1LitreAnd1000Millilitres_ShouldReturnEqual() {
        Quantity<VolumeUnit> l1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml1000 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(l1, ml1000);
    }

    @Test
    public void given1GallonAndLitres_ShouldReturnEqual() {
        Quantity<VolumeUnit> gal1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> l3_78541 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        assertEquals(gal1, l3_78541);
    }

    @Test
    public void givenVolumes_WhenAdded_ShouldReturnSum() {
        Quantity<VolumeUnit> l1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml1000 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> result = Quantity.add(l1, ml1000);
        assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), result);
    }

    @Test
    public void givenVolume_WhenConverted_ShouldReturnNewConvertedQuantity() {
        Quantity<VolumeUnit> l1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> inMl = l1.convertTo(VolumeUnit.MILLILITRE);
        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), inMl);
    }
}
