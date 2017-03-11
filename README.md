# mapjfx-demo

a demo application showing the possibilites of the [mapjfx](http://www.sothawo.com/projects/mapjfx/) component. The
application's scene is built using an fxml file.

More Information about the project can be found at [the sothawo website](http://www.sothawo.com/projects/mapjfx-demo/).

## license

 Copyright 2014-2015 Peter-Josef Meisch (pj.meisch@sothawo.com)

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
application directory structure in _target/mapjfx-demo_. You can copy this whole directory to a machine which has
Java8 installed and start the program by using the scripts found in the _bin_ directory. Or start it on the machine
where you did the build  using

_(cd target/mapjfx-demo && ./bin/mapjfx-demo)_

or all in one:

_mvn package && (cd target/mapjfx-demo && ./bin/mapjfx-demo)_


## version history

the version of this artifact is always the same as the one of the integrated mapjfx component.

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

