package at.htl.resource;

import at.htl.entity.Device;
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
        Map<String, List<?>> allData = new HashMap<>();
        allData.put("devices", InitBean.getDevices());
        allData.put("units", InitBean.getUnits());
        allData.put("values", InitBean.getvalueList());
        allData.put("valueTypes", InitBean.getValueTypeList());
        return allData;
    }
}
