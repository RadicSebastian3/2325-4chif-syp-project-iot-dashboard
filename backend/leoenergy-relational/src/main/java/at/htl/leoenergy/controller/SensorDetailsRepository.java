package at.htl.leoenergy.controller;
import at.htl.leoenergy.entity.SensorDetails;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class SensorDetailsRepository implements PanacheRepository<SensorDetails> {

    public List<SensorDetails> getBetweenTwoTimeStamps(BigInteger startDate, BigInteger endDate) {
        long startTime = startDate.longValue() / 1000;
        long endTime = endDate.longValue() / 1000;


        return this.find("timestamp >= :startTime and timestamp <= :endTime",
                Parameters.with("startTime", startTime).and("endTime", endTime)).list();
    }



}