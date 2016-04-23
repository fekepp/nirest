package net.fekepp.nirest.device.controllers;

import java.util.List;

import org.openni.Device;
import org.openni.DeviceInfo;
import org.openni.OpenNI;
import org.openni.PixelFormat;
import org.openni.SensorInfo;
import org.openni.SensorType;
import org.openni.VideoMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.primesense.nite.NiTE;
import com.primesense.nite.UserTracker;

import net.fekepp.nirest.controllers.AbstractController;
import net.fekepp.nirest.device.DeviceHelper;
import net.fekepp.nirest.device.listeners.UserTrackerListener;
import net.fekepp.nirest.model.DepthSensor;
import net.fekepp.nirest.model.User;

/**
 * @author "Felix Leif Keppmann"
 */
public class DeviceController extends AbstractController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Cache<String, DepthSensor> devices = Caffeine.newBuilder().build();

	private Cache<String, User> users = Caffeine.newBuilder().build();

	@Override
	protected void startup() throws Exception {

		// Initialize OpenNI
		logger.info("Initialize OpenNI");
		OpenNI.initialize();

		// Initialize NiTE
		logger.info("Initialize NiTE");
		NiTE.initialize();

		// Log supported sensor types
		logger.info("Supported sensor types");
		for (SensorType sensorType : SensorType.values()) {
			logger.info("\t{}", sensorType.name());
		}

		// Log supported pixel formats
		logger.info("Supported pixel formats");
		for (PixelFormat pixelFormat : PixelFormat.values()) {
			logger.info("\t{}", pixelFormat.name());
		}

		// Log available devices
		logger.info("Available devices");
		List<DeviceInfo> deviceInfos = OpenNI.enumerateDevices();
		for (int index = 0; index < deviceInfos.size(); index++) {
			DeviceInfo deviceInfo = deviceInfos.get(index);
			Device device = Device.open(deviceInfo.getUri());
			logDevice(device);
			logSensor(device);
		}

		// Add all devices to device map
		DeviceHelper.addDevices(devices);

		// FIXME Temporary add fake devices to device map
		// DeviceHelper.addDevicesFake(devices);

		// If not no device exists do nothing
		if (OpenNI.enumerateDevices().size() == 0) {
			logger.info("Device not found > No device will be opened, no tracker and tracker listeners will be added");
		}

		// Else open the device and add listeners
		else {

			// Open device
			logger.info("Open first device");
			Device device = Device.open(OpenNI.enumerateDevices().get(0).getUri());
			device.getClass();
			logger.info("");

			// Add user tracker and user tracker listener
			logger.info("Add user tracker");
			UserTracker userTracker = UserTracker.create();
			UserTrackerListener userTrackerListener = new UserTrackerListener();
			userTrackerListener.setUsers(users);
			userTracker.addNewFrameListener(userTrackerListener);
			logger.info("");

		}

	}

	@Override
	protected void shutdown() throws Exception {

	}

	private void logDevice(Device device) {
		DeviceInfo deviceInfo = device.getDeviceInfo();
		logger.info("{}", deviceInfo.getUri());
		logger.info("{} > Name > {}", deviceInfo.getUri(), deviceInfo.getName());
		logger.info("{} > Vendor > {}", deviceInfo.getUri(), deviceInfo.getVendor());
		logger.info("{} > USB Product ID > {}", deviceInfo.getUri(), deviceInfo.getUsbProductId());
		logger.info("{} > USB Vendor ID > {}", deviceInfo.getUri(), deviceInfo.getUsbVendorId());
	}

	private void logSensor(Device device) {

		DeviceInfo deviceInfo = device.getDeviceInfo();

		for (SensorType sensorType : SensorType.values()) {

			SensorInfo sensorInfo = device.getSensorInfo(sensorType);

			if (sensorInfo != null) {

				logger.info("{} > {} Sensor", deviceInfo.getUri(), sensorType.name());

				for (VideoMode supportedVideoMode : sensorInfo.getSupportedVideoModes()) {

					logger.info("{} > {} Sensor > Resolution X > {}", deviceInfo.getUri(), sensorType.name(),
							supportedVideoMode.getResolutionX());
					logger.info("{} > {} Sensor > Resolution Y > {}", deviceInfo.getUri(), sensorType.name(),
							supportedVideoMode.getResolutionY());
					logger.info("{} > {} Sensor > FPS > {}", deviceInfo.getUri(), sensorType.name(),
							supportedVideoMode.getFps());
					logger.info("{} > {} Sensor > Pixel format > {}", deviceInfo.getUri(), sensorType.name(),
							supportedVideoMode.getPixelFormat().name());

				}

			}

		}

	}

	public Cache<String, DepthSensor> getDevices() {
		return devices;
	}

	public void setDevices(Cache<String, DepthSensor> devices) {
		this.devices = devices;
	}

	public Cache<String, User> getUsers() {
		return users;
	}

	public void setUsers(Cache<String, User> users) {
		this.users = users;
	}

}