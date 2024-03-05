package at.htl.leoenergy.entity.measurement;

public class BatteryCapacityPercentMeasurement extends TimeSeriesMeasurement{
    public BatteryCapacityPercentMeasurement(long timestamp, double value,String relation) {
        super(timestamp, value, "batteryCapacityPercent", "%",relation);
    }
}
