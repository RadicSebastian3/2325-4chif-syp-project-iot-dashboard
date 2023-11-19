package at.htl;

import at.htl.entity.Device;
import at.htl.entity.Unit;
import at.htl.entity.Value;
import at.htl.entity.ValueType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.json.Json;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped

public class InitBean {

    static List<Unit> units = new ArrayList<>();
    static List<Device> devices = new ArrayList<>();

    static List<Value> valueList = new ArrayList<>();
    static List<ValueType> valueTypeList = new ArrayList<>();

    void startUp(@Observes StartupEvent event) throws IOException {
        int counter = 1;

        try {
            String filePath = "data/ftp-data/7-10979582-20221205194710.json";
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            JsonNode device = jsonNode.get("Device");
            Device newDevice = new Device(device.get("Id").bigIntegerValue(),
                    "", device.get("Name").asText(),
                    device.get("Site").asText());


            JsonNode splittedJsonAfterValueDescs = jsonNode.get("Device").get("ValueDescs");

            devices.add(newDevice);
            if (splittedJsonAfterValueDescs.isArray()) {
                for (JsonNode element : splittedJsonAfterValueDescs) {
                    Unit newUnit = new Unit(new BigInteger(String.valueOf(counter)),
                            element.get("UnitStr").asText());

                    JsonNode valuesOfCurrentElement = element.get("Values").get(0);


                    ValueType newValueType = new ValueType(element.get("Id").bigIntegerValue(),
                            newUnit, element.get("DescriptionStr").asText(),
                            true,
                            false);


                    Value newValue = new Value(new BigInteger(String.valueOf(counter)),
                            new Timestamp(valuesOfCurrentElement.get("Timestamp").asLong() * 1000),
                            valuesOfCurrentElement.get("Val").bigIntegerValue(),
                            newDevice, newValueType);
                    counter++;
                    units.add(newUnit);
                    valueTypeList.add(newValueType);
                    valueList.add(newValue);
                }

            }
            System.out.println(units.toString());
            System.out.println(devices.toString());
            System.out.println(valueList.toString());
            System.out.println(valueTypeList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Device> getDevices() {
        return devices;
    }

    public static List<Unit> getUnits() {
        return units;
    }
    public static List<Value> getvalueList() {
        return valueList;
    }
    public static List<ValueType> getValueTypeList() {
        return valueTypeList;
    }
}
