/* eslint-disable quotes */
import { env } from '@/environments/environment';
import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { Geocoder } from 'leaflet-control-geocoder';
import 'leaflet.locatecontrol';
import { Observable, Subscriber, Subscription } from 'rxjs';
import { MyLeafletService } from './my-leaflet.service';

/**
 * npm i 
 */
@Component({
  selector: 'app-my-leaflet',
  templateUrl: './my-leaflet.component.html',
  styleUrls: ['./my-leaflet.component.scss'],
})
export class MyLeafletComponent implements OnInit, AfterViewInit, OnDestroy {
  constructor(private myLeafletService: MyLeafletService) { }

  map!: L.Map;
  channel1$?: Subscription;
  channel2$?: Subscription;

  ngOnInit(): void { }

  ngOnDestroy(): void {
    if (this.channel1$) {
      this.channel1$.unsubscribe();
    }
    if (this.channel2$) {
      this.channel2$.unsubscribe();
    }
  }

  ngAfterViewInit(): void {
    // this.loadWhidbey();
    // this.loadLondon();
    this.loadLayersLondon();
  }

  London: L.LatLngLiteral = {
    lat: 51.505,
    lng: -0.09,
  };

  /**
   * Base Layers
   */
  private loadLayersLondon() {
    this.map = L.map('map').setView([this.London.lat, this.London.lng], 12); // 14 maximize NJ Map

    this.addGeocoder();

    this.useCurrentLocation();

    const osmLayer = this.loadOpenStreetMapLayer();
    const mapboxStreetLayer = this.loadMapboxStreetLayer();
    const mapboxSatelliteLayer = this.loadMapboxSatelliteLayer();
    // const wmsLayer = this.loadWMSLayer();
    const googleMapStreetLayer = this.loadGoogleMapLayer(
      env.googleMap.lyrs.street
    );
    const googleMapSatelliteLayer = this.loadGoogleMapLayer(
      env.googleMap.lyrs.satellite
    );
    const googleMapHybridLayer = this.loadGoogleMapLayer(
      env.googleMap.lyrs.hybrid
    );
    const googleMapTerrainLayer = this.loadGoogleMapLayer(
      env.googleMap.lyrs.terrain
    );

    const baseLayers: L.Control.LayersObject = {
      'Google Terrain': googleMapTerrainLayer,
      'Google Hybrid': googleMapHybridLayer,
      'Google Satellite': googleMapSatelliteLayer,
      'Google Street': googleMapStreetLayer,
      'Mapbox Satellite': mapboxSatelliteLayer,
      'Mapbox Street': mapboxStreetLayer,
      'Open Street Map': osmLayer,
      // 'Web Map Service': wmsLayer
    };

    const overlays: L.Control.LayersObject = {
      // "Circle": circle,
      // "Polygon": polygon,
      'Markers/Shapes Overlay Group': this.createOverlays(),
      'Image/Vedio/WMS Overlay Group': this.createOverlaysImageVedioWMS(),
      'GeoJSON Overlay Group': this.createOverlaysGeoJSON(),
      'My Vertix Group': this.createVertixOverlay(),
    };

    const layersOptions: L.Control.LayersOptions = {
      collapsed: false,
    };

    L.control.layers(baseLayers, overlays, layersOptions).addTo(this.map);
  }

  /**
   * GeoJSON: https://geojson.io
   *
   * Overlay Groups - GeoJSON
   */
  createOverlaysGeoJSON(): L.LayerGroup {
    const layerGroup: L.LayerGroup = L.layerGroup();

    /**
     * GeoJSON Markers
     */
    this.channel1$ = this.myLeafletService.getMarkers().subscribe({
      next: (data) => {
        const markersJSONOverlay: L.Layer = L.geoJSON(
          data,
          this.geoJSONOptions
        );
        layerGroup.addLayer(markersJSONOverlay);
        this.map.addLayer(layerGroup);
      },
    });

    /**
     * GeoJSON Shapes and Lines
     */
    this.channel2$ = this.myLeafletService.getShapesLines().subscribe({
      next: (data) => {
        const shapsLinesJSONOverlay: L.Layer = L.geoJSON(
          data,
          this.geoJSONOptions,
        );
        layerGroup.addLayer(shapsLinesJSONOverlay);
        this.map.addLayer(layerGroup);
      },
    });

    this.map.addLayer(layerGroup);

    return layerGroup;
  }

  /**
   * GeoJSONOptions
   */
  geoJSONOptions: L.GeoJSONOptions = {
    onEachFeature: (feature, layer) => {
      layer.bindPopup('Name: ' + feature.properties.name).addTo(this.map);
    },
    style: {
      fillColor: 'red',
      fillOpacity: 0.5,
      color: 'blue',
      weight: 3,
      opacity: 0.5,
      /** Markers */
    },
  };

  /**
   * Overlay Groups
   *
   * 1. Image Overlay
   * 2. Vedio Overlay
   * 3. WMS Overlay --- Not working
   */
  createOverlaysImageVedioWMS(): L.LayerGroup {
    const layerGroup: L.LayerGroup = L.layerGroup();

    /**
     * 1. Image Overlay
     */
    const imageOverlay: L.ImageOverlay = this.loadImageOverlay();

    /**
     * By adding 'imageOverlay' to 'layerGroup', 'imageOverlay' is controlled by 'layerGroup'.
     */
    layerGroup.addLayer(imageOverlay);

    /**
     * 2. Vedio Overlay
     */
    const vedioOverlay: L.VideoOverlay = this.loadVedioOverlay();

    /**
     * By adding 'vedioOverlay' to 'layerGroup', 'vedioOverlay' is controlled by 'layerGroup'.
     */
    layerGroup.addLayer(vedioOverlay);

    /**
     * 3. WMS Overlay - The endpoint does not work.
     */
    // this.loadWMSLayer();

    /**
     * Add layerGoup to map
     */
    this.map.addLayer(layerGroup);
    return layerGroup;
  }

  /////////////////////////
  /**
   * (nw: L.LatLngLiteral, metersEast: number, degreesCounterClockwiseFromTropicalLine: number): L.LatLngLiteral
   *
   * Create new vertix from (NW, metersEast, degreesCounterClockwiseFromTropicalLine)
   */
  metersEarthRaius = 6_378_137;
  piOverOneEighty = Math.PI / 180.0;

  makeVertix(nw: L.LatLngLiteral, metersEast: number, degreesCounterClockwiseFromTropicalLine: number): L.LatLngLiteral {
    const lat = nw.lat + metersEast * Math.sin(degreesCounterClockwiseFromTropicalLine * this.piOverOneEighty) / this.metersEarthRaius;
    const lng = nw.lng + metersEast * Math.cos(degreesCounterClockwiseFromTropicalLine * this.piOverOneEighty) / (this.metersEarthRaius * Math.cos(nw.lat * this.piOverOneEighty));
    return { lat: lat, lng: lng };
  }

  /**
   * createRectangle(nw: L.LatLngLiteral, meterEast: number, meterSouth: number, degreesCounterClockwiseFromTropicalLine: number)
   */
  createRectangle(nw: L.LatLngLiteral, meterEast: number, meterSouth: number, degreesCounterClockwiseFromTropicalLine: number): L.Polygon {
    const ne = this.makeVertix(nw, meterEast, degreesCounterClockwiseFromTropicalLine);
    const sw = this.makeVertix(nw, meterSouth, degreesCounterClockwiseFromTropicalLine - 90);
    const se = this.makeVertix(sw, meterEast, degreesCounterClockwiseFromTropicalLine);
    return L.polygon([nw, ne, se, sw]);
  }

  /////////////////////////

  createVertixOverlay(): L.LayerGroup {
    const layerGroup: L.LayerGroup = L.layerGroup();

    const nw: L.LatLngLiteral = { lat: 51.49661, lng: -0.23002 };
    const meterEast = 200_000;
    const meterSouth = 300_000;
    const degreesCounterClockwiseFromTropicalLine = 60;

    /**
     * To create 4 markers for each corner.
     */
    /*
    const ne = this.makeVertix(nw, meterEast, degreesCounterClockwiseFromTropicalLine);
    const sw = this.makeVertix(nw, meterSouth, degreesCounterClockwiseFromTropicalLine - 90);
    const se = this.makeVertix(sw, meterEast, degreesCounterClockwiseFromTropicalLine);

    const nwMarker = L.marker(nw, {
      icon: this.markerIcon,
    })
      .bindTooltip('NW Marker', { ...this.labelTooltipOptions, direction: 'left', offset: [-4, 0] })
      .bindPopup(
        '<b>Hello world!</b><br>I am NW marker.'
      );

    layerGroup.addLayer(nwMarker);

    const neMarker = L.marker(ne, {
      icon: this.markerIcon,
    })
      .bindTooltip('NE Marker', this.labelTooltipOptions)
      .bindPopup(
        '<b>Hello world!</b><br>I am NE marker.'
      );

    layerGroup.addLayer(neMarker);

    const seMarker = L.marker(se, {
      icon: this.markerIcon,
    })
      .bindTooltip('SE Marker', this.labelTooltipOptions)
      .bindPopup(
        '<b>Hello world!</b><br>I am SE marker.'
      );

    layerGroup.addLayer(seMarker);

    const swMarker = L.marker(sw, {
      icon: this.markerIcon,
    })
      .bindTooltip('SW Marker', { ...this.labelTooltipOptions, direction: 'left', offset: [-4, 0] })
      .bindPopup(
        '<b>Hello world!</b><br>I am SW marker.'
      );

    layerGroup.addLayer(swMarker);
    */

    /**
     * Polygon
     */
    const polygon = this.createRectangle(nw, meterEast, meterSouth, degreesCounterClockwiseFromTropicalLine)
      .bindPopup('I am calculated polygon.');

    layerGroup.addLayer(polygon);

    this.map.addLayer(layerGroup);
    return layerGroup;
  }

  /**
   * Current location
   * 
   * npm i leaflet.locate @types/leaflet.locatecontrol
   * 
   */
  useCurrentLocation() {

    /**
     * This line needs 'import "@types/leaflet.locatecontrol";'
     */
    L.control.locate(this.leafletLocateOption).addTo(this.map);

    /*
    this.map.addControl(LL.control.locate({
      locateOptions: {
        enableHighAccuracy: true
      }
    }));
    */
  }

  /**
   * This line needs 'import "@types/leaflet.locatecontrol";'
   */
  leafletLocateOption: L.Control.LocateOptions = {
    position: 'topleft',
    strings: {
      title: 'Show me where I am, yo!'
    },
    keepCurrentZoomLevel: false,
    locateOptions: {
      enableHighAccuracy: true
    }
  };

  /**
   * Geocoder - Search for address. Uses leaftlet nominatim service to support searches.
   * 
   * npm i leaflet-control-geocoder
   */
  addGeocoder() {
    /**
     * Geocoder - 'npm i leaflet-control-geocoder'
     */
    const geocoder = new Geocoder({
      position: 'topleft',
      collapsed: false,
      placeholder: 'Search...',
      showResultIcons: true,
      defaultMarkGeocode: true,
    });
    geocoder.addTo(this.map);

    /*

    /**
     * Geocoder - 'npm i leaflet-geosearch' - Not as good as 'npm i leaflet-control-geocoder'
     */
    /*
    import { GeoSearchControl, OpenStreetMapProvider } from 'leaflet-geosearch';

    const provider = new OpenStreetMapProvider();
    const searchControl = GeoSearchControl({
      provider: provider,
      autoComplete: true,
      autoCompleteDelay: 250,
      showMarker: true,
      showPopup: false,
      marker: {
        // optional: L.Marker    - default L.Icon.Default
        icon: new L.Icon.Default(),
        draggable: false,
      },
    });
    searchControl.addTo(this.map);

    "node_modules/leaflet-geosearch/dist/geosearch.css"
    */
  }

  /**
   * Overlay - LayerGroup
   *
   * Markers, Polygon, Circle, contextMenu, Pop-ups, Labels
   */
  createOverlays(): L.LayerGroup {
    const layerGroup: L.LayerGroup = L.layerGroup();

    /**
     * 3. Markers
     */
    const marker = L.marker([this.London.lat, this.London.lng], {
      icon: this.markerIcon,
    })
      .bindTooltip('Center Marker', this.labelTooltipOptions)
      .bindPopup(
        '<b>Hello world!</b><br>I am a popup at the center of the map.'
      )
      .openPopup();

    layerGroup.addLayer(marker);

    const bottomMarker = L.marker(
      [this.London.lat - 0.01, this.London.lng + 0.01],
      {
        icon: this.markerIcon,
      }
    )
      .bindTooltip('Bottom Marker', this.labelTooltipOptions)
      .bindPopup('I am another standalone popup.');

    layerGroup.addLayer(bottomMarker);

    const markers: any[] = [
      ['Marker A', this.London.lat + 0.01, this.London.lng + 0.003],
      ['Marker C', this.London.lat + 0.013, this.London.lng + 0.002],
      ['Marker B', this.London.lat + 0.013, this.London.lng + 0.005],
    ];

    for (let i = 0; i < markers.length; i++) {
      const marker = L.marker([markers[i][1], markers[i][2]], {
        icon: this.markerIcon,
      }).bindPopup(markers[i][0]);

      layerGroup.addLayer(marker);
    }

    /**
     * 4. Circle
     */
    const circle = L.circle([this.London.lat, this.London.lng - 0.03], {
      color: 'red',
      fillColor: '#f03',
      fillOpacity: 0.5,
      radius: 500,
    }).bindPopup('I am a circle.');

    layerGroup.addLayer(circle);

    /**
     * 5. Polygon
     */
    const polygon = L.polygon([
      [this.London.lat + 0.005, this.London.lng - 0.03],
      [this.London.lat + 0.01, this.London.lng - 0.03],
      [this.London.lat + 0.005, this.London.lng - 0.04],
    ]).bindPopup('I am a polygon.');

    layerGroup.addLayer(polygon);

    /**
     * 6. Standalone popup
     */
    const popup: L.Popup = L.popup()
      .setLatLng([this.London.lat - 0.015, this.London.lng])
      .setContent('I am a standalone popup.');

    layerGroup.addLayer(popup);

    /**
     * 7. Map click
     */
    this.map.on('click', this.onMapClick);

    /**
     * 8. Map right-click/contextmenu
     */
    this.map.on('contextmenu', this.contextmenu);

    this.map.addLayer(layerGroup);
    return layerGroup;
  }

  /**
   * Right-click contextmenu: Click position copy to clickboard
   */
  contextmenu = ($event: L.LeafletMouseEvent) => {
    const position = $event.latlng.lat + ', ' + $event.latlng.lng;

    const popup: L.Popup = L.popup();

    const content: HTMLElement = L.DomUtil.create('div');
    content.innerHTML = '(' + position + ')';
    content.addEventListener('click', ($event) => {
      navigator.clipboard
        .writeText(position)
        .then(() => {
          popup.setContent('Position data copied to clickboard.');
          setTimeout(() => {
            /**
             * 'popup.closePopup()' does not work. Use 'popup.remove()'
             */
            // popup.closePopup();
            popup.remove();
          }, 500);
        })
        .catch((err) => {
          console.log('Something went wrong', err);
        });
    });

    popup.setLatLng($event.latlng).setContent(content).openOn(this.map);
  };

  /**
   * Left-click $event handling
   */
  onMapClick = ($event: L.LeafletMouseEvent) => {
    // alert('You clicked the map at ' + $event.latlng);
  };

  /**
   * OpenStreetMap Layer
   */
  loadOpenStreetMapLayer() {
    return L.tileLayer(env.openStreet.url, {
      maxZoom: 18,
      attribution: env.openStreet.copyright,
    }).addTo(this.map);
  }

  /**
   * Google Map Layer - Free? This does not need userToken.
   *
   * lyrs: env.googleMap.lyrs
   */
  loadGoogleMapLayer(lyrs: string) {
    return L.tileLayer(env.googleMap.url.replace('{lyrs}', lyrs), {
      maxZoom: 20,
      subdomains: env.googleMap.subdomains,
      attribution: env.googleMap.attribution,
    }).addTo(this.map);
  }

  /**
   * Offline OpenStreetMap Layer - Use Luke Ma OpenStreetMap on local docker.
   */
  loadOfflineOpenStreetMapLayer() {
    return L.tileLayer(env.openStreetLuke.url, {
      maxZoom: 18,
      attribution: env.openStreetLuke.copyright,
    }).addTo(this.map);
  }

  /**
   * Web Map Service Tile
   *
   * The url does not work yet.
   */
  loadWMSLayer() {
    return L.tileLayer
      .wms('http://mesonet.agron.iastate.edu/cgi-bin/wms/nexrad/n0r.cgi', {
        layers: 'nexrad-n0r-900913',
        format: 'image/png',
        transparent: false,
        attribution: 'Weather data &copy; 2012 IEM Nexrad',
      })
      .addTo(this.map);
  }

  /**
   * Image Overlay
   */
  loadImageOverlay() {
    const imageUrl = 'assets/images/newark_nj_1922.jpg';
    const imageBounds: L.LatLngBoundsExpression = [
      [this.London.lat + 0.018, this.London.lng - 0.05],
      [this.London.lat - 0.018, this.London.lng + 0.05],
    ];
    return L.imageOverlay(imageUrl, imageBounds).addTo(this.map);
  }

  /**
   * Vedio Overlay
   */
  loadVedioOverlay() {
    const videoUrl = 'assets/images/patricia_nasa.webm';
    const videoBounds: L.LatLngBoundsExpression = [
      [this.London.lat - 0.01, this.London.lng + 0.022],
      [this.London.lat - 0.018, this.London.lng + 0.065],
    ];
    return L.videoOverlay(videoUrl, videoBounds).addTo(this.map);
  }

  /**
   * Mapbox Satellite Layer
   */
  loadMapboxSatelliteLayer() {
    return L.tileLayer(env.mapbox.url, {
      attribution: env.mapbox.attribution,
      maxZoom: 18,
      id: env.mapbox.idSatellite,
      tileSize: 512,
      zoomOffset: -1,
      accessToken: env.mapbox.accessToken,
    }).addTo(this.map);
  }

  /**
   * Mapbox Satellite Layer
   */
  loadMapboxStreetLayer() {
    return L.tileLayer(env.mapbox.url, {
      attribution: env.mapbox.attribution,
      maxZoom: 18,
      id: env.mapbox.idStreet,
      tileSize: 512,
      zoomOffset: -1,
      accessToken: env.mapbox.accessToken,
    }).addTo(this.map);
  }

  private getCurrentPosition(): any {
    return new Observable((observer: Subscriber<any>) => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position: any) => {
          observer.next({
            lat: position.coords.lat,
            lng: position.coords.lng,
          });
          observer.complete();
        });
      } else {
        observer.error();
      }
    });
  }

  /**
   * Marker Default
   */
  markerIcon: L.Icon = L.icon({
    iconUrl: 'assets/images/marker-icon.png',
    shadowUrl: 'assets/images/marker-shadow.png',
    iconSize: [25, 41], // size of the icon
    shadowSize: [41, 41],
    iconAnchor: [12, 41], // point of the icon which will correspond to marker location
    shadowAnchor: [13, 41],
    popupAnchor: [0, -38], // point from which the popup should open relative to the iconAnchor
  });

  /**
   * Marker Leaf
   */
  markerIconLeaf: L.Icon = L.icon({
    iconUrl: 'assets/images/leaf-green.png',
    shadowUrl: 'assets/images/leaf-shadow.png',
    iconSize: [38, 95], // size of the icon
    shadowSize: [50, 64], // size of the shadow
    iconAnchor: [22, 94], // point of the icon which will correspond to marker's location
    shadowAnchor: [4, 62], // the same for the shadow
    popupAnchor: [-3, -76], // point from which the popup should open relative to the iconAnchor
  });

  /**
   * Tooltip Options - right
   */
  labelTooltipOptions: L.TooltipOptions = {
    permanent: true,
    direction: 'right',
    offset: [4, 0],
    opacity: 0.9,
  };

  /**
   * Tooltip Options - auto
   */
  labelTooltipOptionsAuto: L.TooltipOptions = {
    permanent: true,
    direction: 'auto',
    offset: [4, 0],
    opacity: 0.9,
  };

  /**
   * TODO: Leaflet
   * 2. feature layers
   * 3. feature groups
   * 5. GIS
   */

  ////////////////////////////////////////////
  ////////////////////////////////////////////
  ////////////////////////////////////////////
  ////////////////////////////////////////////

  /**
   * Below: One Layer Only
   */
  LAX: L.LatLngLiteral = {
    lat: 33.94382,
    lng: -118.41345,
  };

  Whidbey: L.LatLngLiteral = {
    lat: 48.34185,
    lng: -122.66375,
  };

  private loadWhidbey(): void {
    this.map = L.map('map').setView([this.Whidbey.lat, this.Whidbey.lng], 14);

    this.loadMapLayer();

    /**
     * GeoJSON - Whidbey Polygon
     */
    this.channel1$ = this.myLeafletService.getWhidbeyPolygon().subscribe({
      next: (data) => {
        L.geoJSON(data, this.geoJSONOptions).addTo(this.map);
      },
    });

    const marker = L.marker([this.Whidbey.lat, this.Whidbey.lng], {
      icon: this.markerIcon,
    })
      .bindTooltip('Test Label', {
        permanent: true,
        direction: 'right',
      })
      .addTo(this.map);

    const circle = L.circle([this.Whidbey.lat, this.Whidbey.lng + 0.03], {
      color: 'red',
      fillColor: '#f03',
      fillOpacity: 0.5,
      radius: 500,
    }).addTo(this.map);

    const polygon = L.polygon([
      [this.Whidbey.lat + 0.01, this.Whidbey.lng + 0.01],
      [this.Whidbey.lat + 0.01, this.Whidbey.lng + 0.02],
      [this.Whidbey.lat + 0.02, this.Whidbey.lng + 0.01],
    ]).addTo(this.map);
  }

  /**
   * https://github.com/iamtekson/Leaflet-Basic
   * https://www.youtube.com/watch?v=ls_Eue1xUtY
   */
  private loadLondon(): void {
    this.map = L.map('map').setView([this.London.lat, this.London.lng], 14);

    /**
     * 1. Tile
     */
    this.loadMapLayer();

    /**
     * 2. Multiple Base Layers
     */

    /**
     * 3. Markers
     */
    const marker = L.marker([this.London.lat, this.London.lng], {
      icon: this.markerIcon,
    })
      .bindTooltip('Center Marker', this.labelTooltipOptions)
      .addTo(this.map);

    marker
      .bindPopup(
        '<b>Hello world!</b><br>I am a popup at the center of the map.'
      )
      .openPopup();

    const bottomMarker = L.marker(
      [this.London.lat - 0.01, this.London.lng + 0.01],
      {
        icon: this.markerIcon,
      }
    )
      .bindTooltip('Bottom Marker', this.labelTooltipOptions)
      .addTo(this.map);
    bottomMarker.bindPopup('I am another standalone popup.');

    const markers: any[] = [
      ['Marker A', this.London.lat + 0.01, this.London.lng + 0.003],
      ['Marker C', this.London.lat + 0.013, this.London.lng + 0.002],
      ['Marker B', this.London.lat + 0.013, this.London.lng + 0.005],
    ];

    for (let i = 0; i < markers.length; i++) {
      const marker = L.marker([markers[i][1], markers[i][2]], {
        icon: this.markerIcon,
      })
        .bindPopup(markers[i][0])
        .addTo(this.map);
    }

    /**
     * 4. Circle
     */
    const circle = L.circle([this.London.lat, this.London.lng - 0.03], {
      color: 'red',
      fillColor: '#f03',
      fillOpacity: 0.5,
      radius: 500,
    }).addTo(this.map);

    circle.bindPopup('I am a circle.');

    /**
     * 5. Polygon
     */
    const polygon = L.polygon([
      [this.London.lat + 0.005, this.London.lng - 0.03],
      [this.London.lat + 0.01, this.London.lng - 0.03],
      [this.London.lat + 0.005, this.London.lng - 0.04],
    ]).addTo(this.map);

    polygon.bindPopup('I am a polygon.');

    /**
     * 6. Standalone popup
     */
    L.popup()
      .setLatLng([this.London.lat - 0.015, this.London.lng])
      .setContent('I am a standalone popup.')
      .openOn(this.map);

    /**
     * 7. Map click
     */
    this.map.on('click', this.onMapClick);

    /**
     * 8. Map right-click/contextmenu
     */
    this.map.on('contextmenu', this.contextmenu);
  }

  /**
   * Load "One Layer" Map (Satellite or Street, Mapbox or OpenStreetMap)
   */
  loadMapLayer() {
    /**
     * OpenStreetMap Layer
     */
    this.loadOpenStreetMapLayer();

    /**
     * Mapbox Satellite Layer
     */
    // this.loadMapboxSatelliteLayer();

    /**
     * Mapbox Street Layer
     */
    // this.loadMapboxStreetLayer();
  }
}
