import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {SensorBoxDTO} from "../model/SensorBoxDTO";

@Injectable({
  providedIn: 'root'
})
export class SensorboxService {
  private BASE_URL: string = 'https://vm23.htl-leonding.ac.at/api/sensorbox';
  private headers = new HttpHeaders()
                                      .set('Access-Control-Allow-Origin', '*')
                                      .set('Accept', 'application/json');

  constructor(private http: HttpClient) { }

  public getAllFloors(): Observable<string[]> {
    return this.http.get<string[]>(`${this.BASE_URL}/floors`, {headers: this.headers});
  }

  public getAllRooms(): Observable<string[]> {
    return this.http.get<string[]>(`${this.BASE_URL}/rooms`, {headers: this.headers});
  }

  public getLatestValuesOfRoom(room: string): Observable<SensorBoxDTO> {
    return this.http.get<SensorBoxDTO>(`${this.BASE_URL}/latest-values/${room}`, {headers: this.headers});
  }
}
