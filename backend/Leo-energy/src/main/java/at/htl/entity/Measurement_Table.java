package at.htl.entity;

import java.time.Instant;
import java.util.List;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

@Measurement(name = "Measurement_Device")
public class Measurement_Table {
    @Column(tag = true)
    BigInteger id; //tag
    @Column(timestamp = true)
    Timestamp timestamp;
    @Column
    BigDecimal value;
    @Column
    at.htl.entity.Measurement measurement;

    public Measurement_Table() {
    }

    public Measurement_Table(BigInteger id, Timestamp timestamp, BigDecimal value, at.htl.entity.Measurement measurement) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
        this.measurement = measurement;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
