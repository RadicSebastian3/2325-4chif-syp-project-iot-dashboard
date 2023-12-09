package at.htl.controller;

import at.htl.entity.Device;
import at.htl.entity.Measurement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
@ApplicationScoped
public class MeasurementRepository {
    @Inject
    EntityManager em;

    @Transactional
    public Measurement save(Measurement measurement){
        return em.merge(measurement);
    }

    public List<Measurement> getAll() {
        return em.createQuery("select m from Measurement m",Measurement.class).getResultList();
    }

    public Measurement findById(Long id){
        return em.find(Measurement.class,id);
    }

    public void delete(Long id){
        em.remove(findById(id));
    }
    public void update(Long id, Measurement measurement){
        Measurement m = findById(id);
        m.setDevice(measurement.getDevice());
        m.setName(measurement.getName());
        m.setValueType(measurement.getValueType());
        em.merge(m);
    }
}
