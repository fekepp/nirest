package net.fekepp.nirest.api.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.github.benmanes.caffeine.cache.Cache;

import net.fekepp.nirest.api.servlets.DeviceServlet;
import net.fekepp.nirest.api.servlets.UserServlet;
import net.fekepp.nirest.device.controllers.DeviceController;
import net.fekepp.nirest.model.DepthSensor;
import net.fekepp.nirest.model.User;

/**
 * @author "Felix Leif Keppmann"
 */
@WebListener
public class ApiContextListener implements ServletContextListener {

	private DeviceController controller;

	private Cache<String, DepthSensor> devices;

	private Cache<String, User> users;

	@Override
	public void contextInitialized(ServletContextEvent event) {

		controller = new DeviceController();
		devices = controller.getDevices();
		users = controller.getUsers();

		DeviceServlet.setDevices(devices);
		UserServlet.setUsers(users);

		controller.start();

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

}