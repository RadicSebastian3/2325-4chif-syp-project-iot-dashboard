package at.htl.leoenergy.influxdb.measurement;

public class ConsumptionMeasurement extends TimeSeriesMeasurement {
    public ConsumptionMeasurement(long timestamp, double value, String name) {
        super(timestamp, value, name, "W");
    }
}
