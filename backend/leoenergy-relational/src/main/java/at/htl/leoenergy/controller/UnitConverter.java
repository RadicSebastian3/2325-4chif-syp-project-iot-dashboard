package at.htl.leoenergy.controller;

import java.math.BigDecimal;

public class UnitConverter {
    public static Double convertToKilowatt(String unit, Double value) {
        Double result;

        switch (unit) {
            case "W":
                result = value / 1000.0;
                break;
            case "Wh":
                result = value / 1000.0;
                break;
            case "m^3/h":
                result = value * 0.000277778;
                break;
            case "A":
            case "Bin":
            default:
                result = Double.valueOf(0.00);
        }

        return result;
    }
}
