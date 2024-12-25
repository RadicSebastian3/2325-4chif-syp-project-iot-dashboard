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
  public currentSensorboxValues: Map<string, SensorBoxDTO> = new Map();
  private intervalId: any;

  private openFloors: Set<string> = new Set();
  private openRooms: Set<string> = new Set();

  constructor(private sbs: SensorboxService) {

  }

  //#region Service
  //PLEASE DON'T TOUCH!!!
  //loads all floors and rooms, and syncs the latest values of all rooms
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
          this.currentSensorboxValues.set(data.room, data);
          console.log(this.currentSensorboxValues);
        });
      });

    }, 10000);
  }

  ngOnDestroy() {
    this.intervalId ? clearInterval(this.intervalId) : null;
  }
  //#endregion
}
