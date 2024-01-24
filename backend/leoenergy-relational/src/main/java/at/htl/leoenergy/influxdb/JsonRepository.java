package at.htl.leoenergy.influxdb;

import at.htl.leoenergy.entity.Sensor_Value;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class JsonRepository {
    public static void insertMeasurement(Sensor_Value sensor_value) {
        String token = "qwI8IEYW52ragsR2lZvEKxSXvhOcQ6J4klG0h8JGbJ1Q3-4IEG0WRRCOfCS0Nt70MR83BwmDnLaNx7cYEefpCw==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";
        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            WriteApiBlocking writeApi = client.getWriteApiBlocking();

            long currentTimeInNanoseconds = TimeUnit.SECONDS.toNanos(sensor_value.getTime());

            Point point = Point.measurement("Sensor_Values")
                    .addField("measurement_id",sensor_value.getMeasurementId().toString())
                    .addTag("device_id",String.valueOf(sensor_value.getDeviceId()))
                    .addField("value", sensor_value.getValue())
                    .time(currentTimeInNanoseconds,WritePrecision.NS);

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