package at.htl.entity;

import java.math.BigInteger;

public class old_ValueType {
    private BigInteger id;
    private old_Unit unit;
    private String description;
    private boolean presistValues;
    
    private boolean sentomqtt;

    public old_ValueType() {
    }

    public old_ValueType(BigInteger id, old_Unit unit, String description, boolean presistValues, boolean sentomqtt) {
        this.id = id;
        this.unit = unit;
        this.description = description;
        this.presistValues = presistValues;
        this.sentomqtt = sentomqtt;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public old_Unit getUnit() {
        return unit;
    }

    public void setUnit(old_Unit unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPresistValues() {
        return presistValues;
    }

    public void setPresistValues(boolean presistValues) {
        this.presistValues = presistValues;
    }

    public boolean isSentomqtt() {
        return sentomqtt;
    }

    public void setSentomqtt(boolean sentomqtt) {
        this.sentomqtt = sentomqtt;
    }

    @Override
    public String toString() {
        return "ValueType{" +
                "id=" + id +
                ", unit=" + unit +
                ", description='" + description + '\'' +
                ", presistValues=" + presistValues +
                ", sentomqtt=" + sentomqtt +
                '}';
    }
}
