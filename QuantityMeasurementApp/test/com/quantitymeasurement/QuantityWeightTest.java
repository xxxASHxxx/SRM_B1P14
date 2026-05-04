package com.quantitymeasurement;

import com.quantitymeasurement.model.QuantityWeight;
import com.quantitymeasurement.model.WeightUnit;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityWeightTest {

    @Test
    public void given0KilogramAnd0Kilogram_ShouldReturnEqual() {
        QuantityWeight kg1 = new QuantityWeight(0.0, WeightUnit.KILOGRAM);
        QuantityWeight kg2 = new QuantityWeight(0.0, WeightUnit.KILOGRAM);
        assertEquals(kg1, kg2);
    }

    @Test
    public void given1KilogramAnd1000Grams_ShouldReturnEqual() {
        QuantityWeight kg1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g1000 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        assertEquals(kg1, g1000);
    }

    @Test
    public void given1PoundAndGrams_ShouldReturnEqual() {
        QuantityWeight lb1 = new QuantityWeight(1.0, WeightUnit.POUND);
        QuantityWeight g453_592 = new QuantityWeight(453.592, WeightUnit.GRAM);
        assertEquals(lb1, g453_592);
    }

    @Test
    public void givenWeights_WhenAdded_ShouldReturnSum() {
        QuantityWeight kg1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g1000 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight result = QuantityWeight.add(kg1, g1000);
        assertEquals(new QuantityWeight(2.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void givenWeight_WhenConverted_ShouldReturnNewConvertedQuantity() {
        QuantityWeight kg1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight inGrams = kg1.convertTo(WeightUnit.GRAM);
        assertEquals(new QuantityWeight(1000.0, WeightUnit.GRAM), inGrams);
    }
}
