package at.htl.leoenergy.controller;

import at.htl.leoenergy.entity.Device;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeviceRepository implements PanacheRepository<Device> {
    public void save(Device device) {
        Device existingDevice = findById(device.getId());

        if (existingDevice == null) {
            persist(device);
        } else {
            existingDevice.setManufacturerId(device.getManufacturerId());
            existingDevice.setMedium(device.getMedium());
            existingDevice.setName(device.getName());
            existingDevice.setSite(device.getSite());
            getEntityManager().flush();
        }
    }


}
