package at.htlleonding.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;import static org.assertj.core.api.Assertions.assertThat;

class SensorValuePojoTest {
    @Test
    void testConstructorAndGetters() {
        String deviceName = "TestDevice";
        Long measurementId = 1L;
        String description = "Temperature Sensor";
        String unit = "Celsius";
        long time = System.currentTimeMillis();
        double value = 25.0;

        SensorValuePojo sensorValue = new SensorValuePojo(deviceName, measurementId, description, unit, time, value);

        assertThat(sensorValue.getDeviceName()).isEqualTo(deviceName);
        assertThat(sensorValue.getMeasurementId()).isEqualTo(measurementId);
        assertThat(sensorValue.getDescription()).isEqualTo(description);
        assertThat(sensorValue.getUnit()).isEqualTo(unit);
        assertThat(sensorValue.getTime()).isEqualTo(time);
        assertThat(sensorValue.getValue()).isEqualTo(value);
    }

    @Test
    void testSetters() {
        SensorValuePojo sensorValue = new SensorValuePojo();

        String deviceName = "TestDevice";
        Long measurementId = 1L;
        String description = "Temperature Sensor";
        String unit = "Celsius";
        long time = System.currentTimeMillis();
        double value = 25.0;

        sensorValue.setDeviceName(deviceName);
        sensorValue.setMeasurementId(measurementId);
        sensorValue.setDescription(description);
        sensorValue.setUnit(unit);
        sensorValue.setTime(time);
        sensorValue.setValue(value);

        assertThat(sensorValue.getDeviceName()).isEqualTo(deviceName);
        assertThat(sensorValue.getMeasurementId()).isEqualTo(measurementId);
        assertThat(sensorValue.getDescription()).isEqualTo(description);
        assertThat(sensorValue.getUnit()).isEqualTo(unit);
        assertThat(sensorValue.getTime()).isEqualTo(time);
        assertThat(sensorValue.getValue()).isEqualTo(value);
    }

    @Test
    void testToString() {
        String deviceName = "TestDevice";
        Long measurementId = 1L;
        String description = "Temperature Sensor";
        String unit = "Celsius";
        long time = System.currentTimeMillis();
        double value = 25.0;

        SensorValuePojo sensorValue = new SensorValuePojo(deviceName, measurementId, description, unit, time, value);

        String expectedToString = "SensorValuePojo{deviceName='" + deviceName + '\'' +
                ", measurementId=" + measurementId +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", time=" + time +
                ", value=" + value +
                '}';

        assertThat(sensorValue.toString()).isEqualTo(expectedToString);
    }
}