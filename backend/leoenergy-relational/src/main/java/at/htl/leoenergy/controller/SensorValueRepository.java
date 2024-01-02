package at.htl.leoenergy.controller;
import at.htl.leoenergy.entity.SensorValue;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SensorValueRepository implements PanacheRepository<SensorValue> {

}
