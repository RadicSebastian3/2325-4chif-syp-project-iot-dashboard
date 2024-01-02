package at.htl.leoenergy.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

@Entity
@Table(name = "SENSOR_VALUE")
public class SensorValue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    private Device device;

    @Column(name = "VALUE_ID")
    private int valueId;

    @Column(name = "DESCRIPTION")
    private String descriptionStr;

    @Column(name = "UNIT")
    private String unitStr;

    private long timestamp;

    @Column(name = "VALUE")
    private double val;

    @Column(name = "IS_TO_PUBLISH")
    private boolean isToPublish;

    public SensorValue() {
    }

    public SensorValue(Device device, int valueId, String descriptionStr, String unitStr, long timestamp, double val) {
        this.device = device;
        this.valueId = valueId;
        this.descriptionStr = descriptionStr;
        this.unitStr = unitStr;
        this.timestamp = timestamp;
        this.val = val;
    }

    //region getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public int getValueId() {
        return valueId;
    }

    public void setValueId(int valueId) {
        this.valueId = valueId;
    }

    public String getDescriptionStr() {
        return descriptionStr;
    }

    public void setDescriptionStr(String descriptionStr) {
        this.descriptionStr = descriptionStr;
    }

    public String getUnitStr() {
        return unitStr;
    }

    public void setUnitStr(String unitStr) {
        this.unitStr = unitStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public boolean isToPublish() {
        return isToPublish;
    }

    public void setToPublish(boolean toPublish) {
        isToPublish = toPublish;
    }

    //endregion

    public LocalDateTime getDateTime() {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId()
        );
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %f", descriptionStr, unitStr, val);
    }
}
