package at.htl.influxdb;

import at.htl.entity.Measurement;
import at.htl.entity.Measurement_Table;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import io.quarkus.logging.Log;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class JsonToInfluxDB {

    public static void writeToInfluxDB(Measurement_Table measurementTable) {
        String token = "tkmKIvXdbCpVOVhC2NId36u48RkVj6H8vHug5GfNqCJixN9RjG8RAl43lOZNxajhF7a6L-EhkrHt3wNnZ5VuzA==";
        String bucket = "db";
        String org = "Leoenergy";
        String influxUrl = "http://localhost:8086";
        try {
            InfluxDBClient client = InfluxDBClientFactory.create(influxUrl, token.toCharArray());
            WriteApiBlocking writeApi = client.getWriteApiBlocking();

          long currentTimeInNanoseconds = TimeUnit.SECONDS.toNanos(measurementTable.getTime());

            Point point = Point.measurement("sensor_data")
                    .addTag("id", measurementTable.getId().toString())
                    .addField("value", measurementTable.getValue())
                        .time(currentTimeInNanoseconds,WritePrecision.NS);

            writeApi.writePoint(bucket, org, point);
            Log.info("Wrote to InfluxDB: " + point.toLineProtocol());

            client.close();
            Log.info("Closed InfluxDB connection");

        } catch (Exception e) {
            System.err.println("Error writing    data to InfluxDB: " + e.getMessage());
            e.printStackTrace();
        }
    }




  /*  public static void main(String[] args) {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");


        Measurement_Table measurementTable = new Measurement_Table(BigInteger.valueOf(17l),
                1700646116,
                BigDecimal.valueOf(987654),
                new Measurement());
        writeToInfluxDB(measurementTable);
    }*/
}
