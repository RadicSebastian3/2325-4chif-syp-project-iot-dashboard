export class SensorBoxDTO {
  room: string;
  floor: string;
  co2?: number;
  humidity?: number;
  motion?: number;
  neopixel?: number;
  noise?: number;
  pressure?: number;
  rssi?: number;
  temperature?: number;
  timestamp: number;

  constructor(
    room: string,
    floor: string,
    timestamp: number,
    co2?: number,
    humidity?: number,
    motion?: number,
    neopixel?: number,
    noise?: number,
    pressure?: number,
    rssi?: number,
    temperature?: number
  ) {
    this.room = room;
    this.floor = floor;
    this.timestamp = timestamp;
    this.co2 = co2;
    this.humidity = humidity;
    this.motion = motion;
    this.neopixel = neopixel;
    this.noise = noise;
    this.pressure = pressure;
    this.rssi = rssi;
    this.temperature = temperature;
  }

  toString(): string {
    return `SensorBoxDTO { Room: ${this.room}, Floor: ${this.floor}, Timestamp: ${this.timestamp}, CO2: ${this.co2?.toFixed(
      2
    )}, Humidity: ${this.humidity?.toFixed(2)}, Motion: ${this.motion?.toFixed(
      2
    )}, Noise: ${this.noise?.toFixed(2)}, Pressure: ${this.pressure?.toFixed(
      2
    )}, RSSI: ${this.rssi?.toFixed(2)}, Temperature: ${this.temperature?.toFixed(
      2
    )} }`;
  }
}
