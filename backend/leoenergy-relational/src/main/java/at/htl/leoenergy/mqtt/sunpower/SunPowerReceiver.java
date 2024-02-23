package at.htl.leoenergy.mqtt.sunpower;

import at.htl.leoenergy.influxdb.InfluxDBService;
import at.htl.leoenergy.influxdb.measurement.*;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class SunPowerReceiver {
    @Inject
    InfluxDBService influxDBService;

    @Incoming("sunpower-json")
    public void receive(byte[] byteArray){
        String msg = new String(byteArray);
        SunPowerPojo sunPowerPojo = SunPowerPojo.fromJson(msg);
        // DON'T REMOVE! required for UNIX timestamp converting, just ignore or accept it!!!
        sunPowerPojo.setTimeStamp(new Date(sunPowerPojo.getTimeStamp().getTime() - 60 * 60 * 1000));

        //TODO: handle the next steps for the SunPowerPojo (insert to db, etc...)
        long timestamp = sunPowerPojo.getTimeStamp().getTime() - TimeUnit.HOURS.toMillis(1);
        TimeSeriesMeasurement consumption = new ConsumptionMeasurement(timestamp, sunPowerPojo.getConsumptionW(), "Consumption_W");
        TimeSeriesMeasurement gridFeedIn = new GridFeedInMeasurement(timestamp, sunPowerPojo.getGridFeedInW());
        TimeSeriesMeasurement production = new ProductionMeasurement(timestamp, sunPowerPojo.getProductionW(), "Production_W");
        TimeSeriesMeasurement batteryCapacityWh = new BatteryCapacityWhMeasurement(timestamp, sunPowerPojo.getBattRemainingCapacityWh());
        TimeSeriesMeasurement batteryCapacityPercent = new BatteryCapacityPercentMeasurement(timestamp, sunPowerPojo.getBattRemainingCapacityPercent());

        // write to InfluxDB
        influxDBService.writeToInflux(consumption);
        influxDBService.writeToInflux(gridFeedIn);
        influxDBService.writeToInflux(production);
        influxDBService.writeToInflux(batteryCapacityWh);
        influxDBService.writeToInflux(batteryCapacityPercent);
    }
}
