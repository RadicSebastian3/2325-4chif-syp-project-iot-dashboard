package at.htl.leoenergy.mqtt;

import at.htl.leoenergy.entity.SensorBoxValue;
import at.htl.leoenergy.entity.SensorValue;
import at.htl.leoenergy.influxdb.InfluxDbRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ApplicationScoped
public class MqttReceiver {
    @Inject
    InfluxDbRepository influxDbRepository;

    @ConfigProperty(name = "mp.messaging.incoming.leoenergy.host")
    String leoenergyHost;

    @ConfigProperty(name = "mp.messaging.incoming.sensorbox.host")
    String sensorboxHost;

    public void startUp(@Observes StartupEvent ev) {
        Log.infof("Set %s as leoenergy host", leoenergyHost);
        Log.infof("Set %s as sensorbox host", sensorboxHost);
    }

    public void insertMeasurement(SensorValue sensorValue) {
        influxDbRepository.insertMeasurementFromJSON(sensorValue);
    }

    @Incoming("leoenergy")
    public void receive(byte[] byteArray) {
        String msg = new String(byteArray);
        try {
            SensorValue sensorValue = SensorValue.fromJson(msg);
            Log.info(sensorValue.toString());
            insertMeasurement(sensorValue);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Incoming("sensorbox")
    public CompletionStage<Void> receiveSensorBox(MqttMessage<byte[]> msg) {
        try {
            String topic = msg.getTopic();
            String payload = new String(msg.getPayload());
            String[] splitted = topic.split("/");

            // Prüfen, ob das Topic korrekt aufgebaut ist
            if (splitted.length < 3) {
                Log.warn("Unerwartetes Topic-Format: " + topic);
                Log.warn("Nachricht wird ignoriert, da das Topic nicht korrekt aufgebaut ist.");
                return msg.ack();
            }

            String floor = splitted[0];                      // z.B. eg
            String room = splitted[1];                       // z.B. e71
            String physicalParameter = splitted[2];          // z.B. temperature, noise, co2, ...
            String timestamp = extractTimestamp(payload);    // Unix-Timestamp
            String value = extractValue(payload);            // Wert

            if (timestamp.isEmpty() || value.isEmpty()) {
                Log.warn("Nachricht ignoriert, da der Payload (Body) nicht korrekt ist: " + payload);
                return msg.ack();
            }

            long timestampInSeconds = Long.parseLong(timestamp);
            long timestampInMilliseconds = timestampInSeconds * 1000;

            SensorBoxValue sensorBoxValue = new SensorBoxValue(
                    floor,
                    Double.parseDouble(value),
                    room,
                    physicalParameter,
                    timestampInMilliseconds
            );

            Log.info(sensorBoxValue.toString());
            influxDbRepository.insertSensorBoxMeasurement(sensorBoxValue);

            return msg.ack(); // Nachricht erfolgreich verarbeitet
        } catch (RuntimeException e) {
            Log.error(e);
            Log.warn("Die Nachricht konnte nicht verarbeitet werden: " + new String(msg.getPayload()));
            return msg.ack(); // Trotzdem die Nachricht als verarbeitet markieren
        }
    }


    private String extractTimestamp(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode.get("timestamp").asText().replace(",", ".");
        } catch (Exception e) {
            return "";
        }
    }

    private String extractValue(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode.get("value").asText().replace(",", ".");
        } catch (Exception e) {
            return "";
        }
    }
}
