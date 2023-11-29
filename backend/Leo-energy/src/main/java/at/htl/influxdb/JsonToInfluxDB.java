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

import java.util.List;

public class JsonToInfluxDB {

    public static void queryAllData() {
        String token = "_Kilkd0CdWXyWFem_uoj8A5mPdW3jsYmnOIAvNX42HAfClj-D9zK0Zzh97XptA5vkKHFq9Fvys4hnLDXANYrmQ==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());

            // Erstelle eine InfluxDB-Abfrage, die alle Daten für bestimmte Felder abruft
            String query = "from(bucket: \"db\") |> range(start: -1h)";
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
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");

        String token = "_Kilkd0CdWXyWFem_uoj8A5mPdW3jsYmnOIAvNX42HAfClj-D9zK0Zzh97XptA5vkKHFq9Fvys4hnLDXANYrmQ==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";

        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());

            System.out.println("Timestamp: " + measurementTable.getTimestamp().toString());
            Point point = Point.measurement("Measurement_Table")
                    .addTag("id", measurementTable.getId().toString())
                    .addField("value", measurementTable.getValue())
                    .addField("measurement", measurementTable.getMeasurement().toString())
                    .time(measurementTable.getTimestamp().toInstant(), WritePrecision.NS);

            WriteApiBlocking writeApi = client.getWriteApiBlocking();
            writeApi.writePoint(bucket, org, point);
            client.close();

            System.out.println("Data successfully written to InfluxDB.");

        } catch (Exception e) {
            System.err.println("Error writing data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
