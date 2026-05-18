import { DecimalPipe } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { Whidbey2Service } from './whidbey2.service';

interface StatusData {
  id?: number,
  name: string,
  health: string,
  changed: boolean,
  toolTip?: string
}

interface TerminalData {
  statusData: StatusData,
  options: google.maps.PolygonOptions,
  location: google.maps.LatLng[],
  marker: google.maps.LatLng,
  markerOptions: google.maps.MarkerOptions
}

@Component({
  standalone: false,
  selector: 'app-whidbey2',
  templateUrl: './whidbey2.component.html',
  styleUrls: ['./whidbey2.component.scss']
})
export class Whidbey2Component implements OnInit, OnDestroy {

  @ViewChild(GoogleMap) map!: GoogleMap;

  apiLoaded!: Observable<boolean>;
  channel$?: Subscription;
  interval$?: NodeJS.Timeout;
  loading = false;
  terms: TerminalData[] = [];

  constructor(private readonly httpClient: HttpClient,
    private readonly decimalPipe: DecimalPipe,
    public router: Router,
    private readonly whidbey2Service: Whidbey2Service,) {
    /*
    this.channel$ = this.whidbey2Service.initLoad().subscribe({
      next: reponse => { },
      error: (error: HttpErrorResponse) => {
        console.error('HttpErrorResponse', error.status, error);
      }
    });
    */
  }

  whidbey: google.maps.LatLngLiteral = { lat: 48.34185, lng: -122.66375 };
  zoom = 14;

  /**
   * Locations and Shapes
   */
  airfield = this.makeVertices({ lat: 48.34921, lng: -122.67269 }, 280, 1960, -35);
  refuel = this.makeVertices({ lat: 48.33648, lng: -122.65350 }, 270, 210, 0);
  n976 = this.makeVertices({ lat: 48.34510, lng: -122.67743 }, 100, 300, 0);
  h15 = this.makeVertices({ lat: 48.34665, lng: -122.67035 }, 70, 95, 0);
  h12 = this.makeVertices({ lat: 48.34660, lng: -122.67322 }, 170, 105, -35);
  h6outside = this.makeVertices({ lat: 48.33950, lng: -122.66317 }, 28, 105, -35);
  h6 = this.makeVertices({ lat: 48.33862, lng: -122.66500 }, 165, 105, -35);

  ngOnInit(): void {
    console.log('11111111111111 onInit');

    this.terms = [];
    const airfieldTerminalData = {
      statusData: { name: 'Air Field', health: 'Healthy', changed: false },
      options: this.polygonOptionsOff,
      location: this.airfield,
      marker: this.getCenter(this.airfield),
      markerOptions: {
        draggable: false,
        label: { text: 'Air Field', color: 'black' }
      }
    };
    this.terms.push(airfieldTerminalData);

    const refuelTerminalData = {
      statusData: { name: 'Refueling', health: 'Healthy', changed: false },
      options: this.polygonOptionsOff,
      location: this.refuel,
      marker: this.getCenter(this.refuel),
      markerOptions: {
        draggable: false,
        label: { text: 'Refueling', color: 'black' }
      }
    };
    this.terms.push(refuelTerminalData);

    const n976TerminalData = {
      statusData: { name: '#976', health: 'Healthy', changed: false },
      options: this.polygonOptionsOff,
      location: this.n976,
      marker: this.getCenter(this.n976),
      markerOptions: {
        draggable: false,
        label: { text: '#976', color: 'black' }
      }
    };
    this.terms.push(n976TerminalData);

    const h15TerminalData = {
      statusData: { name: 'H15', health: 'Healthy', changed: false },
      options: this.polygonOptionsOff,
      location: this.h15,
      marker: this.getCenter(this.h15),
      markerOptions: {
        draggable: false,
        label: { text: 'H 15', color: 'black' }
      }
    };
    this.terms.push(h15TerminalData);

    const h12TerminalData = {
      statusData: { name: 'H12', health: 'Healthy', changed: false },
      options: this.polygonOptionsOff,
      location: this.h12,
      marker: this.getCenter(this.h12),
      markerOptions: {
        draggable: false,
        label: { text: 'H 12', color: 'black' }
      }
    };
    this.terms.push(h12TerminalData);

    const h6TerminalData = {
      statusData: { name: 'H6', health: 'Healthy', changed: false },
      options: this.polygonOptionsOff,
      location: this.h6,
      marker: this.getCenter(this.h6),
      markerOptions: {
        draggable: false,
        label: { text: 'H 6', color: 'black' }
      }
    };
    this.terms.push(h6TerminalData);

    const h6outsideTerminalData = {
      statusData: { name: 'H6 Outside', health: 'Healthy', changed: false },
      options: this.polygonOptionsOff,
      location: this.h6outside,
      marker: this.getCenter(this.h6outside),
      markerOptions: {
        draggable: false,
        label: { text: 'H 6 Outside', color: 'black' }
      }
    };
    this.terms.push(h6outsideTerminalData);
  }

  refresh(checkLoading: boolean) {
    if (checkLoading) {
      this.loading = true;
    }
    this.channel$ = this.whidbey2Service.getStatus().subscribe({
      next: (data) => {
        console.log('Whidbey2Component', data);
        if (checkLoading) {
          this.loading = false;
        }
        data.forEach(element => {
          this.terms.forEach(term => {
            if (element.siteName === term.statusData.name) {
              if (!term.statusData.id) {
                term.statusData.id = element.siteId;
              }
              this.updateData(term.statusData, element.health);
            }
          });
        });

        console.log('------------ terms', this.terms);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Whidbey2Component', error);
        if (checkLoading) {
          this.loading = false;
        }
      }
    });
  }

  ngAfterViewInit() {
    console.log('22222222222222222 ngAfterViewInit');

    /*
    this.refresh(true);
    this.interval$ = setInterval(() => {
      this.refresh(false);
    }, 30_000);
    */
  }

  mouseover(terminalData: TerminalData) {
    terminalData.options = { ...this.polygonOptionsOn };
  }

  mouseout(terminalData: TerminalData) {
    terminalData.options = { ...this.polygonOptionsOff };
  }

  detailsUrl = '/home/dashboards/whidbey2/details/details';

  toDetails(id: number | undefined) {

    console.log('--- clicked on id:', id);

    /*
    if (id) {
      this.router.navigate([this.detailsUrl], {
        queryParams:
        {
          id: id
        }
      });
    }
    */
  }

  updateData(dest: StatusData, health: string) {
    if (health) {
      if (health === dest.health) {
        dest.changed = false;
      } else {
        dest.changed = true;
        // dest.health = health;
        dest.health = 'Major';
      }
      dest.toolTip = '<div>' + dest.name + ': ' + dest.health + '</div>';
      dest = { ...dest };
    }
  }

  mapOptions: google.maps.MapOptions = {
    mapTypeId: 'satellite',
    streetViewControl: false,
  };

  polygonOptionsOff: google.maps.PolygonOptions = {
    clickable: true,
    strokeColor: '#398bf7',
    fillColor: 'lightblue',
    strokeWeight: 1.5,
    fillOpacity: 0.3,
    zIndex: 200
  };

  polygonOptionsOn: google.maps.PolygonOptions = {
    clickable: true,
    strokeColor: '#6610f2',
    fillColor: 'lightblue',
    strokeWeight: 3,
    fillOpacity: 0.1,
    zIndex: 1000
  };

  markerOptions: google.maps.MarkerOptions = { draggable: false, clickable: true };

  makeVertices(nw: google.maps.LatLngLiteral, metersEast: number, metersSouth: number, degreesClockwise: number): google.maps.LatLng[] {
    const ne = google.maps.geometry.spherical.computeOffset(
      nw, metersEast, 90 + degreesClockwise
    );
    const se = google.maps.geometry.spherical.computeOffset(
      ne, metersSouth, 180 + degreesClockwise
    );
    const sw = google.maps.geometry.spherical.computeOffset(
      se, metersEast, 270 + degreesClockwise
    );

    console.log('nw ne se sw', nw, ne, se, sw);

    return [new google.maps.LatLng(nw), ne, se, sw];
  }

  /////////////////////////
  /**
   * Begin makeVertix
   *
   * Calculate new vertix from (NW, metersEast, degreesCounterClockwiseFromTropicalLine)
   *
   *  const metersEast = 2_000;
   *  const degreesCounterClockwiseFromTropicalLine = 120;
   *  // const nw: google.maps.LatLngLiteral = { lat: 48, lng: -122 };
   *  const nw: google.maps.LatLngLiteral = { lat: 0, lng: 0 };
   *  console.log('------------- nw', nw.lat, nw.lng);
   *  const ne = google.maps.geometry.spherical.computeOffset(nw, metersEast, 90 + degreesCounterClockwiseFromTropicalLine);
   *  console.log('---- ne', this.decimalPipe.transform(ne.lat(), '1.7'), this.decimalPipe.transform(ne.lng(), '1.7'));
   *  const myNe = this.makeVertix(nw, metersEast, degreesCounterClockwiseFromTropicalLine);
   *  console.log('-- myNe', this.decimalPipe.transform(myNe.lat(), '1.7'), this.decimalPipe.transform(myNe.lng(), '1.7'));
   */
  metersEarthRaius = 6_378_137;
  oneMeterToDegrees = 1.0 / this.metersEarthRaius * 180.0 / Math.PI;

  makeVertix(nw: google.maps.LatLngLiteral, meters: number, degreesCounterClockwiseFromTropicalLine: number): google.maps.LatLng {
    const lat = nw.lat - meters * this.oneMeterToDegrees * Math.sin(degreesCounterClockwiseFromTropicalLine / 180.0 * Math.PI);
    const lng = nw.lng + meters * this.oneMeterToDegrees / Math.cos(nw.lat / 180.0 * Math.PI) * Math.cos(degreesCounterClockwiseFromTropicalLine / 180.0 * Math.PI);
    return new google.maps.LatLng(lat, lng);
  }
  /**
   * End makeVertix()
   */
  /////////////////////////

  getCenter(bounds: google.maps.LatLng[] | google.maps.LatLngLiteral[]) {
    const afBounds = new google.maps.LatLngBounds();
    bounds.forEach(element => {
      afBounds.extend(element);
    });
    return afBounds.getCenter();
  }

  ngOnDestroy() {
    if (this.channel$) {
      this.channel$.unsubscribe();
    }
    if (this.interval$) {
      clearInterval(this.interval$);
    }
  }
}
