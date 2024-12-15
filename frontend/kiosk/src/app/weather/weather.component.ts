import { Component, OnInit } from '@angular/core';
import { WeatherService } from '../services/weather.service';
import weatherCodes from '../../assets/weather-codes.json';
import iconMapping from '../../assets/icon-mapping.json';
import {DatePipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-weather',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    NgOptimizedImage,
    DatePipe,
  ],
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit{
  weatherData: any;
  hourlyData: any[] = [];
  dailyData: any;
  latitude = 48.2797;  // Breitengrad von Leonding
  longitude = 14.2533; // Längengrad von Leonding
  errorMessage?: string;
  monthlyWeatherSummary: { sunny: number; cloudy: number; rainy: number } = { sunny: 0, cloudy: 0, rainy: 0 };
  weatherCodes: { [key: string]: string } = weatherCodes;
  iconMapping: { [key: string]: string } = iconMapping;
  currentMonth: string = '';

  constructor(private weatherService: WeatherService) { }

  ngOnInit(): void {
    this.getWeather();
    this.getMonthlyWeather();
    this.setCurrentMonth();
  }

  getWeather(): void {
    this.weatherService.getWeatherForecast(this.latitude, this.longitude)
      .subscribe(
        data => {
          this.weatherData = data;
          console.log('Daten von der Wetter-API:', this.weatherData);
          this.processData();
        },
        error => {
          this.errorMessage = 'Fehler beim Abrufen der Wetterdaten';
          console.error(this.errorMessage, error);
        }
      );
  }

  processData(): void {
    const now = new Date();
    const currentHour = now.getHours();
    const currentDate = now.toISOString().split('T')[0];

    this.hourlyData = this.weatherData.hourly.time
      .map((time: string, index: number) => {
        return {
          time: new Date(time),
          temperature: this.weatherData.hourly.temperature_2m[index],
          weathercode: this.weatherData.hourly.weathercode[index]
        };
      })
      .filter((item: any) => {
        // Nur die Daten für heute und die nächsten Stunden (bis +4 Stunden)
        return item.time.getDate() === now.getDate() &&
          item.time.getHours() >= currentHour &&
          item.time.getHours() <= currentHour + 4;
      });

    console.log('Gefilterte stündliche Daten:', this.hourlyData);

    // Begrenzung auf die nächsten 5 Tage
    this.dailyData = {
      time: this.weatherData.daily.time.slice(0, 5),
      temperature_2m_max: this.weatherData.daily.temperature_2m_max.slice(0, 5),
      temperature_2m_min: this.weatherData.daily.temperature_2m_min.slice(0, 5),
      weathercode: this.weatherData.daily.weathercode.slice(0, 5)
    };

    console.log('Tägliche Wetterdaten für die nächsten 5 Tage:', this.dailyData);
  }

  getWeatherDescription(code: number): string {
    return this.weatherCodes[code.toString()] || 'Unbekanntes Wetter';
  }

  getWeatherIcon(code: number): string {
    return this.iconMapping[code.toString()] || 'fas fa-question';
  }

  getMonthlyWeather():void{
    const today = new Date();
    const startDate = new Date(today.getFullYear(), today.getMonth(),1).toISOString().split('T')[0];
    const endDate = new Date(today.getFullYear(), today.getMonth() + 1, 0).toISOString().split('T')[0];

    this.weatherService.getMonthlyWeatherForecast(this.latitude, this.longitude, startDate, endDate).subscribe(
      (data) => {
        this.aggregateMonthlyWeather(data.daily.weathercode);
        this.renderMonthlyWeatherChart();
      },
      (error) => {
        this.errorMessage = 'Fehler beim Abrufen der Monatswetterdaten';
        console.error(this.errorMessage, error);
      }
    );
  }

  aggregateMonthlyWeather(weatherCodes: number[]): void{
    this.monthlyWeatherSummary = { sunny: 0, cloudy: 0, rainy: 0 };

    weatherCodes.forEach((code) => {
      if ([0, 1].includes(code)) this.monthlyWeatherSummary.sunny++;
      else if ([2, 3].includes(code)) this.monthlyWeatherSummary.cloudy++;
      else if ([61, 63, 65].includes(code)) this.monthlyWeatherSummary.rainy++;
    });

    console.log('Wetterzusammenfassung für den Monat:', this.monthlyWeatherSummary);
  }

  renderMonthlyWeatherChart(): void {
    const ctx = document.getElementById('monthlyWeatherChart') as HTMLCanvasElement;

    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: ['Sonnig', 'Bewölkt', 'Regnerisch'],
        datasets: [
          {
            data: [
              this.monthlyWeatherSummary.sunny,
              this.monthlyWeatherSummary.cloudy,
              this.monthlyWeatherSummary.rainy
            ],
            backgroundColor: ['#FFD700', '#C0C0C0', '#87CEEB'] // Farben für sonnig, bewölkt, regnerisch
          }
        ]
      },
      options: {
        plugins: {
          legend: {
            position: 'bottom'
          },
          title: {
            display: true,
            text: 'Wetterverteilung im aktuellen Monat'
          }
        },
        responsive: true
      }
    });
  }

  setCurrentMonth(): void {
    const today = new Date();
    const monthNames = [
      'Januar', 'Februar', 'März', 'April', 'Mai', 'Juni',
      'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'
    ];
    this.currentMonth = monthNames[today.getMonth()]; // Aktueller Monat
  }
}
