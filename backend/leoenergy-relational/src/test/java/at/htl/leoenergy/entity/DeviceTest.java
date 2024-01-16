package at.htl.leoenergy.entity;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class DeviceTest {
    @Test
    public void givenNewDevice_whenUsingDefaultConstructor_thenPropertiesAreNull(){
        Device device = new Device();

        assertThat(device.getId()).isNull();
        assertThat(device.getDeviceId()).isEqualTo(0);
        assertThat(device.getManufacturerId()).isNull();
        assertThat(device.getMedium()).isNull();
        assertThat(device.getName()).isNull();
        assertThat(device.getSite()).isNull();
    }

    @Test
    public void givenDeviceDetails_whenUsingParameterizedConstructor_thenPropertiesAreSet(){
        int deviceId = 123;
        String manufacturerId = "M123";
        String medium = "Electricity";
        String name = "Device1";
        String site = "Site1";

        Device device = new Device(deviceId, manufacturerId, medium, name, site);

        assertThat(device.getDeviceId()).isEqualTo(deviceId);
        assertThat(device.getManufacturerId()).isEqualTo(manufacturerId);
        assertThat(device.getMedium()).isEqualTo(medium);
        assertThat(device.getName()).isEqualTo(name);
        assertThat(device.getSite()).isEqualTo(site);
    }

    @Test
    public void givenDevice_whenUsingSetters_thenPropertiesAreUpdated(){
        Device device = new Device();
        String newName = "NewDevice";

        device.setName(newName);

        assertThat(device.getName()).isEqualTo(newName);
    }

    @Test
    public void givenDevice_whenToString_thenCorrectFormat() {

        Device device = new Device();
        device.setName("Device1");

        String result = device.toString();

        assertThat(result).isEqualTo("Device1");
    }
}
