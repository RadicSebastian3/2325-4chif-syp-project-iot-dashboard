import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SensorboxOverviewComponent } from './sensorbox-overview.component';

describe('SensorboxOverviewComponent', () => {
  let component: SensorboxOverviewComponent;
  let fixture: ComponentFixture<SensorboxOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SensorboxOverviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SensorboxOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
