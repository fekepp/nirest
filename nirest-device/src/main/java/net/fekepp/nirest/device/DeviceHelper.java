package net.fekepp.nirest.device;

import java.util.List;
import java.util.Map;

import org.openni.DeviceInfo;
import org.openni.OpenNI;

import net.fekepp.nirest.model.DepthSensor;

/**
 * @author "Felix Leif Keppmann"
 */
public class DeviceHelper {

	public static DepthSensor getDeviceById(Integer id) {
		List<DeviceInfo> deviceInfos = OpenNI.enumerateDevices();
		if (id >= deviceInfos.size()) {
			return null;
		}
		return createDevice(deviceInfos.get(id));
	}

	public static void addDevices(Map<String, DepthSensor> devices) {

		List<DeviceInfo> deviceInfos = OpenNI.enumerateDevices();
		for (int index = 0; index < deviceInfos.size(); index++) {
			DepthSensor device = createDevice(deviceInfos.get(index));
			device.setId(String.valueOf(index));
			devices.put(device.getId(), device);
		}

	}

	public static void addDevicesFake(Map<String, DepthSensor> devices) {

		DepthSensor device = new DepthSensor();
		device.setId("13371");
		device.setName("Fake Device #1");
		device.setVendor("Fake Vendor #1337");
		device.setUsbProductID(1337);
		device.setUsbVendorID(1337);
		devices.put(device.getId(), device);

		device = new DepthSensor();
		device.setId("13372");
		device.setName("Fake Device #2");
		device.setVendor("Fake Vendor #1337");
		device.setUsbProductID(1337);
		device.setUsbVendorID(1337);
		devices.put(device.getId(), device);

	}

	public static DepthSensor createDevice(DeviceInfo deviceInfo) {
		DepthSensor device = new DepthSensor();
		device.setName(deviceInfo.getName());
		device.setVendor(deviceInfo.getVendor());
		device.setUsbProductID(deviceInfo.getUsbProductId());
		device.setUsbVendorID(deviceInfo.getUsbVendorId());
		return device;
	}

}