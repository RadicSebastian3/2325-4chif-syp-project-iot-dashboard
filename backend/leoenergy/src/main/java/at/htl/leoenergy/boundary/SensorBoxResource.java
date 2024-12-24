package at.htl.leoenergy.boundary;

import at.htl.leoenergy.influxdb.InfluxDbRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/sensorbox")
@Produces(MediaType.APPLICATION_JSON)
public class SensorBoxResource {
    @Inject
    InfluxDbRepository influxDbRepository;

    @GET
    @Path("/floors")
    public Response getAllFloors() {
        var floors = influxDbRepository.getAllFloors();
        return !floors.isEmpty()
                ? Response.ok(floors).build()
                : Response.noContent().entity("No floors in database").build();
    }

    @GET
    @Path("/rooms")
    public Response getAllRooms() {
        var rooms = influxDbRepository.getAllRooms();
        return !rooms.isEmpty()
                ? Response.ok(rooms).build()
                : Response.noContent().entity("No rooms in database").build();
    }

    @GET
    @Path("/latest-values/{room}")
    public Response getLatestValues(@PathParam("room") String room) {
        return influxDbRepository.getAllRooms().contains(room)
                ? Response.ok(influxDbRepository.getLatestSensorBoxDataForRoom(room)).build()
                : Response.noContent().entity("Room not in database").build();
    }
}
