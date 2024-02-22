package at.htl.leoenergy.mqtt;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;

public class SmartHomeReceiver {
    @Incoming("smarthome-json")
    public void receive(byte[] byteArray){
        String msg = new String(byteArray);
        Log.info(msg);
    }
}
