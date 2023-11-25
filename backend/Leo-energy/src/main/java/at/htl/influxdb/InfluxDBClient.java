package at.htl.influxdb;

import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

public class InfluxDBClient {

    private com.influxdb.client.InfluxDBClient client;
    private String bucket;
    private String org;

    public InfluxDBClient(String propertiesPath) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
            String token = properties.getProperty("influxdb.token");
            this.bucket = properties.getProperty("influxdb.bucket");
            this.org = properties.getProperty("influxdb.org");
            String url = properties.getProperty("influxdb.url");
            this.client = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
            System.out.println("InfluxDB Client successfully initialized");
        } catch (Exception e) {
            System.out.println("Error initalizing InfluxDB Client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void writeTemperature(String device, double value) {
        System.out.println("Writing temperature data to InfluxDB");
        Point point = Point.measurement("temperature")
                .addTag("device", device)
                .addField("value", value)
                .time(Instant.now(), WritePrecision.NS);
        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writePoint(bucket, org, point);
        }
    }

    public List<FluxTable> queryTemperature(String device, Instant start, Instant stop) {
        QueryApi queryApi = client.getQueryApi();
        String flux = String.format("from(bucket:\"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"temperature\" and r.device == \"%s\")", bucket, start.toString(), stop.toString(), device);
        System.out.println("Query executed");
        return queryApi.query(flux);
    }

    public void close() {
        if (client != null) {
            client.close();
        }
    }

    public static void main(String[] args) {
        InfluxDBClient influxDBClient = new InfluxDBClient("config.properties");

        // Write a temperature point to InfluxDB
        influxDBClient.writeTemperature("sensor1", 22.5);

        // Query temperature points from InfluxDB
        List<FluxTable> tables = influxDBClient.queryTemperature("sensor1", Instant.now().minusSeconds(3600), Instant.now());
        for (FluxTable table : tables) {
            table.getRecords().forEach(record -> {
                System.out.println(record.getTime() + ": " + record.getValueByKey("value"));
            });
        }

        // Close the InfluxDB client
        influxDBClient.close();
    }
}
