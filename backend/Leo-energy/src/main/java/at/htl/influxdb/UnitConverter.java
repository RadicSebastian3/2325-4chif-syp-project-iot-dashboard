package at.htl.influxdb;

import java.math.BigDecimal;

public class UnitConverter {
    public static BigDecimal convertToKilowatt(String unit, BigDecimal value) {
        BigDecimal result;

        switch (unit) {
            case "W":
                result = value.divide(BigDecimal.valueOf(1000.0));
                break;
            case "Wh":
                result = value.divide(BigDecimal.valueOf(1000.0));
                break;
            case "A":
            case "Bin":
            default:
                result = new BigDecimal(0);
        }
        return result;
    }
}
