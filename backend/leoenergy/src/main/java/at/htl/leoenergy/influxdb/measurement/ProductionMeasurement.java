package at.htl.leoenergy.influxdb.measurement;

public class ProductionMeasurement extends TimeSeriesMeasurement{
    public ProductionMeasurement(long timestamp, double value, String name) {
        super(timestamp, value, name, "W");
    }
}
