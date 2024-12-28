package at.htl.leoenergy.entity;

public class SensorBoxDTO {
    private String room;
    private String floor;
    private Double co2;
    private Double humidity;
    private Double motion;
    private Double neopixel;
    private Double noise;
    private Double pressure;
    private Double rssi;
    private Double temperature;
    private long timestamp; // Time for the latest reading

    public SensorBoxDTO() {
    }

    public SensorBoxDTO(String room, String floor, long timestamp) {
        this.room = room;
        this.floor = floor;
        this.timestamp = timestamp;
    }

    // Getters and setters for all fields

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Double getCo2() {
        return co2;
    }

    public void setCo2(Double co2) {
        this.co2 = co2;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getMotion() {
        return motion;
    }

    public void setMotion(Double motion) {
        this.motion = motion;
    }

    public Double getNeopixel() {
        return neopixel;
    }

    public void setNeopixel(Double neopixel) {
        this.neopixel = neopixel;
    }

    public Double getNoise() {
        return noise;
    }

    public void setNoise(Double noise) {
        this.noise = noise;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getRssi() {
        return rssi;
    }

    public void setRssi(Double rssi) {
        this.rssi = rssi;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "SensorBoxDTO { Room: %s, Floor: %s, Timestamp: %d, CO2: %.2f, Humidity: %.2f, Motion: %.2f, Noise: %.2f, Pressure: %.2f, RSSI: %.2f, Temperature: %.2f }",
                room, floor, timestamp, co2, humidity, motion, noise, pressure, rssi, temperature
        );
    }

    public static SensorBoxDTO deleteFaultyValues(SensorBoxDTO dto) {
        dto.co2 = dto.co2 >= 0 && dto.co2 <= 1_000_000 ? dto.co2 : Double.NaN;
        dto.humidity = dto.humidity >= 0 && dto.humidity <= 100 ? dto.humidity : Double.NaN;
        dto.noise = dto.noise >= 0 && dto.noise <= 194 ? dto.noise : Double.NaN;
        dto.pressure = dto.pressure >= 0 ? dto.pressure : Double.NaN;
        dto.temperature = dto.temperature >= -273.15 ? dto.temperature : Double.NaN;
        return dto;
    }
}
