import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '@environments/environment';
import { Boat } from '@app/models/boat';

@Injectable({ providedIn: 'root' })
export class BoatService {

  readonly RESOURCE_PATH: string = '/boats';

  constructor(private http: HttpClient) { }

  public getAllBoats(): Observable<Boat[]> {
    return this.http.get<Boat[]>(`${environment.apiUrl}${this.RESOURCE_PATH}`);
  }

  public createBoat(boatData: Boat): Observable<Boat> {
    return this.http.post<Boat>(`${environment.apiUrl}${this.RESOURCE_PATH}`, boatData);
  }

  public getBoatById(id: number): Observable<Boat> {
    return this.http.get<Boat>(`${environment.apiUrl}${this.RESOURCE_PATH}/${id}`);
  }

  public updateBoat(boatData: Boat): Observable<Boat> {
    return this.http.put<Boat>(`${environment.apiUrl}${this.RESOURCE_PATH}/${boatData.id}`, boatData);
  }

  public deleteBoatById(id: number): Observable<any> {
    return this.http.delete(`${environment.apiUrl}${this.RESOURCE_PATH}/${id}`);
  }
}