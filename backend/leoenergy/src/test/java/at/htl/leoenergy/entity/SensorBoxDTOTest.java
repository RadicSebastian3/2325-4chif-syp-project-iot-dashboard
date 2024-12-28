package at.htl.leoenergy.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SensorBoxDTOTest {

    @Test
    void deleteFaultyValues() {
        // Test case with valid values
        SensorBoxDTO validDto = new SensorBoxDTO();
        validDto.setCo2(500.0);
        validDto.setHumidity(50.0);
        validDto.setNoise(100.0);
        validDto.setPressure(1013.25);
        validDto.setTemperature(20.0);

        SensorBoxDTO resultValid = SensorBoxDTO.deleteFaultyValues(validDto);
        assertEquals(500.0, resultValid.getCo2());
        assertEquals(50.0, resultValid.getHumidity());
        assertEquals(100.0, resultValid.getNoise());
        assertEquals(1013.25, resultValid.getPressure());
        assertEquals(20.0, resultValid.getTemperature());

        // Test case with invalid values (less than acceptable range)
        SensorBoxDTO invalidDtoLow = new SensorBoxDTO();
        invalidDtoLow.setCo2(-10.0);
        invalidDtoLow.setHumidity(-5.0);
        invalidDtoLow.setNoise(-1.0);
        invalidDtoLow.setPressure(-100.0);
        invalidDtoLow.setTemperature(-300.0);

        SensorBoxDTO resultInvalidLow = SensorBoxDTO.deleteFaultyValues(invalidDtoLow);
        assertEquals(Double.NaN, resultInvalidLow.getCo2());
        assertEquals(Double.NaN, resultInvalidLow.getHumidity());
        assertEquals(Double.NaN, resultInvalidLow.getNoise());
        assertEquals(Double.NaN, resultInvalidLow.getPressure());
        assertEquals(Double.NaN, resultInvalidLow.getTemperature());

        // Test case with invalid values (greater than acceptable range)
        SensorBoxDTO invalidDtoHigh = new SensorBoxDTO();
        invalidDtoHigh.setCo2(1000001.0);
        invalidDtoHigh.setHumidity(101.0);
        invalidDtoHigh.setNoise(195.0);
        invalidDtoHigh.setPressure(Double.MAX_VALUE);
        invalidDtoHigh.setTemperature(1000.0); // Valid temperature

        SensorBoxDTO resultInvalidHigh = SensorBoxDTO.deleteFaultyValues(invalidDtoHigh);
        assertEquals(Double.NaN, resultInvalidHigh.getCo2());
        assertEquals(Double.NaN, resultInvalidHigh.getHumidity());
        assertEquals(Double.NaN, resultInvalidHigh.getNoise());
        assertEquals(Double.MAX_VALUE, resultInvalidHigh.getPressure()); // Pressure can be high
        assertEquals(1000.0, resultInvalidHigh.getTemperature());
    }
}