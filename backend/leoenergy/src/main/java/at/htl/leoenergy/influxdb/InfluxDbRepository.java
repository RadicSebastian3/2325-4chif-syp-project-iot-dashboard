package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.entity.SensorValue;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.concurrent.TimeUnit;
@ApplicationScoped
public class InfluxDbRepository {
    @ConfigProperty(name = "influxdb.url")
    String influxUrl;


    @ConfigProperty(name = "influxdb.token")
    String token;

    @ConfigProperty(name = "influxdb.org")
    String org;

    @ConfigProperty(name = "influxdb.bucket")
    String bucket;

    public  void insertMeasurementFromJSON(SensorValue sensorValue) {

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            WriteApiBlocking writeApi = client.getWriteApiBlocking();


            long currentTimeInNanoseconds = TimeUnit.SECONDS.toNanos(sensorValue.getTime());

            Point point = Point.measurement("Sensor_Values")
                    .addTag("device_name",sensorValue.getDeviceName())
                    .addTag("measurement_id",String.valueOf(sensorValue.getMeasurementId()))
                    .addField("value", sensorValue.getValue())
                    .addTag("relation",sensorValue.getRelation())
                    .addTag("unit",sensorValue.getUnit())
                    .addField("description",sensorValue.getDescription())
                    .time(currentTimeInNanoseconds,WritePrecision.NS);

            writeApi.writePoint(bucket, org, point);

            client.close();

        } catch (Exception e) {
            System.err.println("Error writing data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}