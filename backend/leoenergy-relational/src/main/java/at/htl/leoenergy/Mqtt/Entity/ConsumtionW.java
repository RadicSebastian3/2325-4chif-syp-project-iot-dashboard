package at.htl.leoenergy.Mqtt.Entity;

public class ConsumtionW {
   private long time;
   private final String unit = "W";

   private double value;

    public ConsumtionW(long time, double value) {
        this.time = time;
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
