package at.htl.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import at.htl.InitBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/device/data")
public class DataResource {
    @GET
    @Produces("application/json")
    public Map<String, List<?>> getAllData() {

        return null;
    }
}
