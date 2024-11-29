package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.entity.SensorBoxValue;
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

            Point point = Point.measurement("sensor_values")
                    // Beispiel: Name des Geräts, z.B. "PV-Energie"
                    .addTag("device_name", sensorValue.getDeviceName()) // device_name: PV-Energie
                    // Beispiel: ID des Wertetyps, z.B. "56"
                    .addTag("value_type_id", String.valueOf(sensorValue.getValueTypeId())) // value_type_id: 56
                    // Beispiel: Typ des gemessenen Werts, z.B. "Energy"
                    .addTag("value_type", String.valueOf(sensorValue.getValueType())) // value_type: Energy
                    // Beispiel: Gemessener Wert, z.B. "7572564"
                    .addField("value", sensorValue.getValue()) // value: 7572564
                    // Beispiel: Beziehung/Relation des Werts, z.B. "consumption/production"
                    .addTag("relation", sensorValue.getRelation()) // relation: generated
                    // Beispiel: Einheit des gemessenen Werts, z.B. "Wh"
                    .addTag("unit", sensorValue.getUnit()) // unit: Wh
                    // Beispiel: Standort des Geräts, z.B. "PV-Raum-Keller"
                    .addTag("site", sensorValue.getSite()) // site: PV-Raum-Keller
                    // Zeitstempel des Werts (in Millisekunden), z.B. 1732873810000
                    .time(currentTimeInNanoseconds, WritePrecision.MS);



            writeApi.writePoint(bucket, org, point);

            client.close();

        } catch (Exception e) {
            System.err.println("Error writing SensorValue data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void insertSensorBoxMeasurement(SensorBoxValue sensorBox) {

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            WriteApiBlocking writeApi = client.getWriteApiBlocking();


            long currentTimeInNanoseconds = TimeUnit.MILLISECONDS.toMillis(sensorBox.getTime());

            Point point = Point.measurement("sensor_box")
                    .addTag("room", sensorBox.getRoom()) // Room: e72
                    .addTag("parameter", sensorBox.getParameter()) // Parameter: humidity// Beispiel: Stockwerk, z.B. "eg" (Erdgeschoss)
                    .addTag("floor", sensorBox.getFloor()) // Floor: eg
                    .addField("value", sensorBox.getValue()) // Value: 33.98
                    // Zeitstempel des Werts (in Millisekunden), z.B. 1732874651000 (nach Konvertierung)
                    .time(currentTimeInNanoseconds, WritePrecision.MS);


            writeApi.writePoint(bucket, org, point);
            client.close();

        } catch (Exception e) {
            System.err.println("Error writing CO2 data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}