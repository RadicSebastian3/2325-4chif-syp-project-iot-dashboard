package at.htl.leoenergy.entity.measurement;

public class BatteryCapacityWhMeasurement extends TimeSeriesMeasurement {
    public BatteryCapacityWhMeasurement(long timestamp, double value, String relation) {
        super(timestamp, value, "batteryCapacityWh", "Wh",relation);
    }
}
