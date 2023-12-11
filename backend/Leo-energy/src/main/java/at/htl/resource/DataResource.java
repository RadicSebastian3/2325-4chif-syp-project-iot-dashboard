package at.htl.resource;

import at.htl.entity.Measurement_Table;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static at.htl.influxdb.JsonToInfluxDB.getValuesBetweenTwoTimeStamps;

@Path("/device/data")
public class DataResource {

    @GET
    @Produces("application/json")

    public Map<String, List<?>> getAllData() {
        Timestamp startTime = Timestamp.valueOf("2023-10-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-10-31 23:59:59");

        List<Measurement_Table> measurements = getValuesBetweenTwoTimeStamps(startTime, endTime);
        List<String> labels = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();

        for (Measurement_Table measurement : measurements) {
            labels.add(measurement.getTimeInstance().toString());
            values.add(measurement.getValue());
        }

        Map<String, List<?>> dataMap = new HashMap<>();
        dataMap.put("labels", labels);
        dataMap.put("values", values);

        return dataMap;
    }

}
