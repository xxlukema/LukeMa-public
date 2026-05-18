# OpenStreetMap

## 1. Google "openstreetmap docker"

[openstreetmap-tile-server]<https://github.com/Overv/openstreetmap-tile-server>

    gis clone https://github.com/Overv/openstreetmap-tile-server.git

## 2. Create volumes

    # docker-compose
    docker-compose down --remove-orphans
    
    # images
    docker image ls
    docker image prune
    
    # containers
    docker container ls
    docker container prune
    
    # volumes
    docker volume ls
    docker volume prune
    docker volume rm openstreetmap-data
    docker volume rm openstreetmap-rendered-tiles
    #
    docker volume create openstreetmap-data
    docker volume create openstreetmap-rendered-tiles
    
## 3. Fix `docker-compose.yml` File

As of 2022-04-06, the server is actually using postgres version 12, but in the `docker-compose.yml` file, it is configured to use
postgres version 14. This can be observed but running `docker run ... import` console logs.

Therefore, it is necessary to point the correct container postgres file to the volume:

    # docker-compose.yml:
    # Change '14' to '12' in '- openstreetmap-data:/var/lib/postgresql/14/main'.
    # Otherwise, after 'import' the maps, 'run' will not be able to mount correctly to database.
    volumes:
      - openstreetmap-data:/var/lib/postgresql/12/main

## 4. `docker run ... import` --- Import maps to postgres

    # docker-compose.yml:
    # Change the line: 'command: "run"' to 'command: "import"'
    command: "import"

    docker-compose up

## 5. `docker run ... import` --- Import maps to postgres

    # docker-compose.yml:
    # Change the line: 'command: "import"' to 'command: "run"'
    command: "run"
    
    docker-compose up -d

Then, the tiles are served in <http://localhost:8080/tile/{z}/{x}/{y}.png>

## 6. Fix `leaflet-demo.html`

    # leaflet-demo.html
    # Change "L.tileLayer('http://localhost:8080/tile/{z}/{x}/{y}.png'" to "L.tileLayer('http://localhost:8080/tile/{z}/{x}/{y}.png"
    L.tileLayer('http://localhost:8080/tile/{z}/{x}/{y}.png', {

## 7. Open `leaflet-demo.html` with Firefox or Chrome

## 8. Download Raw Map Files

[GeoFabrik Home]<https://www.geofabrik.de/>

[GeoFabrik Download Servers]<https://www.geofabrik.de/data/download.html>

- 8.1 Download `*.osm.pbf` and `*.poly` to `C:\geofabrik\` from <https://download.geofabrik.de/>





