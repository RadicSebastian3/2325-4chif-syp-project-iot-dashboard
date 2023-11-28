package at.htl.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class old_Unit {
    private BigInteger id;
    private String name;

    public old_Unit() {
    }

    public old_Unit(BigInteger id, String name) {
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
        return "Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
