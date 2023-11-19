package at.htl.entity;

import java.math.BigInteger;
import java.util.List;

public class Device {
    BigInteger id;
    String displayName;

    String name;

    String site;

    public Device() {
    }

    public Device(BigInteger id, String displayName, String name, String site) {
        this.id = id;
        this.displayName = displayName;
        this.name = name;
        this.site = site;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
