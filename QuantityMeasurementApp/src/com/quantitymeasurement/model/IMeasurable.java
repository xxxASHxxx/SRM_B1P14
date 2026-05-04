package com.quantitymeasurement.model;

/**
 * Interface standardizing unit behavior across all measurement categories.
 * Allows generic Quantity classes to operate on any measurement type.
 *
 * @author QuantityMeasurementApp
 * @version UC10
 */
public interface IMeasurable {
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
}
