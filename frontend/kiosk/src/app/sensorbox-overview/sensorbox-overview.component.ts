import {Component, OnDestroy, OnInit} from '@angular/core';
import {SensorBoxDTO} from "../model/SensorBoxDTO";
import {SensorboxService} from "../services/sensorbox.service";

@Component({
  selector: 'app-sensorbox-overview',
  standalone: true,
  imports: [],
  templateUrl: './sensorbox-overview.component.html',
  styleUrl: './sensorbox-overview.component.css'
})
export class SensorboxOverviewComponent implements OnInit, OnDestroy{
  public floors: string[] = [];
  public rooms: string[] = [];
  public currentSensorboxValues: SensorBoxDTO[] = [];
  private intervalId: any;

  constructor(private sbs: SensorboxService) {

  }

  ngOnInit() {
    this.sbs.getAllFloors().subscribe((data) => {
      this.floors = data;
      console.log("All floors: " + this.floors);
    });

    this.sbs.getAllRooms().subscribe((data) => {
      this.rooms = data;
      console.log("All rooms: " + this.rooms);
    });

    this.intervalId = setInterval(() => {
      this.rooms.forEach(room => {
        this.sbs.getLatestValuesOfRoom(room).subscribe((data) => {
          console.log(data);
        })
      })
    })
  }

  ngOnDestroy() {
    this.intervalId ? clearInterval(this.intervalId) : null;
  }
}
