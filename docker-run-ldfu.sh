#!/bin/sh

docker 	run \
	-ti \
	-p 8888:8888 \
	--privileged \
	-v /dev/bus/usb:/dev/bus/usb \
	--entrypoint=/bin/bash \
	--rm fekepp/nirest \
	/usr/share/nirest/bin/nirest-ldfu
