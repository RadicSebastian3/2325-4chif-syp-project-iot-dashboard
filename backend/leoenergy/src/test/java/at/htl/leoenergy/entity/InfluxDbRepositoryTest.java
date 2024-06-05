package at.htl.leoenergy.entity;

import at.htl.leoenergy.influxdb.InfluxDbRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

@QuarkusTest
public class InfluxDbRepositoryTest {
    @Test
    public void testInsertMeasurementFromJSON() {
        InfluxDbRepository influxDbRepository = spy(new InfluxDbRepository());

        SensorValue sensorValue = new SensorValue();
        sensorValue.setTime(System.currentTimeMillis());
        sensorValue.setDeviceName("TestDevice");
        sensorValue.setSite("TestSite");
        sensorValue.setUnit("W");
        sensorValue.setValue(100.0);
        sensorValue.setDeviceId(1);

        doNothing().when(influxDbRepository).insertMeasurementFromJSON(sensorValue);

        influxDbRepository.insertMeasurementFromJSON(sensorValue);

        verify(influxDbRepository, times(1)).insertMeasurementFromJSON(sensorValue);
    }
}
