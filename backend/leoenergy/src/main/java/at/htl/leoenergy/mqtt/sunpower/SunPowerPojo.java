package at.htl.leoenergy.mqtt.sunpower;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import java.util.Date;

public class SunPowerPojo {
    //#region Members
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("TimeStamp")
    private Date timeStamp;

    @JsonProperty("Consumption_W")
    private int consumptionW;

    @JsonProperty("GridFeedIn_W")
    private int gridFeedInW;

    @JsonProperty("Production_W")
    private int productionW;

    @JsonProperty("Batt_remaining_Capacity_Wh")
    private int battRemainingCapacityWh;

    @JsonProperty("Batt_remaining_Capacity_%")
    private int battRemainingCapacityPercent;
    //#endregion

    //#region Getter & Setter
    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getConsumptionW() {
        return consumptionW;
    }

    public void setConsumptionW(int consumptionW) {
        this.consumptionW = consumptionW;
    }

    public int getGridFeedInW() {
        return gridFeedInW;
    }

    public void setGridFeedInW(int gridFeedInW) {
        this.gridFeedInW = gridFeedInW;
    }

    public int getProductionW() {
        return productionW;
    }

    public void setProductionW(int productionW) {
        this.productionW = productionW;
    }

    public int getBattRemainingCapacityWh() {
        return battRemainingCapacityWh;
    }

    public void setBattRemainingCapacityWh(int battRemainingCapacityWh) {
        this.battRemainingCapacityWh = battRemainingCapacityWh;
    }

    public int getBattRemainingCapacityPercent() {
        return battRemainingCapacityPercent;
    }

    public void setBattRemainingCapacityPercent(int battRemainingCapacityPercent) {
        this.battRemainingCapacityPercent = battRemainingCapacityPercent;
    }
    //#endregion

    public static SunPowerPojo fromJson(String json){
        try {
            return new ObjectMapper().readValue(json, SunPowerPojo.class);
        } catch (JsonProcessingException e) {
            Log.error("Error during converting json string to SunPowerPojo!");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "SunPowerPojo{" +
                "timeStamp=" + timeStamp +
                ", consumptionW=" + consumptionW +
                ", gridFeedInW=" + gridFeedInW +
                ", productionW=" + productionW +
                ", battRemainingCapacityWh=" + battRemainingCapacityWh +
                ", battRemainingCapacityPercent=" + battRemainingCapacityPercent +
                '}';
    }
}
