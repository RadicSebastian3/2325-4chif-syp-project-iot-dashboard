package at.htl.leoenergy.controller;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
public class UnitConverterTest {
    @Test
    public void given1000Watt_whenConvertKilowatt_thenReturn1Kilowatt(){
        String unit = "W";
        Double value = 1000.0;
        Double expectedValue = 1.0;

        Double result = UnitConverter.convertToKilowatt(unit, value);

        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void given1000WattHourValue_whenConvertToKilowatt_thenReturn1Kilowatt(){
        String unit = "Wh";
        Double value = 1000.0;
        Double expectedValue = 1.0;

        Double result = UnitConverter.convertToKilowatt(unit, value);

        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void given3600CubicMetersPerHour_whenConvertToKilowatt_thenReturn1Kilowatt(){
        String unit = "m^3/h";
        Double value = 3600.0;
        Double expectedValue = 1.0000008;

        Double result = UnitConverter.convertToKilowatt(unit, value);

        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void givenUnknownUnit_whenConvertToKilowatt_thenZeroKilowattValue(){
        String unit = "XYZ";
        Double value = 500.0;
        Double expectedValue = 0.0;

        Double result = UnitConverter.convertToKilowatt(unit, value);

        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void givenAmpereValue_whenConvertToKilowatt_thenZeroKilowattValue(){
        String unit = "A";
        Double value = 10.0;
        Double expectedValue = 0.0;

        Double result = UnitConverter.convertToKilowatt(unit, value);

        assertThat(result).isEqualTo(expectedValue);
    }
}
