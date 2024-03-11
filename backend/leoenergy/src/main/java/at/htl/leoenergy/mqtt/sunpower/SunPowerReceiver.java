package at.htl.leoenergy.mqtt.sunpower;

import at.htl.leoenergy.entity.measurement.*;
import at.htl.leoenergy.influxdb.InfluxDbRepository;
import io.quarkus.logging.Log;
import io.vertx.mqtt.MqttClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class SunPowerReceiver {
   @Inject
    InfluxDbRepository influxDbRepository;

   public void insertMeasurement(TimeSeriesMeasurement timeSeriesMeasurement){
       influxDbRepository.insertMQTTMeasurements(timeSeriesMeasurement);
   }

    @Incoming("sunpower-json")
    public void receive(byte[] byteArray){
        String msg = new String(byteArray);

        /*Dynamic lösung
        insertMeasurement(TimeSeriesMeasurement.fromJson(msg));*/

        //Statische lösung
        //Leider sind die Topics nicht in gleichen Format :-) deswegen kann man nicht so gut ausprobieren
        SunPowerPojo sunPowerPojo = SunPowerPojo.fromJson(msg);
        // the time is in gmt that why you have to minus 1 hour to get right time
        long timestamp = TimeUnit.MILLISECONDS.toSeconds(sunPowerPojo.getTimeStamp().getTime() - TimeUnit.HOURS.toMillis(1));
        String relationPower = "ConsumtionAll";
        String relation = "kwH";

        TimeSeriesMeasurement consumption = new ConsumptionMeasurement(timestamp, sunPowerPojo.getConsumptionW(), "Consumption_W",relationPower);
        TimeSeriesMeasurement gridFeedIn = new GridFeedInMeasurement(timestamp, sunPowerPojo.getGridFeedInW(),relationPower);
        TimeSeriesMeasurement production = new ProductionMeasurement(timestamp, sunPowerPojo.getProductionW(), "Production_W",relationPower);
        TimeSeriesMeasurement batteryCapacityWh = new BatteryCapacityWhMeasurement(timestamp, sunPowerPojo.getBattRemainingCapacityWh(),relation);
        TimeSeriesMeasurement batteryCapacityPercent = new BatteryCapacityPercentMeasurement(timestamp, sunPowerPojo.getBattRemainingCapacityPercent(),relationPower);

        influxDbRepository.insertMQTTMeasurements(consumption);
        influxDbRepository.insertMQTTMeasurements(gridFeedIn);
        influxDbRepository.insertMQTTMeasurements(production);
        influxDbRepository.insertMQTTMeasurements(batteryCapacityWh);
        influxDbRepository.insertMQTTMeasurements(batteryCapacityPercent);

    }

}
