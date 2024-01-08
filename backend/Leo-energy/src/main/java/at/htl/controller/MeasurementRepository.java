package at.htl.controller;

import at.htl.entity.Device;
import at.htl.entity.Measurement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.util.List;
@ApplicationScoped
public class MeasurementRepository {
    @Inject
    EntityManager em;

    @Transactional
    public void save(Measurement measurement){
        if (findById(measurement.getId()) == null){
             em.persist(measurement);
        }
        else{
            em.merge(measurement);
        }
    }

    public List<Measurement> getAll() {
        return em.createQuery("select m from Measurement m",Measurement.class).getResultList();
    }

    public Measurement findById(BigInteger id){
        return em.find(Measurement.class,id);
    }

    public void delete(BigInteger id){
        em.remove(findById(id));
    }
    public void update(BigInteger id, Measurement measurement){
        Measurement m = findById(id);
        m.setDevice(measurement.getDevice());
        m.setName(measurement.getName());
        m.setValueType(measurement.getValueType());
        em.merge(m);
    }
}
