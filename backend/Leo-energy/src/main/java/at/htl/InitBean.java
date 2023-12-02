package at.htl;

import at.htl.controller.DeviceRepository;
import at.htl.controller.MeasurementRepository;
import at.htl.entity.Device;
import at.htl.entity.Measurement;
import at.htl.entity.Measurement_Table;
import at.htl.influxdb.JsonToInfluxDB;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped

public class InitBean {

    @Inject
    MeasurementRepository measurementRepository;

    @Inject
    DeviceRepository deviceRepository;


    static List<Device> devices = new ArrayList<>();

    static List<Measurement> measurementList = new ArrayList<>();
    static List<Measurement_Table> measurement_tableList = new ArrayList<>();
    @Transactional
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
                        Device newDevice = new Device(device.get("Id").bigIntegerValue(), device.get("Name").asText());


                        JsonNode splittedJsonAfterValueDescs = jsonNode.get("Device").get("ValueDescs");

                       // devices.add(newDevice);
                        if (splittedJsonAfterValueDescs.isArray()) {
                            for (JsonNode element : splittedJsonAfterValueDescs) {

                                Measurement currentMeasurement = new Measurement(element.get("Id").bigIntegerValue(),
                                        element.get("DescriptionStr").asText(),
                                        element.get("ValueType").decimalValue(),
                                        newDevice);

                                measurementRepository.save(currentMeasurement);

                                JsonNode valuesOfCurrentElement = element.get("Values").get(0);

                                Measurement_Table measurementTable = new Measurement_Table((new BigInteger(String.valueOf(counter))),
                                        (valuesOfCurrentElement.get("Timestamp").asLong()),
                                        valuesOfCurrentElement.get("Val").decimalValue(),currentMeasurement);
                                counter++;

                                JsonToInfluxDB.writeToInfluxDB(measurementTable);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                  /*  if (data.delete()) {
                        System.out.println("Datei erfolgreich gelöscht: " + data.getName());
                    } else {
                        System.out.println("Fehler beim Löschen der Datei: " + data.getName());
                    }*/


                }
            } else {
                System.out.println("Das Verzeichnis ist leer.");
            }
        } else {
            System.out.println("Das Verzeichnis existiert nicht oder ist kein Verzeichnis.");
        }

        JsonToInfluxDB.queryAllData();
    }
}
