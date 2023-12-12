package at.htl.leoenergy.entity;

import com.influxdb.annotations.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

@Table(name = "MEASUREMENT")
public class Measurement_Table {
    @Id
    private BigInteger id;

    private BigInteger measurementId;

    private long time;

    private BigDecimal value;

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

    @Override
    public String toString() {
        return "Measurement_Table{" +
                "id=" + id +
                ", measurementId=" + measurementId +
                ", time=" + time +
                ", value=" + value +
                ", timeInstance=" + timeInstance +
                '}';
    }
}
