package at.htlleonding.mqtt;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.time.Duration;

public class Sender {
    @Outgoing("sensorvalues")
    public Multi<String> send(){
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
                .map(x -> "Hallo MQTT");
    }
}
