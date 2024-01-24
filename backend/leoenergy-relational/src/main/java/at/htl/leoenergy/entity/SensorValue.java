package at.htl.leoenergy.entity;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.math.BigInteger;
import java.time.Instant;

@Measurement(name = "sensor_values")
public class SensorValue {
    @Column(tag = true)
    private BigInteger id;

    private long deviceId;

    @Column(tag = true)
    private Long measurementId;

    private long time;

    @Column
    private double value;

    @Column(timestamp = true)
    Instant timeInstance;



    public SensorValue() {
    }


    public SensorValue(long deviceId, long time, double value, Long measurementId) {
        this.measurementId = measurementId;
        this.time = time;
        this.value = value;
        this.deviceId = deviceId;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public BigInteger getId() {
        return id;
    }

    public Long getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Long measurementId) {
        this.measurementId = measurementId;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }


    public void setId(BigInteger id) {
        this.id = id;
    }
}
