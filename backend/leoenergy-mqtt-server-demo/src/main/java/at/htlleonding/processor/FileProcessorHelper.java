package at.htlleonding.processor;


import at.htlleonding.repository.MqttRepository;
import at.htlleonding.entity.Device;
import at.htlleonding.entity.SensorValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class FileProcessorHelper {


@Inject
MqttRepository mqttRepository;

    private long processedFileCount = 0;

    private long deletedFileCount = 0;

    public void importJsonFiles(String directoryName,int limit) {
        try (Stream<Path> filePathStream = Files.walk(Paths.get(directoryName)).onClose(() -> {
            Log.info("Closing file stream");
            Log.info("All " + deletedFileCount + " files deleted");
            Log.info("All " + processedFileCount + " files processed");
        })) {
            filePathStream
                    .filter(Files::isRegularFile)
                    .filter(f -> !f.toFile().isHidden())
                    .limit(limit)
                    .forEach(this::processFileAndDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private  void processFileAndDelete(Path filePath) {
        parseJson(filePath);
        deleteFileAfterReading(filePath);
    }

    private void deleteFileAfterReading(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                deletedFileCount++;
            }
        } catch (IOException e) {
            Log.error("Error deleting file: " + filePath.toString(), e);
        }
    }

    private void parseJson(Path filePath) {
        //System.out.println(jsonRoot);
        //printOnConsole(filePath);
        persistInDb(filePath);
        processedFileCount++;
        printProgress();
    }

    private void printProgress(){
        if(processedFileCount % 2000 == 0){
            Log.infof("%d files processed", processedFileCount);
            Log.infof("%d files deleted", deletedFileCount);
            processedFileCount = 0;
            deletedFileCount = 0;
        }

    }

    @Transactional
    void persistInDb(Path filePath) {

        ObjectMapper om = new ObjectMapper();
        JsonNode jsonRoot = null;
        Device device = null;

        try {
            jsonRoot = om.readTree(filePath.toFile());

            // check, if device already exists
            String deviceName = jsonRoot.at("/Device/Name").asText();
            String deviceSite = jsonRoot.at("/Device/Site").asText();




                device = new Device(
                        jsonRoot.at("/Device/Id").asInt(),
                        jsonRoot.at("/Device/ManufacturerId").asText(),
                        jsonRoot.at("/Device/Medium").asText(),
                        jsonRoot.at("/Device/Name").asText(),
                        jsonRoot.at("/Device/Site").asText()
                );


            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        ArrayNode valueArray = (ArrayNode) jsonRoot.at("/Device/ValueDescs");
            for (JsonNode jsonNode : valueArray) {


                long dateNow = 1707865200;
                long timeOfValueInUnix =  jsonNode.get("Values").get(0).get("Timestamp").asLong();

                long diff = dateNow-timeOfValueInUnix; // differenz beetwen today 0:00 and the timestamp
                long newDate = diff + timeOfValueInUnix; // calculate new Date for every measurement

                SensorValue sensorValue = new SensorValue(device,
                        newDate,
                        UnitConverter.convertToKilowatt(
                                jsonNode.get("UnitStr").asText(),
                                jsonNode.get("Values").get(0).get("Val").doubleValue()),
                        jsonNode.get("Id").asLong(),
                        jsonNode.get("DescriptionStr").asText(),
                        jsonNode.get("UnitStr").asText());




               if ( jsonNode.get("UnitStr").asText().equals("W") || jsonNode.get("UnitStr").asText().equals("Wh")) {
                    mqttRepository.setSensorValuePojo(mqttRepository.convertSensorValueToPojo(sensorValue));
                    mqttRepository.sendMessage();
                }
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

    public static void main(String[] args) {

    }
}