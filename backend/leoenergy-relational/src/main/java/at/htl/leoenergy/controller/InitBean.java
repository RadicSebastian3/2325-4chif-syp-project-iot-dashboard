package at.htl.leoenergy.controller;

import at.htl.leoenergy.entity.SensorValue;
import at.htl.leoenergy.entity.Device;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@ApplicationScoped
public class InitBean {
    @ConfigProperty(name = "json.file-directory-midi")
    private String directoryNameMidi;

    @ConfigProperty(name = "json.file-directory-maxi_100")
    private String directoryNameMaxi_100;

    @ConfigProperty(name = "json.file-directory-all")
    private String directoryNameAll;

    @ConfigProperty(name = "json.file-directory-midi_700")
    private String directoryNameMidi_700;

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private SensorValueRepository sensorValueRepository;

    private long processedFileCount = 0;

    void init(@Observes StartupEvent event) {
        importJsonFiles(directoryNameAll);
        //printFileNamesOnConsole(directoryNameMaxi_100);
    }

    private void importJsonFiles(String directoryName) {
        try (Stream<Path> filePathStream = Files.walk(Paths.get(directoryName))) {
            filePathStream
                    .filter(Files::isRegularFile)
                    .filter(f -> !f.toFile().isHidden())
                    //.peek(System.out::println)
                    //.map(Path::getFileName)
                    .forEach(this::parseJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Log.info("All " + processedFileCount + " files processed");
        processedFileCount = 0;
    }

    private void parseJson(Path filePath) {
        //System.out.println(jsonRoot);
        //printOnConsole(filePath);
        persistInDb(filePath);
        processedFileCount++;
        printProgress();
    }

    private void printProgress(){
        if(processedFileCount % 20000 == 0){
            Log.infof("%d files processed", processedFileCount);
        }
    }

    @Transactional
    void persistInDb(Path filePath) {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonRoot = null;

        try {
            jsonRoot = om.readTree(filePath.toFile());

            // check, if device already exists
            String deviceName = jsonRoot.at("/Device/Name").asText();
            String deviceSite = jsonRoot.at("/Device/Site").asText();

            long noOfDevices = deviceRepository.count("name = :NAME and site = :SITE",
                    Parameters
                            .with("NAME", deviceName)
                            .and("SITE", deviceSite)
            );

            Device device = null;
            if (noOfDevices > 0L) {
                device = deviceRepository.find("name = :NAME and site = :SITE",
                        Parameters
                                .with("NAME", deviceName)
                                .and("SITE", deviceSite)
                ).singleResult();
            } else {
                device = new Device(
                        jsonRoot.at("/Device/Id").asInt(),
                        jsonRoot.at("/Device/ManufacturerId").asText(),
                        jsonRoot.at("/Device/Medium").asText(),
                        jsonRoot.at("/Device/Name").asText(),
                        jsonRoot.at("/Device/Site").asText()
                );
            }

            ArrayNode valueArray = (ArrayNode) jsonRoot.at("/Device/ValueDescs");
            for (JsonNode jsonNode : valueArray) {
                SensorValue sensorValue = new SensorValue(
                        device,
                        jsonNode.get("Id").asInt(),
                        jsonNode.get("DescriptionStr").asText(),
                        jsonNode.get("UnitStr").asText(),
                        jsonNode.get("Values").get(0).get("Timestamp").asLong(),
                        jsonNode.get("Values").get(0).get("Val").asDouble()
                );
                sensorValueRepository.persist(sensorValue);
                //Log.infof("%s persisted", sensorValue);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printOnConsole(Path filePath) {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonRoot = null;

        try {
            System.out.println(filePath.getFileName());
            jsonRoot = om.readTree(filePath.toFile());

            System.out.println(jsonRoot.get("TimeUTC"));
            System.out.println(jsonRoot.get("Timestamp"));
            System.out.println(jsonRoot.get("TimezoneOffset"));
            System.out.println(jsonRoot.at("/Device/Id"));
            System.out.println(jsonRoot.at("/Device/ManufacturerId"));
            System.out.println(jsonRoot.at("/Device/Medium"));
            System.out.println(jsonRoot.at("/Device/Name"));
            System.out.println(jsonRoot.at("/Device/Site"));
            ArrayNode valueArray = (ArrayNode) jsonRoot.at("/Device/ValueDescs");
            for (JsonNode jsonNode : valueArray) {
                System.out.println(jsonNode.get("Id"));
                System.out.println(jsonNode.get("DescriptionStr"));
                System.out.println(jsonNode.get("UnitStr"));
                System.out.println(jsonNode.get("Values").get(0).get("Timestamp"));
                System.out.println(jsonNode.get("Values").get(0).get("Val"));
                System.out.println("-----------------------------");
            }
            System.out.println("----------------------------------------------\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printFileNamesOnConsole(String directoryName) {
        try (Stream<Path> filePathStream = Files.walk(Paths.get(directoryName))) {
            filePathStream
                    .filter(Files::isRegularFile)
                    .filter(f -> !f.toFile().isHidden())
                    //.peek(System.out::println)
                    //.map(Path::getFileName)
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
