package at.htl.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DEVICE")
public class NewDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public NewDevice(String name) {
        this.name = name;
    }

    public NewDevice() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "NewDevice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
