package at.htl.influxdb;

import at.htl.entity.Measurement_Table;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import io.quarkus.logging.Log;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class JsonToInfluxDB_Erik {

    public static void writeToInfluxDB(Measurement_Table measurementTable) {
        String token = "0-NfyFWahOWqLpDD9TDhRGj-9OVL1NGBpdyuWLcaa85LEPiNEI243-5fi0ygtVKtgIY1iSAI9S0BIQpQorS_4w==";
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

    public void getValuesFromThatDay(Timestamp a1, Timestamp a2){
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
