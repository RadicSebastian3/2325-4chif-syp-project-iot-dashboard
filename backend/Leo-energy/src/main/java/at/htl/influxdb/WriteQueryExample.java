package at.htl.influxdb;

import java.time.Instant;
import java.util.stream.Stream;

import com.influxdb.v3.client.InfluxDBClient;
import com.influxdb.v3.client.query.QueryOptions;
import com.influxdb.v3.client.query.QueryType;
import com.influxdb.v3.client.write.Point;
import com.influxdb.v3.client.write.WriteOptions;

public final class WriteQueryExample {

    public static void main(final String[] args) throws Exception {
        String hostUrl = "https://eu-central-1-1.aws.cloud2.influxdata.com";
        char[] authToken = "2BBU5v2nGK-DS1nb6C1_3xrPE69UUZ7YLaDtc9YZGbeP54mZb68mhMH9cIvvdeO3FaC_ZB3ZWJuBsJx2ZnKhIQ==".toCharArray();

        try (InfluxDBClient client = InfluxDBClient.getInstance(hostUrl, authToken, null)) {
            String database = "mydb";

            Point[] points = new Point[] {
                    Point.measurement("census")
                            .addTag("location", "Klamath")
                            .addField("bees", 23),
                    Point.measurement("census")
                            .addTag("location", "Portland")
                            .addField("ants", 30),
                    Point.measurement("census")
                            .addTag("location", "Klamath")
                            .addField("bees", 28),
                    Point.measurement("census")
                            .addTag("location", "Portland")
                            .addField("ants", 32),
                    Point.measurement("census")
                            .addTag("location", "Klamath")
                            .addField("bees", 29),
                    Point.measurement("census")
                            .addTag("location", "Portland")
                            .addField("ants", 40)
            };

            for (Point point : points) {
                client.writePoint(point, new WriteOptions.Builder().database(database).build());

                Thread.sleep(1000); // separate points by 1 second
            }

            System.out.println("Complete. Return to the InfluxDB UI.");

            String sql = "SELECT * " +
                    "FROM 'census' " +
                    "WHERE time >= now() - interval '1 hour' " +
                    "AND ('bees' IS NOT NULL OR 'ants' IS NOT NULL) order by time asc";

            System.out.printf("| %-5s | %-5s | %-8s | %-30s |%n", "ants", "bees", "location", "time");
            try (Stream<Object[]> stream = client.query(sql, new QueryOptions("mydb", QueryType.SQL))) {
                stream.forEach(row -> System.out.printf("| %-5s | %-5s | %-8s | %-30s |%n",  row[0], row[1], row[2], row[3]));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }



    }
}
