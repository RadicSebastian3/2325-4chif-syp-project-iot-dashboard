package at.htl.leoenergy.influxdb.measurement;

public class BatteryCapacityWhMeasurement extends TimeSeriesMeasurement {
    public BatteryCapacityWhMeasurement(long timestamp, double value) {
        super(timestamp, value, "batteryCapacityWh", "Wh");
    }
}
