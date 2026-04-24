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

        // UC4 Demo
        System.out.println("\n--- UC4 Demo ---");
        QuantityLength yard1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength feet3 = new QuantityLength(3.0, Unit.FEET);
        System.out.println("1.0 YARD == 3.0 FEET : " + yard1.equals(feet3));

        QuantityLength cm2_54 = new QuantityLength(2.54, Unit.CENTIMETER);
        QuantityLength inch1 = new QuantityLength(1.0, Unit.INCH);
        System.out.println("2.54 CENTIMETER \u2248 1.0 INCH : " + cm2_54.equals(inch1));

        QuantityLength yard1_again = new QuantityLength(1.0, Unit.YARD);
        QuantityLength inch36 = new QuantityLength(36.0, Unit.INCH);
        System.out.println("1.0 YARD == 36.0 INCH : " + yard1_again.equals(inch36));

        // UC5 Demo
        System.out.println("\n--- UC5 Demo ---");
        System.out.println("convert(1.0, FEET, INCH) = " + QuantityLength.convert(1.0, Unit.FEET, Unit.INCH));
        System.out.println("convert(1.0, YARD, FEET) = " + QuantityLength.convert(1.0, Unit.YARD, Unit.FEET));
        System.out.println("convert(2.54, CENTIMETER, INCH) \u2248 " + QuantityLength.convert(2.54, Unit.CENTIMETER, Unit.INCH));

        // UC6 Demo
        System.out.println("\n--- UC6 Demo ---");
        QuantityLength feet1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength inch12 = new QuantityLength(12.0, Unit.INCH);
        System.out.println("add(1.0 FEET, 12.0 INCH) = " + QuantityLength.add(feet1, inch12));
        System.out.println("add(12.0 INCH, 1.0 FEET) = " + QuantityLength.add(inch12, feet1));
        
        QuantityLength yard1_uc6 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength feet3_uc6 = new QuantityLength(3.0, Unit.FEET);
        System.out.println("add(1.0 YARD, 3.0 FEET) = " + QuantityLength.add(yard1_uc6, feet3_uc6));

        // UC7 Demo
        System.out.println("\n--- UC7 Demo ---");
        System.out.println("add(1.0 FEET, 12.0 INCH, YARD) \u2248 " + QuantityLength.add(feet1, inch12, Unit.YARD));
        System.out.println("add(1.0 YARD, 3.0 FEET, INCH) = " + QuantityLength.add(yard1_uc6, feet3_uc6, Unit.INCH));
        System.out.println("add(1.0 FEET, 1.0 FEET, CENTIMETER) \u2248 " + QuantityLength.add(feet1, feet1, Unit.CENTIMETER));
    }
}
