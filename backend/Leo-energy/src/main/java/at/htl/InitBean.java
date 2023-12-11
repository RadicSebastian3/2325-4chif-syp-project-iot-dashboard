package at.htl;

import at.htl.controller.DeviceRepository;
import at.htl.controller.MeasurementRepository;
import at.htl.entity.Device;
import at.htl.entity.Measurement;
import at.htl.entity.Measurement_Table;
import at.htl.influxdb.JsonToInfluxDB;
import at.htl.influxdb.UnitConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class InitBean {

    private static final Logger LOG = LoggerFactory.getLogger(InitBean.class);

    @Inject
    MeasurementRepository measurementRepository;

    @Inject
    DeviceRepository deviceRepository;

    private static final int MAX_FILES_TO_PROCESS = 5;

    private int counter = 1;

    void startUp(@Observes StartupEvent event) {
        String configFilePath = "config/config.txt";
        File configFile = new File(configFilePath);

        try {
            readCounterFromConfig(configFile);
            processFiles();
            writeCounterToConfig(configFile);
        } catch (IOException e) {
            LOG.error("Fehler beim Starten des Programms", e);
        }
    }

    private void readCounterFromConfig(File configFile) throws IOException {
        if (configFile.exists()) {
            try (Scanner scanner = new Scanner(configFile)) {
                if (scanner.hasNextInt()) {
                    counter = scanner.nextInt();
                }
            }
        }
    }

    private void processFiles() {
        String relativePath = "/home/balint/htl/4bhif/syp/newData/wetransfer_2023-10-zip_2023-11-22_1103/2023-10";
        String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();
        File testOrdner = new File(absolutePath);

        if (testOrdner.exists() && testOrdner.isDirectory()) {
            File[] datas = testOrdner.listFiles();
            if (datas != null) {
                ExecutorService executorService = Executors.newFixedThreadPool(MAX_FILES_TO_PROCESS);

                for (File data : datas) {
                    executorService.submit(() -> processFile(data));
                }

                executorService.shutdown();
                try {
                    executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    LOG.error("Fehler beim Warten auf die ExecutorService-Abschaltung", e);
                }
            } else {
                LOG.info("Das Verzeichnis ist leer.");
            }
        } else {
            LOG.info("Das Verzeichnis existiert nicht oder ist kein Verzeichnis.");
        }
    }

    private void processFile(File data) {
        try {
            String filePath = data.getPath();
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            JsonNode device = jsonNode.get("Device");
            Device newDevice = new Device(device.get("Id").bigIntegerValue(), device.get("Name").asText());

            JsonNode splittedJsonAfterValueDescs = jsonNode.get("Device").get("ValueDescs");

            deviceRepository.save(newDevice);
            if (splittedJsonAfterValueDescs.isArray()) {
                for (JsonNode element : splittedJsonAfterValueDescs) {
                    Measurement currentMeasurement = new Measurement(element.get("Id").bigIntegerValue(), element.get("DescriptionStr").asText(), element.get("ValueType").decimalValue(), newDevice, element.get("UnitStr").asText());

                    measurementRepository.save(currentMeasurement);

                    JsonNode valuesOfCurrentElement = element.get("Values").get(0);

                    Measurement_Table measurementTable = new Measurement_Table(new BigInteger(String.valueOf(counter)),
                            currentMeasurement.getId(),
                            valuesOfCurrentElement.get("Timestamp").asLong(),
                            UnitConverter.convertToKilowatt(currentMeasurement.getUnitStr(),
                            valuesOfCurrentElement.get("Val").decimalValue()));

                    counter += 1;

                    JsonToInfluxDB.insertMeasurement(measurementTable);
                }
            }

        } catch (Exception e) {
            LOG.error("Fehler bei der Verarbeitung der Datei: {}", data.getName(), e);
        }

        if (data.delete()) {
            LOG.info("Datei erfolgreich gelöscht: {}", data.getName());
        } else {
            LOG.error("Fehler beim Löschen der Datei: {}", data.getName());
        }
    }

    private void writeCounterToConfig(File configFile) {
        try (PrintWriter writer = new PrintWriter(configFile)) {
            writer.print(counter);
        } catch (IOException e) {
            LOG.error("Fehler beim Schreiben des Zählerwerts in die Konfigurationsdatei", e);
        }
    }
}
