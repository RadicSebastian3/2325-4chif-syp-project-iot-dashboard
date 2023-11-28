package at.htl.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MEASUREMENT")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double valueType;

    public Measurement(String name, double valueType) {
        this.name = name;
        this.valueType = valueType;
    }

    public Measurement() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValueType() {
        return valueType;
    }

    public void setValueType(double valueType) {
        this.valueType = valueType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
