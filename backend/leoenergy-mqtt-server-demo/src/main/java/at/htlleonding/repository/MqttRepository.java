package at.htlleonding.repository;

import at.htlleonding.entity.Device;
import at.htlleonding.entity.SensorValue;
import at.htlleonding.pojo.SensorValuePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class MqttRepository {
    SensorValuePojo sensorValuePojo = new SensorValuePojo();

    public MqttRepository() {
    }

    @Outgoing("leonergy-demo" )
    public Multi<String> sendMessage() {

        Jsonb jsonb = JsonbBuilder.create();
        System.out.println(jsonb.toJson(sensorValuePojo));
        try {
            return Multi.createFrom().item(new ObjectMapper().writeValueAsString(sensorValuePojo));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public SensorValuePojo getSensorValuePojo() {
        return sensorValuePojo;
    }

    public void setSensorValuePojo(SensorValuePojo sensorValuePojo) {
        this.sensorValuePojo = sensorValuePojo;
    }

    public SensorValuePojo convertSensorValueToPojo(SensorValue sensorValue){
        return  new SensorValuePojo(sensorValue.getDevice().getName(),
                sensorValue.getMeasurementId(),
                sensorValue.getDescription(),
                sensorValue.getUnit(),
                sensorValue.getTime(),
                sensorValue.getValue()
        );
    }
}
