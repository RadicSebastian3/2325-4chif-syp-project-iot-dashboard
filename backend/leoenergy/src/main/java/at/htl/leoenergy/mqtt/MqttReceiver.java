package at.htl.leoenergy.mqtt;

import at.htl.leoenergy.entity.SensorBoxValue;
import at.htl.leoenergy.entity.SensorValue;
import at.htl.leoenergy.influxdb.InfluxDbRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ApplicationScoped
public class MqttReceiver {
   @Inject
   InfluxDbRepository influxDbRepository;

   public void insertMeasurement(SensorValue sensorValue){
       influxDbRepository.insertMeasurementFromJSON(sensorValue);
   }

   @Incoming("leoenergy")
   public void receive(byte[] byteArray) {


       String msg = new String(byteArray);
       try {
           SensorValue sensorValue = SensorValue.fromJson(msg);
           Log.info(sensorValue.toString());
           insertMeasurement(sensorValue);
       }
       catch (NullPointerException e){
           e.printStackTrace();
       }
   }

   @Incoming("sensorbox")
   public CompletionStage<Void> receiveSensorBox(MqttMessage<byte[]> msg) {
       String topic = msg.getTopic();
       String payload = new String(msg.getPayload());
       String[] splitted = topic.split("/");

       String floor = splitted[0];                      //zB eg
       String room = splitted[1];                       //zB e71
       String physicalParameter = splitted[2];          //zB temperature, noise, co2,...
       String timestamp = extractTimestamp(payload);     //Unix-Timestamp

       String value = extractValue(payload);            //value


       long timestampInSeconds = Long.parseLong(timestamp);
       long timestampInMilliseconds = timestampInSeconds * 1000;

       SensorBoxValue sensorBoxValue = new SensorBoxValue(floor,Double.parseDouble(value),room,physicalParameter,timestampInMilliseconds);

       Log.info(sensorBoxValue.toString());

       influxDbRepository.insertSensorBoxMeasurement(sensorBoxValue);

       return msg.ack();
   }

   private String extractTimestamp(String json){
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode jsonNode = objectMapper.readTree(json);
           return jsonNode.get("timestamp").asText().replace(",", ".");
       } catch (Exception e) {
           return "";
       }
   }

   private String extractValue(String json){
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode jsonNode = objectMapper.readTree(json);
           return jsonNode.get("value").asText().replace(",", ".");
       } catch (Exception e) {
           return "";
       }
   }
}
