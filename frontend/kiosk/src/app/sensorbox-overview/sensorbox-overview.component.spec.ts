import { TestBed } from '@angular/core/testing';
import { SensorboxOverviewComponent } from './sensorbox-overview.component';
import {SensorboxService} from "../services/sensorbox.service";

describe('SensorboxOverviewComponent Room Status Methods', () => {
  let component: SensorboxOverviewComponent;

  // Mock für SensorboxService
  const mockSensorboxService = {};

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SensorboxOverviewComponent], // Standalone-Komponente importieren
      providers: [
        { provide: SensorboxService, useValue: mockSensorboxService }, // Mock bereitstellen
      ],
    }).compileComponents();

    const fixture = TestBed.createComponent(SensorboxOverviewComponent);
    component = fixture.componentInstance;
  });

  describe('isRoomOptimal', () => {
    it('should return true when all values are within green thresholds', () => {
      component.settings = {
        co2: { greenMax: 800, yellowMax: 1200 },
        temperature: { greenMin: 20, greenMax: 22 },
        humidity: { greenMin: 40, greenMax: 60 },
      };

      const room = 'Room1';
      component.currentSensorboxValues.set(room, {
        co2: 700,
        temperature: 21,
        humidity: 50,
      } as any);

      expect(component.isRoomOptimal(room)).toBeTrue();
    });

    it('should return false when a value exceeds green thresholds', () => {
      component.settings = {
        co2: { greenMax: 800, yellowMax: 1200 },
        temperature: { greenMin: 20, greenMax: 22 },
        humidity: { greenMin: 40, greenMax: 60 },
      };

      const room = 'Room2';
      component.currentSensorboxValues.set(room, {
        co2: 900, // Exceeds green threshold
        temperature: 21,
        humidity: 50,
      } as any);

      expect(component.isRoomOptimal(room)).toBeFalse();
    });
  });

  describe('isRoomAcceptable', () => {
    it('should return true when a value is in the yellow range and none are critical', () => {
      component.settings = {
        co2: { greenMax: 800, yellowMax: 1200 },
        temperature: { greenMin: 20, greenMax: 22 },
        humidity: { greenMin: 40, greenMax: 60 },
      };

      const room = 'Room3';
      component.currentSensorboxValues.set(room, {
        co2: 850, // Yellow range
        temperature: 21,
        humidity: 50,
      } as any);

      expect(component.isRoomAcceptable(room)).toBeTrue();
    });

    it('should return false when a value is critical', () => {
      component.settings = {
        co2: { greenMax: 800, yellowMax: 1200 },
        temperature: { greenMin: 20, greenMax: 22 },
        humidity: { greenMin: 40, greenMax: 60 },
      };

      const room = 'Room4';
      component.currentSensorboxValues.set(room, {
        co2: 1300, // Critical
        temperature: 21,
        humidity: 50,
      } as any);

      expect(component.isRoomAcceptable(room)).toBeFalse();
    });
  });

  describe('isRoomCritical', () => {
    it('should return true when a value exceeds yellow thresholds', () => {
      component.settings = {
        co2: { greenMax: 800, yellowMax: 1200 },
        temperature: { greenMin: 20, greenMax: 22 },
        humidity: { greenMin: 40, greenMax: 60 },
      };

      const room = 'Room5';
      component.currentSensorboxValues.set(room, {
        co2: 1300, // Exceeds yellow threshold
        temperature: 21,
        humidity: 50,
      } as any);

      expect(component.isRoomCritical(room)).toBeTrue();
    });

    it('should return false when all values are within green thresholds', () => {
      component.settings = {
        co2: { greenMax: 800, yellowMax: 1200 },
        temperature: { greenMin: 20, greenMax: 22 },
        humidity: { greenMin: 40, greenMax: 60 },
      };

      const room = 'Room6';
      component.currentSensorboxValues.set(room, {
        co2: 700,
        temperature: 21,
        humidity: 50,
      } as any);

      expect(component.isRoomCritical(room)).toBeFalse();
    });
  });

  describe('Custom Threshold Tests', () => {
    it('should adapt logic when thresholds are changed', () => {
      component.settings = {
        co2: { greenMax: 600, yellowMax: 1000 },
        temperature: { greenMin: 18, greenMax: 24 },
        humidity: { greenMin: 30, greenMax: 50 },
      };

      const room = 'Room7';
      component.currentSensorboxValues.set(room, {
        co2: 700, // Yellow with new settings
        temperature: 20,
        humidity: 40,
      } as any);

      expect(component.isRoomOptimal(room)).toBeFalse(); // Not optimal
      expect(component.isRoomAcceptable(room)).toBeTrue(); // Acceptable
      expect(component.isRoomCritical(room)).toBeFalse(); // Not critical
    });
  });
});
