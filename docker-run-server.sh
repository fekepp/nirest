#!/bin/bash

docker 	run \
	-ti \
	-p 8888:8888 \
	--privileged \
	-v /dev/bus/usb:/dev/bus/usb \
	--rm fekepp/nirest /bin/bash ./gradlew :nirest-server:run