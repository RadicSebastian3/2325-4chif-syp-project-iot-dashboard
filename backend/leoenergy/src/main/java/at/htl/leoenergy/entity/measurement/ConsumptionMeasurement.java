package at.htl.leoenergy.entity.measurement;

public class ConsumptionMeasurement extends TimeSeriesMeasurement {
    public ConsumptionMeasurement(long timestamp, double value, String name,String relation) {
        super(timestamp, value, name, "W",relation);
    }
}
