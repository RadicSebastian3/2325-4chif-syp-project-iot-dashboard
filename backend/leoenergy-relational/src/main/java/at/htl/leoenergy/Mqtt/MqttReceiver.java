package at.htl.leoenergy.Mqtt;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MqttReceiver {

    private static final String timestampTopic = "timestamp";
    private static final String consumptionWTopic = "consumption-w";
    private static final String gridFeedInWTopic = "grid-feed-in-w";
    private static final String productionWTopic = "production-w";
    private static final String battRemainingCapacityWhTopic = "batt-remaining-capacity-wh";
    private static final String battRemainingCapacityPercentTopic = "batt-remaining-capacity-%";

    @Incoming(timestampTopic)
    public void receiveTimestamp(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received timestamp: " + msgAsJson);
    }

    @Incoming(consumptionWTopic)
    public void receiveConsumptionW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received consumption W: " + msgAsJson);
    }

    @Incoming(gridFeedInWTopic)
    public void receiveGridFeedInW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received grid feed in W: " + msgAsJson);
    }

    @Incoming(productionWTopic)
    public void receiveProductionW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received production W: " + msgAsJson);
    }

    @Incoming(battRemainingCapacityWhTopic)
    public void receiveBattRemainingCapacityW(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received batt remaining capacity W: " + msgAsJson);
    }

    @Incoming(battRemainingCapacityPercentTopic)
    public void receiveBattRemainingCapacityPercent(byte[] byteArray){
        final String msgAsJson = new String(byteArray);
        Log.info("Received batt remaining capacity %: " + msgAsJson);
    }
}