package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.mqtt.sunpower.SunPowerPojo;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import com.influxdb.client.domain.WritePrecision;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class InfluxDBService {
    @Inject
    InfluxDBConfig config;

    public void writeToInflux(SunPowerPojo sunPowerPojo){
        try(InfluxDBClient client = InfluxDBClientFactory.create(config.url, config.token.toCharArray(), config.org, config.bucket)){
            try(WriteApi writeApi = client.getWriteApi()){

                Instant timestamp = Instant.ofEpochMilli(sunPowerPojo.getTimeStamp().getTime());

                Point point = Point.measurement("sunpower")
                        .addTag("device", "SunPowerDevice")
                        .addField("Consumption_W", sunPowerPojo.getConsumptionW())
                        .addField("GridFeedIn_W", sunPowerPojo.getGridFeedInW())
                        .addField("Production_W", sunPowerPojo.getProductionW())
                        .addField("Batt_remaining_Capacity_Wh", sunPowerPojo.getBattRemainingCapacityWh())
                        .addField("Batt_remaining_Capacity_%", sunPowerPojo.getBattRemainingCapacityPercent())
                        .time(timestamp, WritePrecision.NS);

                writeApi.writePoint(point);
            }

        } catch(Exception e){
            Log.error("Failed to write to InfluxDB: " + e.getMessage());
        }
    }
}
