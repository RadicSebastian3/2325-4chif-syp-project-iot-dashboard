package at.htl.controller;

import at.htl.entity.Device;
import at.htl.entity.Measurement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DeviceRepository {

    @Inject
    EntityManager em;

     @Transactional
    public Device save(Device device){
        return em.merge(device);
    }

    public List<Device> getAll() {
        return em.createQuery("select d from Device d",Device.class).getResultList();
    }

    public Device findById(Long id){
        return em.find(Device.class,id);
    }

    public void delete(Long id){
        em.remove(findById(id));
    }
    @Transactional
    public void update(Long id, Device device){
        Device d = findById(id);
        d.setName(device.getName());
        em.merge(d);
    }
}
