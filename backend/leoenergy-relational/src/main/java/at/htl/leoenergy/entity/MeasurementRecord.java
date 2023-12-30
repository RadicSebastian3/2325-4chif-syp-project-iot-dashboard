package at.htl.leoenergy.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

@Entity
@Table(name = "MEASUREMENT_RECORD")
public class MeasurementRecord {
    @Id
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "measurement_id", referencedColumnName = "id")
    private Measurement measurement;

    private long time;

    private BigDecimal value;

    Instant timeInstance;

    public MeasurementRecord() {
    }


    public MeasurementRecord(BigInteger id, Measurement measurement, long time, BigDecimal value) {
        this.id = id;
        this.measurement = measurement;
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

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Measurement_Table{" +
                "id=" + id +
                ", measurementId=" + measurement +
                ", time=" + time +
                ", value=" + value +
                ", timeInstance=" + timeInstance +
                '}';
    }
}
