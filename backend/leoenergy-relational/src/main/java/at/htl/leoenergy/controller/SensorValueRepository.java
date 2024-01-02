package at.htl.leoenergy.controller;
import at.htl.leoenergy.entity.SensorValue;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Timestamp;
import java.util.List;

@ApplicationScoped
public class SensorValueRepository implements PanacheRepository<SensorValue> {

    public List<SensorValue> getBetweenTwoTimeStamps(Timestamp startDate, Timestamp endDate) {
        return list("timestamp between ?1 and ?2", startDate, endDate);
    }

}
