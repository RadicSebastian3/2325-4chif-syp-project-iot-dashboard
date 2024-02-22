package at.htl.leoenergy.mqtt.sunpower;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.Date;

public class SunPowerReceiver {
    @Incoming("sunpower-json")
    public void receive(byte[] byteArray){
        String msg = new String(byteArray);
        SunPowerPojo sunPowerPojo = SunPowerPojo.fromJson(msg);
        // DON'T REMOVE! required for UNIX timestamp converting, just ignore or accept it!!!
        sunPowerPojo.setTimeStamp(new Date(sunPowerPojo.getTimeStamp().getTime() - 60 * 60 * 1000));

        Log.info("new SunPowerPojo created after receiving: " + sunPowerPojo);

        //TODO: handle the next steps for the SunPowerPojo (insert to db, etc...)
    }
}
