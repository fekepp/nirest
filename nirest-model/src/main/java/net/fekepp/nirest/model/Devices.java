package net.fekepp.nirest.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Devices {

	private Map<String, DepthSensor> devices = new HashMap<String, DepthSensor>();

	public void addDevice(DepthSensor device) {
		devices.put(device.getId(), device);
	}

	public Map<String, DepthSensor> getDevices() {
		return devices;
	}

	public void setDevices(Map<String, DepthSensor> devices) {
		this.devices = devices;
	}

	public String toString() {

		try {

			Class<?> clazz = this.getClass();
			StringBuffer buffer = new StringBuffer();

			buffer.append(clazz.getSimpleName());
			buffer.append("[");

			Field[] fields = clazz.getDeclaredFields();

			for (int index = 0; index < fields.length; index++) {
				buffer.append(fields[index].getName());
				buffer.append("=");

				buffer.append(fields[index].get(this));

				if (index < fields.length - 1) {
					buffer.append("|");
				}
			}

			buffer.append("]");

			return buffer.toString();

		}

		// Catch any exception
		catch (Exception e) {
			return "FAILED";
		}

	}

}