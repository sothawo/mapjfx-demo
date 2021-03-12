# mapjfx-demo

a demo application showing the possibilites of the [mapjfx](http://www.sothawo.com/projects/mapjfx/) component. The
application's scene is built using an fxml file.

More Information about the project can be found at [the sothawo website](http://www.sothawo.com/projects/mapjfx-demo/).

## license

 Copyright 2014-2020 Peter-Josef Meisch (pj.meisch@sothawo.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

## building the program

this project is built using mvn. The library jar is created by running `mvn package`. This creates as well an
application directory structure in _target/mapjfx-demo_. You can copy this whole directory to a machine which has Java 11 installed and start the program by using the scripts found in the _bin_ directory. 
Or start it on the machine where you did the build  using

_(cd target/mapjfx-demo && ./bin/mapjfx-demo)_

or all in one:

_mvn package && (cd target/mapjfx-demo && ./bin/mapjfx-demo)_

## version history

the version of this artifact is always the same as the one of the integrated mapjfx component.

### 3.0.1

* use mapjfx 3.0.1

### 3.0.0

* use Java/JavaFX 15

### 2.15.3

* use mapjfx 2.15.3

### 2.15.2

* use mapjfx 2.15.2

### 2.15.1

* use mapjfx 2.15.1

### 2.15.0

* add circle around the station
* use mapjfx 2.15.0

### 2.14.0

* MAPFX-80 - Marker rotation.
* use mapjfx 2.14.0

### 2.13.1

* use mapjfx 2.13.1

### 2.12.0

* use mapjfx 2.12.0

### 2.11.0

* add new map types
* use mapjfx 2.11.0

### 2.10.2

* use mapjfx 2.10.2

### 2.9.0

* use mapjfx 2.9.0

### 2.7.3

* use mapjfx 2.7.3

### 2.7.2

* use mapjfx 2.7.2

### 2.7.1

* use different WMS sample server
* use mapjfx 2.7.1

### 2.7.0

* set interactive mode to true
* use mapjfx 2.7.0

### 2.6.0

* show normalized coordinate in status bar on map click 

### 2.5.2-1

* added animation of click marker

### 2.5.2

* use mapjfx 2.5.2

### 2.5.1

* use mapjfx 2.5.1

### 2.5.0

* use mapjfx 2.5.0

### 2.4.0

* slf4j and logback

### 2.3.0

* add code to handle wgs84 commandline parameter and set initial map projection
* use mapjfx 2.3.0

### 2.2.0

* add polygon drawing code
* use mapjfx 2.2.0

### 2.1.1

* use mapjfx 2.1.1

### 2.1.0

* use mapjfx 2.1.0

### 2.0.0

* switch to OpenJDK 11 and OpenJFX 11

### 1.19.0

* add XYZ map source (contribution from [Erik Jaehne](https://github.com/s3erjaeh))

### 1.18.0

* log MAP_POINTER_MOVED event
* use mapjfx 1.18.0-SNAPSHOT

### 1.17.0

* display bounding extent event
* use mapjfx 1.17.0

### 1.16.0

* add map type Stamen Watercolor
* use mapjfx 1.16.0

### 1.15.0

* use mapjfx 1.15.0

### 1.14.0

* use mapjfx 1.14.0

### 1.13.2

* use mapjfx 1.13.2

### 1.13.1

* use mapjfx 1.13.1

### 1.13.0

* use mapjfx 1.13.0

### 1.12.2

* do not use cache as default
* use mapjfx 1.12.2

### 1.12.1

* use mapjfx 1.12.1

### 1.12.0

* added MAP_EXTENT handling

### 1.11.0

* using mapjfx 1.11.0

### 1.10.0

* using mapjfx 1.10.0
* added two WMS servers

### 1.9.0

* using mapjfx 1.9.0

### 1.8.2

* using mapjfx 1.8.2

### 1.8.1

* using mapjfx 1.8.1

current development version

### 1.7.3

* using mapjfx 1.7.3
* removed MapQuest see [ol issue #5484](https://github.com/openlayers/ol3/issues/5484)

### 1.7.2

* using mapjfx 1.7.2

### 1.7.1

* using mapjfx 1.7.1

### 1.7.0

* using offline cache

### 1.6.0-SNAPSHOT

* added Labels

### 1.5.0

* added entry for BingMaps API Key and corresponding selection.

### 1.3.0-SNAPSHOT

* added markers, predefined and custom
* added single click handler

### 1.1.1

* added configurable options to the left of the window

### 1.1.0

* intial version

