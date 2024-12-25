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

  // check window status
  isWindowOpen(room: string): boolean {
    const data = this.currentSensorboxValues.get(room);

    if(!data) return false;

    const {co2, temperature, humidity} = data;

    const isCo2Low = co2 !== undefined && co2 < 400;
    const isTemperatureLow = temperature !== undefined && temperature < 15;
    const isHumidityLow = humidity !== undefined && humidity < 30;

    return (isCo2Low && isTemperatureLow) || (isCo2Low && isHumidityLow) || (isTemperatureLow && isHumidityLow);
  }

  toogleFloor(floor: string): void {
    this.openFloors.has(floor) ? this.openFloors.delete(floor) : this.openFloors.add(floor);
  }

  isFloorOpen(floor: string): boolean {
    return this.openFloors.has(floor);
  }

  toogleRoom(room: string): void {
    this.openRooms.has(room) ? this.openRooms.delete(room) : this.openRooms.add(room);
  }

  isRoomOpen(room: string): boolean {
    return this.openRooms.has(room);
  }

  getRoomsForFloor(floor: string): string[] {
    return this.rooms.filter((room) => this.currentSensorboxValues.get(room)?.floor === floor);
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
