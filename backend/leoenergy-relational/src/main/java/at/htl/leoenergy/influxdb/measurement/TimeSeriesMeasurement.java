package at.htl.leoenergy.influxdb.measurement;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

public class TimeSeriesMeasurement {
    private long timestamp;
    private double value;
    private String name;
    private String unit;

    public TimeSeriesMeasurement(long timestamp, double value, String name, String unit) {
        this.timestamp = timestamp;
        this.value = value;
        this.name = name;
        this.unit = unit;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Point toInfluxDBPoint(){
        return Point.measurement(name)
                .addTag("measurement_name", name)
                .addTag("unit", unit)
                .addField("value", value)
                .time(timestamp * 1000000, WritePrecision.NS);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}
