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


            long currentTimeInNanoseconds = TimeUnit.MILLISECONDS.toMillis(sensorValue.getTime());

            Point point = Point.measurement("Sensor_Values")
                    .addTag("device_name",sensorValue.getDeviceName())
                    .addTag("valueType_id",String.valueOf(sensorValue.getValueTypeId()))
                    .addField("value", sensorValue.getValue())
                    .addTag("type",sensorValue.getRelation())
                    .addTag("unit",sensorValue.getUnit())
                    .addTag("site",sensorValue.getSite())
                    .time(currentTimeInNanoseconds,WritePrecision.MS);

            writeApi.writePoint(bucket, org, point);

            client.close();

        } catch (Exception e) {
            System.err.println("Error writing data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}