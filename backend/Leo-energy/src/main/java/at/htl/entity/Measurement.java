package at.htl.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "MEASUREMENT")
public class Measurement {
    @Id
    private BigInteger id;

    private String name;

    private BigDecimal valueType;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Device device;





    public Measurement() {

    }

    public Measurement(BigInteger id, String name, BigDecimal valueType, Device device) {
        this.id = id;
        this.name = name;
        this.valueType = valueType;
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValueType() {
        return valueType;
    }

    public void setValueType(BigDecimal valueType) {
        this.valueType = valueType;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", valueType=" + valueType +
                '}';
    }
}
