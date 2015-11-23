package net.fekepp.nirest.webapp.listeners;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.fekepp.nirest.device.controllers.DeviceController;
import net.fekepp.nirest.model.DepthSensor;
import net.fekepp.nirest.model.User;

/**
 * @author "Felix Leif Keppmann"
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private DeviceController controller;

	private Map<String, DepthSensor> devices;

	private Map<String, User> users;

	@Override
	public void contextInitialized(ServletContextEvent event) {

		controller = new DeviceController();
		devices = controller.getDevices();
		users = controller.getUsers();

		ServletContext context = event.getServletContext();
		context.setAttribute("controller", controller);
		context.setAttribute("users", users);
		context.setAttribute("devices", devices);

		controller.start();

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

}