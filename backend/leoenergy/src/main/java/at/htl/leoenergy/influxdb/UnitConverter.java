package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.entity.SensorValue;

import java.math.BigDecimal;

public class UnitConverter {
    public static void convertToKilowattAndSetRelation(SensorValue sensorValue) {
        double result;

        switch (sensorValue.getUnit()) {
            case "W":

                sensorValue.setValue(sensorValue.getValue() / 1000.0);
                if(sensorValue.getDeviceName().equals("EMU PV-Energie"))
                    sensorValue.setRelation("generated_kW");
                else {
                    sensorValue.setRelation("consumtion_kW");
                }
                break;
            case "Wh":

                sensorValue.setValue(sensorValue.getValue() / 1000.0);
                if(sensorValue.getDeviceName().equals("EMU PV-Energie"))
                sensorValue.setRelation("generated_Wh");
                else {
                    sensorValue.setRelation("consumtion_Wh");
                }
                break;
            case "A":
            case "Bin":
            default:
                result = 0.00;
        }


    }
}