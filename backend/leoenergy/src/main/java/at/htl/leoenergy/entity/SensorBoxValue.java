package at.htl.leoenergy.entity;

public class SensorBoxValue {
    private String floor;
    private double value;
    private String room;
    private String parameter;
    private long time;

    public SensorBoxValue() {
    }

    public SensorBoxValue(String floor, double value, String room, String parameter, long time) {
        this.floor = floor;
        this.value = value;
        this.room = room;
        this.parameter = parameter;
        this.time = time;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format(
                "SensorBoxValue { Floor: %s, Value: %.2f, Room: %s, Parameter: %s, Time: %d }",
                floor, value, room, parameter, time
        );
    }

}
