#!/bin/bash

docker 	run \
	-ti \
	-p 8888:8888 \
	--privileged \
	-v /dev/bus/usb:/dev/bus/usb \
	-v /tmp/.X11-unix:/tmp/.X11-unix \
	-e DISPLAY=$DISPLAY \
	--rm fekepp/nirest /bin/bash
