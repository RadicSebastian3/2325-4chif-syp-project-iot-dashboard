package at.htl.leoenergy.influxdb.measurement;

public class BatteryCapacityPercentMeasurement extends TimeSeriesMeasurement{
    public BatteryCapacityPercentMeasurement(long timestamp, double value) {
        super(timestamp, value, "batteryCapacityPercent", "%");
    }
}
