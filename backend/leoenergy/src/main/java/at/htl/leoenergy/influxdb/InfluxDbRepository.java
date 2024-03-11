package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.entity.Device;
import at.htl.leoenergy.entity.SensorValue;
import at.htl.leoenergy.entity.measurement.TimeSeriesMeasurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.Timestamp;
import java.time.Instant;
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

    public  void insertMeasurementFromJSON(SensorValue sensorValue, Device device) {

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            WriteApiBlocking writeApi = client.getWriteApiBlocking();


            long currentTimeInNanoseconds = TimeUnit.SECONDS.toNanos(sensorValue.getTime());

            Point point = Point.measurement("Sensor_Values")
                    .addTag("device_name",device.getName())
                    .addTag("measurement_id",String.valueOf(sensorValue.getMeasurementId()))
                    .addField("value", sensorValue.getValue())
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
    public void insertMQTTMeasurements(TimeSeriesMeasurement measurement) {

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            WriteApiBlocking writeApi = client.getWriteApiBlocking();


            long currentTimeInNanoseconds = TimeUnit.SECONDS.toSeconds(measurement.getTimestamp()) ;

            Point point = Point.measurement("Mqtt-Values")
                    .addTag("name",measurement.getName())
                    .addTag("unit",measurement.getUnit())
                    .addTag("relation",measurement.getRelation())
                    .addField("value", measurement.getValue())
                    .time(currentTimeInNanoseconds,WritePrecision.S);

            writeApi.writePoint(bucket, org, point);

            client.close();

        } catch (Exception e) {
            System.err.println("Error writing data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /*  public static List<Measurement_Table> getValuesBetweenTwoTimeStampsWithTheSameMeasurementId(Timestamp startTime, Timestamp endTime,BigInteger measurementId) {
          String token = "dpWZoPRCcJmjM7CEynlTim-xRjf0Fo7YLav_CgHkl2liY5Xai0hja1-H3HDfOSzCb3HVfHaS_Y7ohT6yzKYGfg==";
          String bucket = "db";
          String org = "Leoenergy";
          String influxUrl = "http://localhost:8086";

          List<Measurement_Table> resultList = new ArrayList<>();

          try (InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray())) {

              long startNano = startTime.toInstant().toEpochMilli() * 1_000_000;
              long endNano = endTime.toInstant().toEpochMilli() * 1_000_000;

              String query = String.format("from(bucket: \"%s\") " +
                              "|> range(start: %d, stop: %d)"+
                              "|> filter(fn: (r) => r[\"_measurement\"] == \"measurement_table\")" +
                              "|> filter(fn: (r) => r[\"_field\"] == \"value\")\n" +
                              "  |> filter(fn: (r) => r[\"measurement_id\"] == \"10\")"
                      , bucket, startNano, endNano);

              QueryApi queryApi = client.getQueryApi();
              List<FluxTable> tables = queryApi.query(query, org);

              for (FluxTable fluxTable : tables) {
                  List<FluxRecord> records = fluxTable.getRecords();
                  for (FluxRecord fluxRecord : records) {
                      Measurement_Table measurementTable = new Measurement_Table();
                      measurementTable.setTimeInstance(fluxRecord.getTime());
                      measurementTable.setValue(new BigDecimal(fluxRecord.getValueByKey("_value").toString()));
                      resultList.add(measurementTable);
                  }
              }

          } catch (Exception e) {
              e.printStackTrace();
          }
          return resultList;
      }*/
    public static void main(String[] args) {

        Instant startInstant = Instant.parse("2023-10-01T00:00:00Z");
        Instant endInstant = Instant.parse("2023-10-01T23:59:59Z");

        Timestamp startTime = Timestamp.from(startInstant);
        Timestamp endTime = Timestamp.from(endInstant);



    }


}