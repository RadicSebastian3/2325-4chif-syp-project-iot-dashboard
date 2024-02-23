package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.influxdb.measurement.TimeSeriesMeasurement;
import at.htl.leoenergy.mqtt.sunpower.SunPowerPojo;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.write.Point;
import com.influxdb.client.domain.WritePrecision;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;


import java.time.Instant;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class InfluxDBService {
    @ConfigProperty(name = "influxdb.url")
    String url;

    @ConfigProperty(name = "influxdb.token")
    String token;

    @ConfigProperty(name = "influxdb.org")
    String org;

    @ConfigProperty(name = "influxdb.bucket")
    String bucket;

    public void writeToInflux(TimeSeriesMeasurement measurement){
        try(InfluxDBClient client = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket)){
            try(WriteApi writeApi = client.getWriteApi()){
                Point point = measurement.toInfluxDBPoint();
                writeApi.writePoint(point);
                Log.info("Measurement written to InfluxDB: " + measurement);            }

        } catch(Exception e){
            Log.error("Failed to write to InfluxDB: " + e.getMessage());
        }
    }
}
