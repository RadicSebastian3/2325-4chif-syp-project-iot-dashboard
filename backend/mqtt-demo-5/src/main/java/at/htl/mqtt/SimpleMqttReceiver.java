package at.htl.mqtt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logmanager.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

@ApplicationScoped
public class SimpleMqttReceiver {
    private Logger logger = Logger.getLogger(SimpleMqttReceiver.class.getName());

    private ConcurrentLinkedQueue<MeasurementValue> queue = new ConcurrentLinkedQueue();
    @Inject
    ManagedExecutor executor;

    /**
     * maximum queue size
     * If we are not fast enough with inserting we skip values.
     */
    @ConfigProperty(name = "inserter.max.cache")
    int inserterMaxCache;
    @ConfigProperty(name = "mp.messaging.incoming.noise.topic")
    String fullTopicNoise;

    @ConfigProperty(name = "mp.messaging.incoming.motion.topic")
    String fullTopicMotion;

    @ConfigProperty(name = "mp.messaging.incoming.temperature.topic")
    String fullTopicTemperature;

    @ConfigProperty(name = "mp.messaging.incoming.co2.topic")
    String fullTopicCO2;

    @ConfigProperty(name = "mp.messaging.incoming.humidity.topic")
    String fullTopicHumidity;

    @ConfigProperty(name = "mp.messaging.incoming.neopixel.topic")
    String fullTopicNeopixel;

    @ConfigProperty(name = "mp.messaging.incoming.rssi.topic")
    String fullTopicRSSI;

    @ConfigProperty(name = "mp.messaging.incoming.pressure.topic")
    String fullTopicPressure;

    @Incoming("noise")
    public void receiveNoise(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicNoise);
    }

    @Incoming("motion")
    public void receiveMotion(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicMotion);
    }

    @Incoming("temperature")
    public void receiveTemperature(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicTemperature);
    }
    @Incoming("co2")
    public void receiveCO2(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicCO2);
    }

    @Incoming("humidity")
    public void receiveHumidity(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicHumidity);
    }

    @Incoming("neopixel")
    public void receiveNeopixel(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicNeopixel);
    }

    @Incoming("rssi")
    public void receiveRSSI(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicRSSI);
    }

    @Incoming("pressure")
    public void receivePressure(byte[] byteArray) {
        receiveMeasurements(byteArray, fullTopicPressure);
    }

    public record MeasurementValue(
            String floor,
            String room,
            String type,
            long timestamp,
            double value
    ) {}

    private void receiveMeasurements(byte[] byteArray, String topic) {
        var messageString = new String(byteArray);
        //logger.info(messageString);

        try {
            var mapper = new ObjectMapper();
            var jsonNode = mapper.readTree(messageString);

            var timestamp = jsonNode.get("timestamp").asLong();
            var value = jsonNode.get("value").asDouble();

            String[] topicParts = topic.split("/");
            String floor = topicParts[topicParts.length - 4];
            String room = topicParts[topicParts.length - 3];
            String type = topicParts[topicParts.length - 2];

            var measurementValue = new MeasurementValue(floor, room, type, timestamp, value);

            if (queue.size() <= inserterMaxCache) {
                queue.add(measurementValue);
            }

            CompletableFuture.supplyAsync(() -> insertNextValueToJsonFile(measurementValue), executor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int insertNextValueToJsonFile(MeasurementValue measurementValue) {
        int count = 0;
        try {
            File jsonFolder = new File("json");
            if (!jsonFolder.exists()) {
                jsonFolder.mkdir();
            }

            File file = new File(jsonFolder, measurementValue.floor + "-" + measurementValue.room + "-" + measurementValue.type + "-" + measurementValue.timestamp + ".json");
            try (FileWriter writer = new FileWriter(file)) {
                var mapper = new ObjectMapper();
                var jsonString = mapper.writeValueAsString(measurementValue);
                writer.write(jsonString);
                logger.info("Write to JSON file successful: " + file.getAbsolutePath());
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}