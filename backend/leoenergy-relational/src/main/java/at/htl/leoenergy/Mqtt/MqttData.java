package at.htl.leoenergy.Mqtt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;

public class MqttData {
    private long time;
    private double value;

    private MqttData(long time, double value){
        this.time = time;
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static MqttData convertJsonToMqttData(String jsonString){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);

            long time = jsonNode.get("time").asLong();
            double value = jsonNode.get("value").asDouble();

            return new MqttData(time, value);
        } catch (Exception e) {
            Log.error("Error in MqttData.convertJsonToMqttData: could not convert " + jsonString + " to a MqttData object");
            return null;
        }
    }

    public String convertToJson(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            Log.error("Error in MqttData.convertToJson: could not convert MqttData object to JSON");
            return "";
        }
    }

    @Override
    public String toString() {
        return convertToJson();
    }
}