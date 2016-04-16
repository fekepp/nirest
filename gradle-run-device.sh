#!/bin/bash

LD_LIBRARY_PATH=../redistributables/OpenNI-Linux-x64-2.2.0.33/:../redistributables/NiTE-Linux-x64-2.2.0.11/ ./gradlew :nirest-device:run
