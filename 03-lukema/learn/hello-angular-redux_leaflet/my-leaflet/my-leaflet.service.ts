import { CommonService } from '@/app/common/common.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';

@Injectable()
export class MyLeafletService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly commonService: CommonService
  ) {}

  getMarkers(): Observable<GeoJSON.GeoJsonObject> {
    return this.httpClient
      .get<any>('assets/geojson/markers.json')
      .pipe(catchError(this.commonService.handleError));
  }

  getShapesLines(): Observable<GeoJSON.GeoJsonObject> {
    return this.httpClient
      .get<any>('assets/geojson/shapes-lines.json')
      .pipe(catchError(this.commonService.handleError));
  }

  getWhidbeyPolygon(): Observable<GeoJSON.GeoJsonObject> {
    return this.httpClient
      .get<any>('assets/geojson/whidbey-polygon.json')
      .pipe(catchError(this.commonService.handleError));
  }
}
