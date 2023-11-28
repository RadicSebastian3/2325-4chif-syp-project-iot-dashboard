package at.htl.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class Measurement_Table {
    BigInteger id; //tag
    Timestamp timestamp;
    BigDecimal value;
    Measurement measurement;

    public Measurement_Table() {
    }

    public Measurement_Table(BigInteger id, Timestamp timestamp, BigDecimal value, Measurement measurement) {
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

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
}
