package at.htl.leoenergy.influxdb.measurement;

public class GridFeedInMeasurement extends TimeSeriesMeasurement{
    public GridFeedInMeasurement(long timestamp, double value) {
        super(timestamp, value, "gridFeedIn", "W");
    }
}
