package at.htl.leoenergy.Mqtt;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import io.smallrye.reactive.messaging.mqtt.MqttMessageMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MqttReceiver {

    private static final String timestampTopic = "timestamp";
    private static final String consumptionWTopic = "consumption-w";
    private static final String gridFeedInWTopic = "grid-feed-in-w";
    private static final String productionWTopic = "production-w";
    private static final String battRemainingCapacityWhTopic = "batt-remaining-capacity-wh";
    private static final String battRemainingCapacityPercentTopic = "batt-remaining-capacity-%";

    @Inject
    @Channel("merged-sunpower-data")
    Emitter<SunpowerData> mergedSunpowerData;

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
        Log.info("Received batt remainung capacity %: " + msgAsJson);
    }

    private void processMqttMessagePart(String value, String topic) {
        SunpowerData energyData = new SunpowerData();
        energyData.setTimeStamp(LocalDateTime.now());  // Setzen Sie den Zeitstempel nach Bedarf
        switch (topic) {
            case "timestamp":
                energyData.setTimeStamp(LocalDateTime.parse(value.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                break;
            case "consumption":
                energyData.setConsumptionW(Double.parseDouble(value.trim()));
                break;
            case "gridFeedIn":
                energyData.setGridFeedInW(Double.parseDouble(value.trim()));
                break;
            case "production":
                energyData.setProductionW(Double.parseDouble(value.trim()));
                break;
            case "battRemainingCapacityWh":
                energyData.setBattRemainingCapacityWh(Double.parseDouble(value.trim()));
                break;
            case "battRemainingCapacityPercent":
                energyData.setBattRemainingCapacityPercent(Double.parseDouble(value.trim()));
                break;
        }

        // Sende das EnergyData-Objekt an den Emitter
        mergedSunpowerData.send(energyData);
    }
}