import { Component, OnInit, ViewChild } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';

@Component({
  selector: 'app-my-google-map',
  templateUrl: './my-google-map.component.html',
  styleUrls: ['./my-google-map.component.scss']
})
export class MyGoogleMapComponent implements OnInit {

  @ViewChild(GoogleMap) map!: GoogleMap;

  constructor() { }

  ngOnInit(): void {
  }

  mapOptions: google.maps.MapOptions = {
    center: { lat: 38.99872, lng: -77.25387 },
    zoom: 12,
    /*
    mapTypeId: 'satellite',
    disableDefaultUI: true,
    zoomControl: false,
    mapTypeControl: false,
    streetViewControl: false,
    fullscreenControl: false
    */
  };

  marker = {
    position: { lat: 38.9987208, lng: -77.2538699 },
  };

  marker1 = { position: { lat: 38.9987208, lng: -77.2538699 } };
  marker2 = { position: { lat: 39.7, lng: -76.0 } };
  marker3 = { position: { lat: 37.9, lng: -76.8 } };

  markers = [this.marker1, this.marker2, this.marker3];

  ngAfterViewInit() {

    /**
     * 2. Multi markers
     */
    const bounds = this.getBounds(this.markers);
    this.map.googleMap?.fitBounds(bounds);

    /**
     * 3. Street view
     */
    const streetView = this.map.getStreetView();
    streetView.setOptions({
      position: { lat: 38.9938386, lng: -77.2515373 },
      pov: { heading: 70, pitch: -10 },
    });
    streetView.setVisible(true);
  }

  /**
   * 2. Multi markers
   */
  getBounds(markers: any) {
    let north: any;
    let south: any;
    let east: any;
    let west: any;

    for (const marker of markers) {
      // set the coordinates to marker's lat and lng on the first run.
      // if the coordinates exist, get max or min depends on the coordinates.

      if (north) {
        north = Math.max(north, marker.position.lat);
      } else {
        north = marker.position.lat;
      }

      if (south) {
        south = Math.min(south, marker.position.lat);
      } else {
        south = marker.position.lat;
      }

      if (east) {
        east = Math.max(east, marker.position.lng);
      } else {
        east = marker.position.lng;
      }

      if (west) {
        west = Math.min(west, marker.position.lng);
      } else {
        west = marker.position.lng;
      }
    }

    const bounds = { north, south, east, west };

    return bounds;
  }
}
