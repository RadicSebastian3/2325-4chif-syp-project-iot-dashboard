package at.htl.influxdb;

import at.htl.entity.Measurement_Table;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JsonToInfluxDB {
    public static void insertMeasurement(Measurement_Table measurementTable) {
        String token = "dpWZoPRCcJmjM7CEynlTim-xRjf0Fo7YLav_CgHkl2liY5Xai0hja1-H3HDfOSzCb3HVfHaS_Y7ohT6yzKYGfg==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";
        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            WriteApiBlocking writeApi = client.getWriteApiBlocking();

          long currentTimeInNanoseconds = TimeUnit.SECONDS.toNanos(measurementTable.getTime());

            Point point = Point.measurement("measurement_table")
                    .addTag("id", measurementTable.getId().toString())
                    .addTag("measurement_id",measurementTable.getMeasurementId().toString())
                    .addField("value", measurementTable.getValue())
                    .time(currentTimeInNanoseconds,WritePrecision.NS);

            writeApi.writePoint(bucket, org, point);

            client.close();

        } catch (Exception e) {
            System.err.println("Error writing data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Measurement_Table> getValuesBetweenTwoTimeStamps(Timestamp startTime, Timestamp endTime) {
        String token = "dpWZoPRCcJmjM7CEynlTim-xRjf0Fo7YLav_CgHkl2liY5Xai0hja1-H3HDfOSzCb3HVfHaS_Y7ohT6yzKYGfg==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        List<Measurement_Table> resultList = new ArrayList<>();

        try (InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray())) {

            long startNano = startTime.toInstant().toEpochMilli() * 1_000_000;
            long endNano = endTime.toInstant().toEpochMilli() * 1_000_000;

            String query = String.format("from(bucket: \"%s\") " +
                                         "|> range(start: %d, stop: %d)", bucket, startNano, endNano);

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

        Timestamp startTime = Timestamp.valueOf("2023-10-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-12-10 00:00:00");

        List<Measurement_Table> result = getValuesBetweenTwoTimeStamps(startTime, endTime);

        System.out.println(result.size());
    }


}
