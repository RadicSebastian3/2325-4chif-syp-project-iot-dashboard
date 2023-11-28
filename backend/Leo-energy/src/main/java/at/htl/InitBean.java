package at.htl;

import at.htl.entity.Device;
import at.htl.entity.OldUnit;
import at.htl.entity.OldValue;
import at.htl.entity.OldValueType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped

public class InitBean {



    static List<OldUnit> units = new ArrayList<>();
    static List<Device> devices = new ArrayList<>();

    static List<OldValue> valueList = new ArrayList<>();
    static List<OldValueType> valueTypeList = new ArrayList<>();

    void startUp(@Observes StartupEvent event) throws IOException {
        int counter = 1;

        String testOrdnerPath = "testOrdner/";
        File testOrdner = new File(testOrdnerPath);
        if (testOrdner.exists() && testOrdner.isDirectory()) {
            File[] datas = testOrdner.listFiles();
            if (datas != null) {
                for (File data : datas) {

                    try {
                        String filePath = data.getPath();
                        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(jsonString);

                        JsonNode device = jsonNode.get("Device");
                        Device newDevice = new Device(device.get("Id").bigIntegerValue(), "",
                                device.get("Name").asText(), device.get("Site").asText());


                        JsonNode splittedJsonAfterValueDescs = jsonNode.get("Device").get("ValueDescs");

                        devices.add(newDevice);
                        if (splittedJsonAfterValueDescs.isArray()) {
                            for (JsonNode element : splittedJsonAfterValueDescs) {
                                OldUnit newUnit = new OldUnit(new BigInteger(String.valueOf(counter)), element.get("UnitStr").
                                        asText());

                                JsonNode valuesOfCurrentElement = element.get("Values").get(0);


                                OldValueType newValueType = new OldValueType(element.get("Id").bigIntegerValue(), newUnit,
                                        element.get("DescriptionStr").asText(), true, false);


                                OldValue newValue = new OldValue(new BigInteger(String.valueOf(counter)),
                                        new Timestamp(valuesOfCurrentElement.get("Timestamp").asLong() * 1000),
                                        valuesOfCurrentElement.get("Val").bigIntegerValue(), newDevice, newValueType);
                                counter++;
                                units.add(newUnit);
                                valueTypeList.add(newValueType);
                                valueList.add(newValue);
                            }

                        }
                       // System.out.println(units.toString());
                        //System.out.println(devices.toString());
                      //  System.out.println(valueList.toString());
                       // System.out.println(valueTypeList.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (data.delete()) {
                        System.out.println("Datei erfolgreich gelöscht: " + data.getName());
                    } else {
                        System.out.println("Fehler beim Löschen der Datei: " + data.getName());
                    }


                }
            } else {
                System.out.println("Das Verzeichnis ist leer.");
            }
        } else {
            System.out.println("Das Verzeichnis existiert nicht oder ist kein Verzeichnis.");
        }

        System.out.println("Size of diveces" + devices.size());

        System.out.println(units.size());
        System.out.println(valueList.size());
        System.out.println(valueTypeList.size());

        //TRY DB

    }

    public static List<Device> getDevices() {
        return devices;
    }

    public static List<OldUnit> getUnits() {
        return units;
    }

    public static List<OldValue> getvalueList() {
        return valueList;
    }

    public static List<OldValueType> getValueTypeList() {
        return valueTypeList;
    }
}
