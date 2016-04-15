Natural Interaction via REST (NIREST)
======
## About
NIREST uses the [OpenNI2-FreenectDriver](https://github.com/OpenKinect/libfreenect/tree/master/OpenNI2-FreenectDriver) from [libfreenect](https://github.com/OpenKinect/libfreenect/) of the [OpenKinect Project](http://www.openkinect.org), [OpenNI](http://structure.io/openni), and NiTE.

## Requirements

### Hardware
* Connected Microsoft Kinect v1

## Docker
Docker images for NIREST are automatically build.
* Based on an Ubuntu image.
* All runtime and build dependencies are installed
* NIREST and OpenKinect libfreenect repositories are cloned
* OpenKinect libfreenect is build and installed
* OpenKinect libfreenect OpenNI Drivers are build and installed
* OpenNI/NiTE are installed and OpenKinect libfreenect OpenNI Drivers are linked
* Ini files for OpenNI/NiTE are generated to point to the correct drivers and data directories

### Helper scripts
Use the helper script to pull/run the docker image and start NIREST.
NIREST will be available at "http://localhost:8888".

    ./docker-run-nirest.sh

Pull/run the docker image and start a Bash session.

    ./docker-run-nirest-shell.sh

### Manually

#### Pull image
Pull the latest docker image.

    docker pull fekepp/nirest

#### Run image, start Gradle
Pull/run the docker image and start NIREST.
NIREST will be available at "http://localhost:8888/".

    docker
      run \
      -ti \
      -p 8888:8888 \
      --privileged \
      -v /dev/bus/usb:/dev/bus/usb \
      -v /tmp/.X11-unix:/tmp/.X11-unix \
      -e DISPLAY=$DISPLAY \
      --rm fekepp/nirest /bin/bash ./gradlew :nirest-server:run

#### Run image, start Bash, start Gradle
Pull/run the docker image and start a Bash session.

    docker
      run \
      -ti \
      -p 8888:8888 \
      --privileged \
      -v /dev/bus/usb:/dev/bus/usb \
      -v /tmp/.X11-unix:/tmp/.X11-unix \
      -e DISPLAY=$DISPLAY \
      --rm fekepp/nirest /bin/bash ./gradlew :nirest-server:run

Use Gradle wrapper to start NIREST.
NIREST will be available at "http://localhost:8888/".

    ./gradlew :nirest-server:run
#### Build image
Build the Docker image as usual.

    git clone https://github.com/fekepp/nirest/
    cd nirest
    docker build .
