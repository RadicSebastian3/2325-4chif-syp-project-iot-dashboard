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
        String token = "7EH2IeyCQcEKH4Uhep1gES5JAb7oMBn0nJ6sB1rrkDeyt9Fz53K5-4QjMpkbFW1byFchwLXTftdRZV_uLFHOug==";
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
        String token = "7EH2IeyCQcEKH4Uhep1gES5JAb7oMBn0nJ6sB1rrkDeyt9Fz53K5-4QjMpkbFW1byFchwLXTftdRZV_uLFHOug==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        List<Measurement_Table> resultList = new ArrayList<>();

        try (InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray())) {

            // Konvertiere Timestamps in Nanosekunden
            long startNano = startTime.toInstant().toEpochMilli() * 1_000_000;
            long endNano = endTime.toInstant().toEpochMilli() * 1_000_000;

            // Erstelle die Flux-Abfrage mit dem Zeitbereich
            String query = String.format("from(bucket: \"%s\") |> range(start: %d, stop: %d)", bucket, startNano, endNano);

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
    public static void main(String[] args) {

        Timestamp startTime = Timestamp.valueOf("2023-10-01 18:02:24");
        Timestamp endTime = Timestamp.valueOf("2023-12-09 18:02:24");
        List<Measurement_Table> result = getValuesBetweenTwoTimeStamps(startTime, endTime);

        for (Measurement_Table measurement : result) {
            System.out.println("ID: " + measurement.getId() +
                    ", Time: " + measurement.getTimeInstance() +
                    ", Value: " + measurement.getValue() +
                    "Measurement_ID" + measurement.getMeasurementId());
        }
    }


}
