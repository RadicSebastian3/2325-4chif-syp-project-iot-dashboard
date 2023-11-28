package at.htl.influxdb;

import java.time.Instant;
import java.util.List;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;

public class InfluxDB2Example {
    public static void main(final String[] args) {

        // You can generate an API token from the "API Tokens Tab" in the UI
        String token = System.getenv("INFLUX_TOKEN");
        String bucket = "db";
        String org = "Leoenergy";

        InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());
    }
}

