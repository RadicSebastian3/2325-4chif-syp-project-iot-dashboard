package at.htl.entity;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

@Measurement(name = "measurement_table")
public class Measurement_Table {
    @Column(tag = true)
    private BigInteger id;

    @Column(tag = true)
    private BigInteger measurementId;

    private long time;

    @Column
    private BigDecimal value;

    @Column(timestamp = true)
    Instant timeInstance;

    public Measurement_Table() {
    }


    public Measurement_Table(BigInteger id, BigInteger measurementId, long time, BigDecimal value) {
        this.id = id;
        this.measurementId = measurementId;
        this.time = time;
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public Instant getTimeInstance() {
        return timeInstance;
    }

    public void setTimeInstance(Instant timeInstance) {
        this.timeInstance = timeInstance;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigInteger getId() {
        return id;
    }

    public BigInteger getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(BigInteger measurementId) {
        this.measurementId = measurementId;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
