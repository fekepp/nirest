# Base: Ubuntu 14.04 LTS
FROM	ubuntu:14.04


# Maintainers
MAINTAINER "Felix Leif Keppmann <felix.leif@keppmann.de>"


# Ubuntu: install "software-properties-common", required to add custom repositories
RUN	apt-get install -y \
		software-properties-common


# Ubuntu: add repository with recent Oracle Java packages
RUN	add-apt-repository -y ppa:webupd8team/java


# Ubuntu: accept license required to install Oracle Java 8 JDK
RUN	echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
RUN	echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections


# Ubuntu: install required packages
RUN	apt-get update && \
	apt-get install -y \
		build-essential \
		bzip2 \
		cmake \
		curl \
		cython \
		freeglut3-dev \
		g++ \
		gcc \
		git \
		libtool \
		libusb-1.0-0-dev \
		libxi-dev \
		libxmu-dev \
		make \
		oracle-java8-installer \
		pkg-config \
		python \
		python-dev \
		python-numpy && \
	rm -rf /var/lib/apt/lists/*


# NIREST: clone repository and switch to correct branch or tag
WORKDIR	/root
RUN	git clone https://github.com/fekepp/nirest.git && \
	cd nirest && \
	git checkout master


# Libfreenect: clone repository and switch to correct branch or tag
RUN	git clone https://github.com/OpenKinect/libfreenect.git && \
	cd libfreenect && \
	git checkout v0.5.3


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
