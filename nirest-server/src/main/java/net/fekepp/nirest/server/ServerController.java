package net.fekepp.nirest.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fekepp.controllers.BaseJettyJerseyController;
import net.fekepp.nirest.api.listeners.ApiContextListener;
import net.fekepp.nirest.api.servlets.DeviceServlet;
import net.fekepp.nirest.api.servlets.IndexServlet;
import net.fekepp.nirest.api.servlets.UserServlet;
import net.fekepp.nirest.controllers.ControllerDelegate;

/**
 * @author "Felix Leif Keppmann"
 */
public class ServerController extends BaseJettyJerseyController implements ControllerDelegate {

	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void startup() {

		logger.info("public void startup()");

		// Delegate on its own
		setDelegate(this);

		// Register listeners
		getWebAppContext().addEventListener(new ApiContextListener());

		// Register providers

		// Register servlets
		getResourceConfig().register(IndexServlet.class);
		getResourceConfig().register(DeviceServlet.class);
		getResourceConfig().register(UserServlet.class);

		// Continue startup
		super.startup();

	}

	@Override
	public void shutdown() {
		logger.info("public void shutdown()");
		super.shutdown();
	}

	@Override
	public void onControllerStarted() {
		logger.info("public void onControllerStarted()");
	}

	@Override
	public void onControllerStopped() {
		logger.info("public void onControllerStopped()");
	}

	@Override
	public void onControllerStartupException(Exception e) {
		logger.error("public void onControllerStartupException(Exception e)", e);
	}

	@Override
	public void onControllerRunException(Exception e) {
		logger.error("public void onControllerRunException(Exception e)", e);
	}

	@Override
	public void onControllerShutdownException(Exception e) {
		logger.error("public void onControllerShutdownException(Exception e)", e);
	}

}