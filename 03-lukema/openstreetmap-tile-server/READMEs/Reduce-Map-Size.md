# Reduce OSM Map Size

## Reducing osm.pbf size
As we see that osm size are so large, it will take hours while importing on our posgreSQL database. So, reducing the osm file size by 
removing extra details is a good option to choose.
For that, we can use a tool called [osmosis](https://wiki.openstreetmap.org/wiki/Osmosis) which can be downloaded from 
this [ github repository](https://github.com/openstreetmap/osmosis/releases/tag/0.48.3).

We can understand the openstreet map [admin level](https://wiki.openstreetmap.org/wiki/Key:admin_level) concept and based on that we can 
reduce the map size choosing only selected admin levels and [nodes](https://wiki.openstreetmap.org/wiki/Node).

For example, if we choose Mexico osm file from [North America](http://download.geofabrik.de/north-america.html) which is 505 MB, then we
can reduce it to 8.88 MB rejecting ways and nodes and accepting up to admin level 8.

**On Windows:**

```powershell

./osmosis.bat --read-pbf file="mexico-latest.osm.pbf" --tf accept-nodes "admin_level=1,2,3,4,5,6,7,8"  --tf reject-ways --tf reject-relations --read-pbf file="mexico-latest.osm.pbf" --tf accept-ways "admin_level=1,2,3,4,5,6,7,8"  --tf reject-relations --used-node --read-pbf file="mexico-latest.osm.pbf" --tf accept-relations "admin_level=1,2,3,4,5,6,7,8" --used-way --used-node --merge --merge --write-pbf granularity=10000 "mexico-latest-converted 8.osm.pbf"

```

**On Linux:**

```bash

./osmosis --read-pbf file="mexico-latest.osm.pbf" --tf accept-nodes "admin_level=1,2,3,4,5,6,7,8"  --tf reject-ways --tf reject-relations --read-pbf file="mexico-latest.osm.pbf" --tf accept-ways "admin_level=1,2,3,4,5,6,7,8"  --tf reject-relations --used-node --read-pbf file="mexico-latest.osm.pbf" --tf accept-relations "admin_level=1,2,3,4,5,6,7,8" --used-way --used-node --merge --merge --write-pbf granularity=10000 "mexico-latest-converted 8.osm.pbf"

```