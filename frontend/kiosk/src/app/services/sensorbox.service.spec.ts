import { TestBed } from '@angular/core/testing';

import { SensorboxService } from './sensorbox.service';

describe('SensorboxService', () => {
  let service: SensorboxService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SensorboxService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
