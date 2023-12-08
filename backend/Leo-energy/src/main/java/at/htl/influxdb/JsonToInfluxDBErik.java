package at.htl.influxdb;

import at.htl.entity.Measurement_Table;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JsonToInfluxDBErik {
    public static void queryAllData() {
        String token = "8CeAlE7unEyBbclvk1kFvZ2IBamA4jFmH17-if_7qCbUiJnnZdrPT2KDCv56x9menlCQRmppjOLNMJs8VV9eGw==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());

            String query = "from(bucket: \"db\") |> range(start: -7d) |> filter(fn: (r) => r._measurement == \"Measurement_Table\")";
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
        String token = "8CeAlE7unEyBbclvk1kFvZ2IBamA4jFmH17-if_7qCbUiJnnZdrPT2KDCv56x9menlCQRmppjOLNMJs8VV9eGw==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());

            long currentTimeInNanoseconds = TimeUnit.SECONDS.toNanos(1700646606);

            Point point = Point.measurement("Measurement_Table")
                    .addTag("id", measurementTable.getId().toString())
                    .addField("value", measurementTable.getValue())
                    .time(Instant.now(), WritePrecision.NS);

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
