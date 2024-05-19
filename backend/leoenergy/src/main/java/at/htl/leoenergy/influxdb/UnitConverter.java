package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.entity.SensorValue;

import java.math.BigDecimal;

public class UnitConverter {
    public static void convertToKilowattAndSetRelation(SensorValue sensorValue) {
        switch (sensorValue.getUnit()) {
            case "W":
                if(sensorValue.getDeviceName().equals("PV-Energie"))
                    sensorValue.setRelation("generated_W");
                else {
                    sensorValue.setRelation("consumption_W");
                }
                break;
            case "Wh":
                if(sensorValue.getDeviceName().equals("PV-Energie"))
                sensorValue.setRelation("generated_Wh");
                else {
                    sensorValue.setRelation("consumption_Wh");
                }
                break;
        }
    }
}