# Depend on image with Ubuntu
FROM	ubuntu:trusty


# Set maintainers
MAINTAINER "Dmitri Rubinstein <dmitri.rubinstein@dfki.de>, Felix Leif Keppmann <felix.leif.keppmann@kit.edu>"


# Update Ubuntu
RUN	apt-get update && \
	apt-get upgrade -y


# Install common software properties
RUN	apt-get install -y software-properties-common


# Install dependencies for libfreenect from OpenKinect
RUN	apt-get update && apt-get install --no-install-recommends -y \
	git curl make \
	cmake gcc g++ \
	build-essential libtool \
	pkg-config \
	bzip2 libusb-1.0-0-dev freeglut3-dev \
	libxmu-dev libxi-dev python python-dev python-numpy cython


# Checkout libfreenect from OpenKinect
WORKDIR	/root
ADD	docker/libfreenect.patch .
RUN	git clone https://github.com/OpenKinect/libfreenect.git


# Patch libfreenect from OpenKinect
WORKDIR	/root/libfreenect
RUN	git apply ../libfreenect.patch || true


# Build and install libfreenect from OpenKinect
RUN	mkdir build
WORKDIR	/root/libfreenect/build
RUN	cmake -L -DCMAKE_INSTALL_PREFIX=/usr -DBUILD_PYTHON=on -DBUILD_OPENNI2_DRIVER=ON .. && \
	make && \
	make install


# Fetch and manuall install firmware
WORKDIR	/root/
RUN	python ./libfreenect/src/fwfetcher.py && \
	mv audios.bin /usr/share/libfreenect


# Add repository with recent Oracle Java packages
RUN	add-apt-repository -y ppa:webupd8team/java
RUN	apt-get update


# Accept license required to install Oracle Java 8 JDK
RUN	echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
RUN	echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections


# Install Oracle Java 8 SDK
RUN	apt-get install -y oracle-java8-installer


# Clone NIREST repository
WORKDIR	/root
RUN	git clone https://github.com/fekepp/nirest.git


# Manually install OpenNI/NiTE libraries
WORKDIR	/root/nirest
RUN	cp -v redistributables/OpenNI-Linux-x64-2.2.0.33/*.so /usr/lib/ && \
	cp -rv redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI2 /usr/lib/ && \
	cp -v redistributables/NiTE-Linux-x64-2.2.0.11/*.so /usr/lib/


# Manually link Freenect drivers for OpenNI to drivers' directory of OpenNI libraries
WORKDIR	/usr/lib/OpenNI2/Drivers
RUN	ln -s ../../OpenNI2-FreenectDriver/libFreenectDriver.* .


# Configure OpenNI for NIREST Server (see redistributables/OpenNI-Linux-x64-2.2.0.33/OpenNI.ini)
WORKDIR /root/nirest
RUN	echo "[Log]" > nirest-server/OpenNI.ini && \
	echo "Verbosity=3" >> nirest-server/OpenNI.ini && \
	echo "LogToConsole=0" >> nirest-server/OpenNI.ini && \
	echo "LogToFile=0" >> nirest-server/OpenNI.ini


# Configure NiTe for NIREST Server (see redistributables/NiTE-Linux-x64-2.2.0.11/NiTE.ini)
RUN	echo "[General]" > nirest-server/NiTE.ini && \
	echo "DataDir=../redistributables/NiTE-Linux-x64-2.2.0.11/NiTE2" >> nirest-server/NiTE.ini && \
	echo "" >> nirest-server/NiTE.ini && \
	echo "[Log]" >> nirest-server/NiTE.ini && \
	echo "Verbosity=3" >> nirest-server/NiTE.ini && \
	echo "LogToConsole=0" >> nirest-server/NiTE.ini && \
	echo "LogToFile=0" >> nirest-server/NiTE.ini


# Run Gradle test to trigger download of all dependencies
RUN	./gradlew test