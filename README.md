# Natural Interaction via REST (NIREST)
NIREST uses the [OpenNI2-FreenectDriver](https://github.com/OpenKinect/libfreenect/tree/master/OpenNI2-FreenectDriver) from [libfreenect](https://github.com/OpenKinect/libfreenect/) of the [OpenKinect Project](http://www.openkinect.org), [OpenNI](http://structure.io/openni), and NiTE.

This application provides body tracking for Kinect v1 as RDF read-only REST resources.

## Requirements
* Connected Microsoft Kinect v1
* Java 8
* Docker (optional for running the NIREST Docker container)

## Setup
All required commands are provides as shell scripts, which may be read for further investigation.

### Docker
[NIREST Docker images](https://hub.docker.com/r/fekepp/nirest) are automatically build at [Docker Hub](http://hub.docker.com).

* Based on latest Ubuntu image
* Installation of all dependencies
* Cloning of NIREST and libfreenect repositories
* Building and installation of libfreenect, including OpenNI2-FreenectDriver
* Installation of OpenNI and NiTE middleware
* Configuration of OpenNI and NiTE

#### Docker Image
Pull the latest docker image.

    ./docker-pull.sh

#### Docker Helpers
Run the docker image and start the NIREST server, which will be available at "http://localhost:8888".

    ./docker-run-server.sh

Run the docker image and start a Bash session for manual intervention.

    ./docker-run-shell.sh

#### Docker Build
For manual local build of the docker image, clone the GitHub repository of NIREST and build the Docker image as usual.

    ./docker-build.sh

### Gradle
Run NIREST server directly via Gradle using pre-compiled OpenNI2-FreenectDriver.

    ./gradle-run-server.sh
