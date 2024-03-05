package at.htl.leoenergy.entity.measurement;

public class ProductionMeasurement extends TimeSeriesMeasurement{
    public ProductionMeasurement(long timestamp, double value, String name,String relation) {
        super(timestamp, value, name, "W",relation);
    }
}
