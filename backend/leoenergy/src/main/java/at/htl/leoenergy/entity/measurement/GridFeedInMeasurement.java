package at.htl.leoenergy.entity.measurement;

public class GridFeedInMeasurement extends TimeSeriesMeasurement{
    public GridFeedInMeasurement(long timestamp, double value,String relation) {
        super(timestamp, value, "GridFeedIn", "W",relation);
    }
}
