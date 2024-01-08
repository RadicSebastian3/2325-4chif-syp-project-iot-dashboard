package at.htl.entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "DEVICE")
public class Device {
    @Id
    private BigInteger id;


    private String name;


    public Device() {

    }

    public Device(BigInteger id, String name) {
        this.id = id;
        this.name = name;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NewDevice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
