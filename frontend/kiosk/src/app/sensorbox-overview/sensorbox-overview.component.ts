import {Component, OnDestroy, OnInit} from '@angular/core';
import {SensorBoxDTO} from "../model/SensorBoxDTO";
import {SensorboxService} from "../services/sensorbox.service";
import {NgClass, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import Chart from "chart.js/auto";

@Component({
  selector: 'app-sensorbox-overview',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage,
    NgClass
  ],
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
  private doughnutChart: Chart | null = null;

  private lastGreenRooms = 0;
  private lastRedRooms = 0;


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

  toggleFloor(floor: string): void {
    this.openFloors.has(floor) ? this.openFloors.delete(floor) : this.openFloors.add(floor);
  }

  isFloorOpen(floor: string): boolean {
    return this.openFloors.has(floor);
  }

  toggleRoom(room: string): void {
    this.openRooms.has(room) ? this.openRooms.delete(room) : this.openRooms.add(room);
  }

  isRoomOpen(room: string): boolean {
    return this.openRooms.has(room);
  }

  getRoomsForFloor(floor: string): string[] {
    return this.rooms.filter((room) => this.currentSensorboxValues.get(room)?.floor === floor);
  }

  getRoomCount(floor: string): number {
    return this.getRoomsForFloor(floor).length;
  }

  getGreenRoomCount(floor: string): number {
    return this.getRoomsForFloor(floor).filter((room) => !this.isWindowOpen(room)).length;
  }

  getRedRoomCount(floor: string): number {
    return this.getRoomsForFloor(floor).filter((room) => this.isWindowOpen(room)).length;
  }

  getTemperatureIcon(temperature?: number): string {
    return temperature && temperature < 18
      ? 'assets/images/temperature-cold.png'
      : 'assets/images/temperature-hot.png';
  }

  getHumidityIcon(humidity?: number): string | null {
    if (humidity === undefined || humidity === null) return null;
    if (humidity < 30) return 'assets/images/humidity-low.png';
    if (humidity <= 60) return 'assets/images/humidity-medium.png';
    if (humidity > 60) return 'assets/images/humidity-high.png';
    return null;
  }

  getCo2Class(co2?: number): string {
    if (co2 === undefined || co2 === null) return '';
    if (co2 < 400) return 'low';
    if (co2 <= 1000) return 'medium';
    return 'high';
  }

  getNoiseClass(noise?: number): string {
    if (noise === undefined || noise === null) return '';
    if (noise < 50) return 'low';
    if (noise <= 70) return 'medium';
    return 'high';
  }


  private loadFakeData(): void {
    this.floors = ['EG', 'OG1', 'OG2'];
    this.rooms = [
      'EG01', 'EG02', 'OG101', 'OG102', 'OG201', 'OG202'
    ];

    const fakeData: SensorBoxDTO[] = [
      new SensorBoxDTO('EG01', 'EG', Date.now(), 350, 45, 1, 100, 40, 1010, -50, 20),
      new SensorBoxDTO('EG02', 'EG', Date.now(), 1200, 25, 0, 200, 60, 1005, -55, 12),
      new SensorBoxDTO('OG101', 'OG1', Date.now(), 800, 50, 1, 150, 45, 1020, -40, 22),
      new SensorBoxDTO('OG102', 'OG1', Date.now(), 300, 55, 1, 150, 35, 1015, -60, 18),
      new SensorBoxDTO('OG201', 'OG2', Date.now(), 700, 30, 1, 100, 50, 1018, -45, 19),
      new SensorBoxDTO('OG202', 'OG2', Date.now(), 1500, 20, 0, 200, 70, 1002, -65, 14)
    ];

    fakeData.forEach(data => {
      this.currentSensorboxValues.set(data.room, data);
    });

    console.log('Fake data loaded:', this.currentSensorboxValues);
  }

  createDoughnutChart(): void {
    const totalGreenRooms = this.rooms.filter(room => !this.isWindowOpen(room)).length;
    const totalRedRooms = this.rooms.filter(room => this.isWindowOpen(room)).length;

    // Überprüfen, ob sich die Daten geändert haben
    if (totalGreenRooms === this.lastGreenRooms && totalRedRooms === this.lastRedRooms) {
      console.log('Keine Änderung bei den Daten. Chart wird nicht aktualisiert.');
      return;
    }

    // Aktuelle Werte speichern
    this.lastGreenRooms = totalGreenRooms;
    this.lastRedRooms = totalRedRooms;

    const ctx = document.getElementById('roomStatusChart') as HTMLCanvasElement;

    // Zerstöre den existierenden Chart, falls vorhanden
    if (this.doughnutChart) {
      this.doughnutChart.destroy();
    }

    if (ctx) {
      this.doughnutChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: ['Fenster geschlossen', 'Fenster geöffnet'],
          datasets: [{
            label: 'Raum Status',
            data: [totalGreenRooms, totalRedRooms],
            backgroundColor: ['#28a745', '#dc3545']
          }]
        },
        options: {
          plugins: {
            legend: {
              position: 'bottom',
              labels: {
                font: {
                  size: 12
                }
              }
            },
            title: {
              display: true,
              text: 'Status der Räume',
              font: {
                size: 14
              }
            }
          },
          responsive: true,
          maintainAspectRatio: false,
          layout: {
            padding: {
              top: 10,
              bottom: 10
            }
          }
        }
      });
      console.log('Chart wurde erfolgreich aktualisiert.');
    }
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

      this.createDoughnutChart();

    }, 10000);
  }

  ngOnDestroy() {
    this.intervalId ? clearInterval(this.intervalId) : null;
    if (this.doughnutChart) {
      this.doughnutChart.destroy();
    }
  }
  //#endregion
}
