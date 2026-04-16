package com.quantitymeasurement.app;

import com.quantitymeasurement.model.QuantityLength;
import com.quantitymeasurement.model.Unit;

public class QuantityMeasurementApp {

    public static void main(String[] args) {
        System.out.println("=== Quantity Measurement App ===");

        // UC3 Demo
        QuantityLength f1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength f2 = new QuantityLength(1.0, Unit.FEET);
        System.out.println("1.0 FEET == 1.0 FEET : " + f1.equals(f2));

        QuantityLength i1 = new QuantityLength(12.0, Unit.INCH);
        System.out.println("12.0 INCH == 1.0 FEET : " + i1.equals(f1));

        QuantityLength f3 = new QuantityLength(2.0, Unit.FEET);
        System.out.println("1.0 FEET != 2.0 FEET : " + (!f1.equals(f3)));
    }
}
