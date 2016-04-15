# Base: Ubuntu
FROM	ubuntu:trusty


# Maintainers
MAINTAINER "Felix Leif Keppmann <felix.leif.keppmann@kit.edu> / Dmitri Rubinstein <dmitri.rubinstein@dfki.de>"


# Ubuntu: install software properties common required to add custom repositories
RUN	apt-get install -y \
	software-properties-common


# Ubuntu: add repository with recent Oracle Java packages
RUN	add-apt-repository -y ppa:webupd8team/java


# Ubuntu: accept license required to install Oracle Java 8 JDK
RUN	echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
RUN	echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections


# Ubuntu: update and upgrade
RUN	apt-get update && \
	apt-get upgrade -y


# Ubuntu: install required packages
RUN	apt-get update && \
	apt-get install -y \
		git \
		curl make \
		cmake gcc g++ \
		build-essential libtool \
		pkg-config \
		bzip2 libusb-1.0-0-dev freeglut3-dev \
		libxmu-dev libxi-dev python python-dev python-numpy cython \
		oracle-java8-installer


# NIREST/Libfreenect: clone Git repositories of NIREST and libfreenect
WORKDIR	/root
RUN	git clone https://github.com/fekepp/nirest.git
RUN	git clone https://github.com/OpenKinect/libfreenect.git


# Libfreenect: build and install
RUN	mkdir build
WORKDIR	/root/libfreenect/build
RUN	cmake -L -DCMAKE_INSTALL_PREFIX=/usr -DBUILD_OPENNI2_DRIVER=ON .. && \
	make && \
	make install


# Libfreenect: fetch and manually install firmware
WORKDIR	/root/
RUN	python ./libfreenect/src/fwfetcher.py && \
	mv audios.bin /usr/share/libfreenect


# OpenNI/NiTE: manually install libraries
WORKDIR	/root/nirest
RUN	cp -v redistributables/OpenNI-Linux-x64-2.2.0.33/*.so /usr/lib/ && \
	cp -v redistributables/NiTE-Linux-x64-2.2.0.11/*.so /usr/lib/ && \
	cp -rv redistributables/NiTE-Linux-x64-2.2.0.11/NiTE2 /usr/lib/


# NIREST: run tests with Gradle to trigger download of all dependencies
RUN	./gradlew test


# NIREST: configure OpenNI for NIREST server (see redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini)
WORKDIR /root/nirest
RUN	echo "[Drivers]" > /usr/lib/OpenNI.ini && \
	echo "Repository=/usr/lib/OpenNI2-FreenectDriver" >> /usr/lib/OpenNI.ini && \
	echo "" >> /usr/lib/OpenNI.ini && \
	echo "[Log]" >> /usr/lib/OpenNI.ini && \
	echo "Verbosity=3" >> /usr/lib/OpenNI.ini && \
	echo "LogToConsole=0" >> /usr/lib/OpenNI.ini && \
	echo "LogToFile=0" >> /usr/lib/OpenNI.ini


# NIREST: configure NiTE for NIREST Server (see redistributables/NiTE-Linux-x64-2.2.0.11/NiTE.ini)
RUN	echo "[General]" > nirest-server/NiTE.ini && \
	echo "DataDir=/usr/lib/NiTE2" >> nirest-server/NiTE.ini && \
	echo "" >> nirest-server/NiTE.ini && \
	echo "[Log]" >> nirest-server/NiTE.ini && \
	echo "Verbosity=3" >> nirest-server/NiTE.ini && \
	echo "LogToConsole=0" >> nirest-server/NiTE.ini && \
	echo "LogToFile=0" >> nirest-server/NiTE.ini
