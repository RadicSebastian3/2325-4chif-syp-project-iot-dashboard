package at.htl.leoenergy.entity.measurement;

import at.htl.leoenergy.influxdb.UnitConverter;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

public class TimeSeriesMeasurement {

    private String relation;
    private long timestamp;
    private double value;
    private String name;
    private String unit;

    public TimeSeriesMeasurement(long timestamp, double value, String name, String unit,String relation) {
        this.timestamp = timestamp;
        this.value = UnitConverter.convertToKilowatt(unit,value);
        this.name = name;
        this.unit = unit;
        this.relation = relation;
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp * 1000000 +
                '}';
    }
}
