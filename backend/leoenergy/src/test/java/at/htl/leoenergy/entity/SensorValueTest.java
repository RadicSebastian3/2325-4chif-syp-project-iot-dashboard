package at.htl.leoenergy.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class SensorValueTest {
    @Test
    public void testFromJson() {
        String json = "{\"id\":1,\"time\":1627312496000,\"deviceName\":\"PV-Energie\",\"site\":\"TestSite\",\"unit\":\"W\",\"valueType\":\"TestType\",\"value\":100.0,\"deviceId\":123,\"relation\":\"TestRelation\",\"valueTypeId\":1}";

        SensorValue sensorValue = SensorValue.fromJson(json);

        assertThat(sensorValue).isNotNull();
        assertThat(sensorValue.getTime()).isEqualTo(1627312496000L);
        assertThat(sensorValue.getDeviceName()).isEqualTo("PV-Energie");
        assertThat(sensorValue.getSite()).isEqualTo("TestSite");
        assertThat(sensorValue.getUnit()).isEqualTo("W");
        assertThat(sensorValue.getValueType()).isEqualTo("TestType");
        assertThat(sensorValue.getValue()).isEqualTo(100.0);
        assertThat(sensorValue.getDeviceId()).isEqualTo(123);
        assertThat(sensorValue.getRelation()).isEqualTo("generated_W");
    }
}
