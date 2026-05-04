package com.quantitymeasurement.app;

import com.quantitymeasurement.model.Quantity;
import com.quantitymeasurement.model.LengthUnit;


public class QuantityMeasurementApp {

    public static void main(String[] args) {
        System.out.println("=== Quantity Measurement App ===");

        // UC3 Demo
        Quantity<LengthUnit> f1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> f2 = new Quantity<>(1.0, LengthUnit.FEET);
        System.out.println("1.0 FEET == 1.0 FEET : " + f1.equals(f2));

        Quantity<LengthUnit> i1 = new Quantity<>(12.0, LengthUnit.INCH);
        System.out.println("12.0 INCH == 1.0 FEET : " + i1.equals(f1));

        Quantity<LengthUnit> f3 = new Quantity<>(2.0, LengthUnit.FEET);
        System.out.println("1.0 FEET != 2.0 FEET : " + (!f1.equals(f3)));

        // UC4 Demo
        System.out.println("\n--- UC4 Demo ---");
        Quantity<LengthUnit> yard1 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet3 = new Quantity<>(3.0, LengthUnit.FEET);
        System.out.println("1.0 YARD == 3.0 FEET : " + yard1.equals(feet3));

        Quantity<LengthUnit> cm2_54 = new Quantity<>(2.54, LengthUnit.CENTIMETER);
        Quantity<LengthUnit> inch1 = new Quantity<>(1.0, LengthUnit.INCH);
        System.out.println("2.54 CENTIMETER \u2248 1.0 INCH : " + cm2_54.equals(inch1));

        Quantity<LengthUnit> yard1_again = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> inch36 = new Quantity<>(36.0, LengthUnit.INCH);
        System.out.println("1.0 YARD == 36.0 INCH : " + yard1_again.equals(inch36));

        // UC5 Demo
        System.out.println("\n--- UC5 Demo ---");
        System.out.println("convert(1.0, FEET, INCH) = " + Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));
        System.out.println("convert(1.0, YARD, FEET) = " + Quantity.convert(1.0, LengthUnit.YARD, LengthUnit.FEET));
        System.out.println("convert(2.54, CENTIMETER, INCH) \u2248 " + Quantity.convert(2.54, LengthUnit.CENTIMETER, LengthUnit.INCH));

        // UC6 Demo
        System.out.println("\n--- UC6 Demo ---");
        Quantity<LengthUnit> feet1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch12 = new Quantity<>(12.0, LengthUnit.INCH);
        System.out.println("add(1.0 FEET, 12.0 INCH) = " + Quantity.add(feet1, inch12));
        System.out.println("add(12.0 INCH, 1.0 FEET) = " + Quantity.add(inch12, feet1));
        
        Quantity<LengthUnit> yard1_uc6 = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet3_uc6 = new Quantity<>(3.0, LengthUnit.FEET);
        System.out.println("add(1.0 YARD, 3.0 FEET) = " + Quantity.add(yard1_uc6, feet3_uc6));

        // UC7 Demo
        System.out.println("\n--- UC7 Demo ---");
        System.out.println("add(1.0 FEET, 12.0 INCH, YARD) \u2248 " + Quantity.add(feet1, inch12, LengthUnit.YARD));
        System.out.println("add(1.0 YARD, 3.0 FEET, INCH) = " + Quantity.add(yard1_uc6, feet3_uc6, LengthUnit.INCH));
        System.out.println("add(1.0 FEET, 1.0 FEET, CENTIMETER) \u2248 " + Quantity.add(feet1, feet1, LengthUnit.CENTIMETER));

        // UC8 Demo
        System.out.println("\n=== UC8: Refactored LengthUnit Demo ===");
        com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.LengthUnit> f1_v2 = new com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.LengthUnit>(1.0, com.quantitymeasurement.model.LengthUnit.FEET);
        com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.LengthUnit> f2_v2 = new com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.LengthUnit>(1.0, com.quantitymeasurement.model.LengthUnit.FEET);
        System.out.println("Quantity<LengthUnit> demo \u2014 1.0 FEET == 1.0 FEET : " + f1_v2.equals(f2_v2));
        System.out.println("LengthUnit.FEET.convertToBaseUnit(3.0) = " + com.quantitymeasurement.model.LengthUnit.FEET.convertToBaseUnit(3.0));
        System.out.println("LengthUnit.YARD.convertFromBaseUnit(3.0) = " + com.quantitymeasurement.model.LengthUnit.YARD.convertFromBaseUnit(3.0));
        com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.LengthUnit> i12_v2 = new com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.LengthUnit>(12.0, com.quantitymeasurement.model.LengthUnit.INCH);
        System.out.println("add(1.0 FEET, 12.0 INCH, YARD) \u2248 " + com.quantitymeasurement.model.Quantity.add(f1_v2, i12_v2, com.quantitymeasurement.model.LengthUnit.YARD));

        // UC9 Demo
        System.out.println("\n=== UC9: Weight Measurement Demo ===");
        com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.WeightUnit> kg1 = new com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.WeightUnit>(1.0, com.quantitymeasurement.model.WeightUnit.KILOGRAM);
        com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.WeightUnit> g1000 = new com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.WeightUnit>(1000.0, com.quantitymeasurement.model.WeightUnit.GRAM);
        System.out.println("1.0 KILOGRAM == 1000.0 GRAM : " + kg1.equals(g1000));
        
        com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.WeightUnit> lb1 = new com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.WeightUnit>(1.0, com.quantitymeasurement.model.WeightUnit.POUND);
        com.quantitymeasurement.model.Quantity<com.quantitymeasurement.model.WeightUnit> inGrams = lb1.convertTo(com.quantitymeasurement.model.WeightUnit.GRAM);
        System.out.println("1.0 POUND in GRAMS : " + inGrams);

        System.out.println("add(1.0 KILOGRAM, 1000.0 GRAM) = " + com.quantitymeasurement.model.Quantity.add(kg1, g1000));
    }
}
