package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.entity.SensorValue;

import java.math.BigDecimal;

public class UnitConverter {
    public static double convertToKilowattAndSetRelation(SensorValue sensorValue) {
        double result;

        switch (sensorValue.getUnit()) {
            case "W":
                result = sensorValue.getValue() / 1000.0;
                sensorValue.setRelation("consumtion w");
                break;
            case "Wh":
                result = sensorValue.getValue() / 1000.0;
                sensorValue.setRelation("consumtion wh");
                break;
            case "A":
            case "Bin":
            default:
                result = 0.00;
        }

        return result;
    }
}