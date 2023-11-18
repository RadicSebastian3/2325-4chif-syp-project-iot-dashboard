package at.htl.entity;

import java.math.BigInteger;

public class Unit {
    
    BigInteger id;
    String name;

    public Unit() {
    }

    public Unit(BigInteger id, String name) {
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
}
