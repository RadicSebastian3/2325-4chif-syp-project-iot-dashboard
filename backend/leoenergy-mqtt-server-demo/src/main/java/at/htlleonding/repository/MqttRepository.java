package at.htlleonding.repository;

import at.htlleonding.entity.SensorValue;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class MqttRepository {

    @Outgoing("leonergy-demo/TOPIC1")
    public static Multi<String> sendMessage() {
        List<SensorValue> sensorValueList;

        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .map(x -> {

                    Jsonb jsonb = JsonbBuilder.create();
                    return jsonb.toJson(new SensorValue());
                });
    }


}
