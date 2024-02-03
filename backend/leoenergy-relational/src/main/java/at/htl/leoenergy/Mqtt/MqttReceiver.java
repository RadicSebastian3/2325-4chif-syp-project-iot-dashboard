package at.htl.leoenergy.Mqtt;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MqttReceiver {
    @Incoming("leo-energy")
    public void receive(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("received message from broker: " + msgAsJson);
        MqttData mqttObject = MqttData.convertJsonToMqttData(msgAsJson);
    }
}
