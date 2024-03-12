package at.htl.leoenergy.mqtt;

import at.htl.leoenergy.controller.DeviceRepository;
import at.htl.leoenergy.entity.SensorValue;
import at.htl.leoenergy.influxdb.InfluxDbRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MqttReceiver {
   @Inject
    InfluxDbRepository influxDbRepository;
   @Inject
    DeviceRepository deviceRepository;

   public void insertMeasurement(SensorValue sensorValue){
       influxDbRepository.insertMeasurementFromJSON(sensorValue,deviceRepository.findById(sensorValue.getDeviceId()));
   }

    @Incoming("leoenergy-demo")
    public void receive(byte[] byteArray) {
        String msg = new String(byteArray);

        SensorValue sensorValue = new SensorValue();

        /*Dynamic lösung
        insertMeasurement(TimeSeriesMeasurement.fromJson(msg));*/

    }

}
