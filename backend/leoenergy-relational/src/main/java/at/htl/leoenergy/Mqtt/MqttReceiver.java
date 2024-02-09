package at.htl.leoenergy.Mqtt;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import io.smallrye.reactive.messaging.mqtt.MqttMessageMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MqttReceiver {
    /*@Incoming("leo-energy")
    public void receive(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("received message from broker: " + msgAsJson);
        MqttData mqttObject = MqttData.convertJsonToMqttData(msgAsJson);
    }*/

    @Incoming("timestamp")
    public void receiveTimestamp(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received timestamp: " + msgAsJson);
    }

    @Incoming("consumption-w")
    public void receiveConsumptionW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received consumption: " + msgAsJson);
    }

    @Incoming("grid-feed-in-w")
    public void receiveGridFeedInW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received grid feed in W: " + msgAsJson);
    }

    @Incoming("production-w")
    public void receiveProductionW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received production W: " + msgAsJson);
    }

    @Incoming("batt-remaining-capacity-wh")
    public void receiveBattRemainingCapacityW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received batt remainung capacity W: " + msgAsJson);
    }

    @Incoming("batt-remaining-capacity-%")
    public void receiveBattRemainingCapacityPercent(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received batt remainung capacity %: " + msgAsJson);
    }
}