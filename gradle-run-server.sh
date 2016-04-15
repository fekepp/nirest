#!/bin/bash


# Configure OpenNI for NIREST Device (see redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini)
echo "[Drivers]" > redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini
echo "Repository=../OpenKinect-OpenNI2-FreenectDriver-0.5.3" >> redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini
echo "" >> redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini
echo "[Log]" >> redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini
echo "Verbosity=3" >> redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini
echo "LogToConsole=0" >> redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini
echo "LogToFile=0" >> redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini


# Configure NiTe for NIREST Device (see redistributables/NiTE-Linux-x64-2.2.0.11/NiTE.ini)
echo "[General]" > nirest-server/NiTE.ini
echo "DataDir=../redistributables/NiTE-Linux-x64-2.2.0.11/NiTE2" >> nirest-server/NiTE.ini
echo "" >> nirest-server/NiTE.ini
echo "[Log]" >> nirest-server/NiTE.ini
echo "Verbosity=3" >> nirest-server/NiTE.ini
echo "LogToConsole=0" >> nirest-server/NiTE.ini
echo "LogToFile=0" >> nirest-server/NiTE.ini


LD_LIBRARY_PATH=../redistributables/OpenNI-Linux-x64-2.2.0.33/:../redistributables/NiTE-Linux-x64-2.2.0.11/ ./gradlew :nirest-server:run