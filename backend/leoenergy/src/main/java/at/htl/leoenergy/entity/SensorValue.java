package at.htl.leoenergy.entity;

import at.htl.leoenergy.influxdb.UnitConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import io.quarkus.logging.Log;

import java.math.BigInteger;
import java.time.Instant;

@Measurement(name = "sensor_values")
public class SensorValue {
    @Column(tag = true)
    private BigInteger id;

    private long deviceId;
    @Column(tag = true)
    @JsonProperty(namespace = "measurementId")
    private Long measurementId;
    @JsonProperty(namespace = "deviceName")
    private String deviceName;
    @JsonProperty(namespace = "description")
    private String description;
    @JsonProperty(namespace = "unit")
    private String unit;
    @JsonProperty(namespace = "timestamp")
    private long timestamp;

    private String relation;
    @Column
    @JsonProperty(namespace = "value")
    private double value;




    public SensorValue() {
    }


    public SensorValue(long deviceId, long time, double value, Long measurementId, String description,String unit, String relation,String deviceName)  {
        this.measurementId = measurementId;
        this.timestamp = time;
        this.value = value;
        this.deviceId = deviceId;
        this.description = description;
        this.unit = unit;
        this.relation = relation;
    }

    public long getTime() {
        return timestamp;
    }
    public static SensorValue fromJson(String json){
        try {
            SensorValue sensorValue = new ObjectMapper().readValue(json, SensorValue.class);
            UnitConverter.convertToKilowattAndSetRelation(sensorValue);
            return sensorValue;

        } catch (JsonProcessingException e) {
            Log.error("Error during converting json string to SunPowerPojo!");
            e.printStackTrace();
            return null;
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setTime(long time) {
        this.timestamp = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }


    public void setId(BigInteger id) {
        this.id = id;
    }
}