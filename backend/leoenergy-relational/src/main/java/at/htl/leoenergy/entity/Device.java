package at.htl.leoenergy.entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "DEVICE")
public class Device {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int deviceId;
    private String manufacturerId;
    private String medium;
    private String name;
    private String site;

    //region constructors
    public Device() {
    }

    public Device(int deviceId, String manufacturerId, String medium, String name, String site) {
        this.deviceId = deviceId;
        this.manufacturerId = manufacturerId;
        this.medium = medium;
        this.name = name;
        this.site = site;
    }
    //endregion

    //region getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
    //endregion

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
