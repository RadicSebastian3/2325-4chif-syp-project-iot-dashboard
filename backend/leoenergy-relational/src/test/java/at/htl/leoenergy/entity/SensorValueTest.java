package at.htl.leoenergy.entity;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class SensorValueTest {
    @Test
    public void givenNewSensorValue_whenUsingDefaultConstructor_thenPropertiesAreDefault() {
        SensorValue sensorValue = new SensorValue();

        SensorValue expectedDefault = new SensorValue();
        expectedDefault.setId(null); // Setzen Sie hier die Standardwerte
        expectedDefault.setDevice(null);
        expectedDefault.setValueId(0);
        expectedDefault.setDescriptionStr(null);
        expectedDefault.setUnitStr(null);
        expectedDefault.setTimestamp(0L);
        expectedDefault.setVal(0.0);

        assertThat(sensorValue).usingRecursiveComparison();
    }

    @Test
    public void givenSensorValueDetails_whenUsingParameterizedConstructor_thenPropertiesAreSet() {
        // Given
        Device device = new Device();
        int valueId = 123;
        String description = "Temperature";
        String unit = "Celsius";
        long timestamp = System.currentTimeMillis();
        double value = 25.5;

        SensorValue expectedSensorValue = new SensorValue(device, valueId, description, unit, timestamp, value);
        SensorValue actualSensorValue = new SensorValue(device, valueId, description, unit, timestamp, value);

        assertThat(actualSensorValue).usingRecursiveComparison().isEqualTo(expectedSensorValue);
    }
}
