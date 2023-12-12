package at.htl.resource;

import at.htl.entity.Measurement_Table;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static at.htl.influxdb.JsonToInfluxDB.getValuesBetweenTwoTimeStamps;

@Path("/energyproduction")
public class DataResource {
    @GET
    @Path("/getDataBetweenTwoTimestamps/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataBetweenTwoTimestamps(@PathParam("start") String start, @PathParam("end") String end){
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime = Timestamp.valueOf(end);

        List<Measurement_Table> data = getValuesBetweenTwoTimeStamps(startTime, endTime);
        Map<Timestamp, Double> map = new HashMap<>();

        for (Measurement_Table measurement : data){
            map.put(Timestamp.from(measurement.getTimeInstance()), measurement.getValue().doubleValue());
        }

        return Response.ok().entity(map).build();
    }

    @GET
    @Path("/example/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response example(@PathParam("start") String start, @PathParam("end") String end){
        Timestamp startTime = Timestamp.from(Instant.parse(start));
        Timestamp endTime = Timestamp.from(Instant.parse(end));

        Map<Timestamp, Double> map = new HashMap<>();
        map.put(startTime, Double.valueOf(Math.abs(new Random().nextInt())));
        map.put(Timestamp.from(Instant.parse(start).plusSeconds(10000)), Double.valueOf(Math.abs(new Random().nextInt())));
        map.put(Timestamp.from(Instant.parse(start).plusSeconds(20000)), Double.valueOf(Math.abs(new Random().nextInt())));
        map.put(Timestamp.from(Instant.parse(start).plusSeconds(30000)), Double.valueOf(Math.abs(new Random().nextInt())));
        map.put(Timestamp.from(Instant.parse(start).plusSeconds(40000)), Double.valueOf(Math.abs(new Random().nextInt())));
        map.put(Timestamp.from(Instant.parse(start).plusSeconds(50000)), Double.valueOf(Math.abs(new Random().nextInt())));
        map.put(endTime, Double.valueOf(Math.abs(new Random().nextInt())));

        return Response.ok().entity(map).build();
    }
}
