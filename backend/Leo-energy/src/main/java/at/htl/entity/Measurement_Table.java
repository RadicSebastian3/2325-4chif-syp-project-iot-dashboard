package at.htl.entity;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public class Measurement_Table {

    private BigInteger id;

    private long time;


    private BigDecimal value;

    private at.htl.entity.Measurement measurement;

    public Measurement_Table() {
    }

    public Measurement_Table(BigInteger id, long time, BigDecimal value, at.htl.entity.Measurement measurement) {
        this.id = id;
        this.time = time;
        this.value = value;
        this.measurement = measurement;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public at.htl.entity.Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(at.htl.entity.Measurement measurement) {
        this.measurement = measurement;
    }
}
