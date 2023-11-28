package at.htl.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

public class old_Value {

    private BigInteger id;
    private Timestamp time;
    private BigInteger value;
    private Device device;
    private old_ValueType valueType;

    public old_Value() {
    }

    public old_Value(BigInteger id, Timestamp time, BigInteger value, Device device, old_ValueType valueType) {
        this.id = id;
        this.time = time;
        this.value = value;
        this.device = device;
        this.valueType = valueType;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public old_ValueType getValueType() {
        return valueType;
    }

    public void setValueType(old_ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", time=" + time +
                ", value=" + value +
                ", device=" + device +
                ", valueType=" + valueType +
                '}';
    }
}
