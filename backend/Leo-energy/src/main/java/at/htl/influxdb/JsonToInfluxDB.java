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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JsonToInfluxDB {

    public static void queryAllData() {
        String token = "3-wsjnNOrdM4gOhX-UnBGGV2kqkeUW0DDMU37vjCV1gNDdv9MqaB9JJZOfwN90Y3kOppCi1uP_OaZdnmFPL0Pg==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());

            // Erstelle eine InfluxDB-Abfrage, die alle Daten für bestimmte Felder abruft
            String query = "from(bucket: \"db\") |> range(start: -1h) |> filter(fn: (r) => r._measurement == \"Measurement_Table\")";
            List<FluxTable> tables = client.getQueryApi().query(query, org);

            for (FluxTable table : tables) {
                for (FluxRecord record : table.getRecords()) {
                    System.out.println(record);
                }
            }

            client.close();

        } catch (Exception e) {
            System.err.println("Error querying data from InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void writeToInfluxDB(Measurement_Table measurementTable) {
        String token = "3-wsjnNOrdM4gOhX-UnBGGV2kqkeUW0DDMU37vjCV1gNDdv9MqaB9JJZOfwN90Y3kOppCi1uP_OaZdnmFPL0Pg==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            //long influxDBTimestamp = measurementTable.getTimestamp().toInstant().toEpochMilli() * 1_000_000L; // Umrechnung in Nanosekunden

            long influxDBTimestamp = TimeUnit.MILLISECONDS.toNanos(measurementTable.getTimestamp().getTime());

            System.out.println("Timestamp: " + measurementTable.getTimestamp().toString());

            Point point = Point.measurement("Measurement_Table")
                    .addTag("id", measurementTable.getId().toString())
                    .addField("value", measurementTable.getValue())
                    .addField("measurement", measurementTable.getMeasurement().toString())
                    .time(influxDBTimestamp, WritePrecision.NS);

            WriteApiBlocking writeApi = client.getWriteApiBlocking();
            writeApi.writePoint(bucket, org, point);

            client.close();

            System.out.println("Data successfully written to InfluxDB.");

        } catch (Exception e) {
            System.err.println("Error writing    data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
