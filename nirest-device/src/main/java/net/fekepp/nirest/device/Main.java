package net.fekepp.nirest.device;

import net.fekepp.nirest.device.controllers.DeviceController;

/**
 * @author "Felix Leif Keppmann"
 */
public class Main {

	public static void main(String[] args) {
		DeviceController deviceController = new DeviceController();
		deviceController.start();
	}

}