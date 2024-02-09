package at.htl.leoenergy.Mqtt;

import java.time.LocalDateTime;

public class SunpowerData {
    private LocalDateTime timeStamp;
    private double consumptionW;
    private double gridFeedInW;
    private double productionW;
    private double battRemainingCapacityWh;
    private double battRemainingCapacityPercent;

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getConsumptionW() {
        return consumptionW;
    }

    public void setConsumptionW(double consumptionW) {
        this.consumptionW = consumptionW;
    }

    public double getGridFeedInW() {
        return gridFeedInW;
    }

    public void setGridFeedInW(double gridFeedInW) {
        this.gridFeedInW = gridFeedInW;
    }

    public double getProductionW() {
        return productionW;
    }

    public void setProductionW(double productionW) {
        this.productionW = productionW;
    }

    public double getBattRemainingCapacityWh() {
        return battRemainingCapacityWh;
    }

    public void setBattRemainingCapacityWh(double battRemainingCapacityWh) {
        this.battRemainingCapacityWh = battRemainingCapacityWh;
    }

    public double getBattRemainingCapacityPercent() {
        return battRemainingCapacityPercent;
    }

    public void setBattRemainingCapacityPercent(double battRemainingCapacityPercent) {
        this.battRemainingCapacityPercent = battRemainingCapacityPercent;
    }
}
