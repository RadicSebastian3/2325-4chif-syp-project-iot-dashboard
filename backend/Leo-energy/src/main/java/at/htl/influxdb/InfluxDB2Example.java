package at.htl.influxdb;

import java.time.Instant;
import java.util.List;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.*;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

public class InfluxDB2Example {
    public static void main(final String[] args) {

        // You can generate an API token from the "API Tokens Tab" in the UI
        String token = "-PHNf93K8rwPKfgmBjmwSCfvqyQ3Nf6eE0R9pIml4K0VtJCoSTNtuCMKi4BVsds5nCXvhsLoEFUOQNXJsF3bPA==";
        String bucket = "db";
        String org = "Leoenergy";

        InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());

        String data = "mem,host=host1 used_percent=23.43234543";

        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writeRecord(bucket, org, WritePrecision.NS, data);

        Point point = Point
                .measurement("mem")
                .addTag("host", "host1")
                .addField("used_percent", 23.43234543)
                .time(Instant.now(), WritePrecision.NS);

         writeApi = client.getWriteApiBlocking();
        writeApi.writePoint(bucket, org, point);

        String query = "from(bucket: \"db\") |> range(start: -1h)";
        List<FluxTable> tables = client.getQueryApi().query(query, org);

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(record);
            }
        }



        client.close();


    }
}

