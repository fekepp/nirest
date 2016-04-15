package net.fekepp.controllers;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fekepp.nirest.controllers.AbstractController;

/**
 * @author "Felix Leif Keppmann"
 */
public class BaseJettyController extends AbstractController {

	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Jetty server
	 */
	private Server server = new Server();

	/**
	 * Server connector for Jetty server
	 * 
	 * Jetty 9: ServerConnector connector = new ServerConnector(server);
	 * 
	 * Jetty 8: SocketConnector connector = new SocketConnector();
	 */
	private ServerConnector serverConnector = new ServerConnector(server);

	/**
	 * Host for server connector
	 */
	private String host;

	/**
	 * Port for server connector
	 */
	private int port;

	/**
	 * Web app context for Jetty server
	 */
	private WebAppContext webAppContext = new WebAppContext();

	/**
	 * Context path for web app context
	 */
	private String contextPath = "/";

	/**
	 * Resource base for web app context
	 */
	private String resourceBase = "src/main/webapp";

	@Override
	protected void startup() {

		// Configure the connector
		serverConnector.setHost(host);
		serverConnector.setPort(port);

		// Add the connector to the server
		server.addConnector(serverConnector);

		// Configure the web app context handler
		webAppContext.setContextPath(contextPath);
		webAppContext.setResourceBase(resourceBase);

		// Ad the web app context handler to the server
		server.setHandler(webAppContext);

		// Start the Jetty server
		try {
			server.start();
		}

		// Notify the delegate in case of exceptions
		catch (Exception e) {
			logger.error("Jetty startup failed", e);
			if (delegate != null) {
				delegate.onControllerStartupException(e);
			}
		}

		// Get actual host and port
		host = serverConnector.getHost();
		port = serverConnector.getLocalPort();

	}

	@Override
	protected void shutdown() {

		// Shutdown the Jetty server
		try {
			server.stop();
		}

		// Notify the delegate in case of exceptions
		catch (Exception e) {
			logger.error("Jetty shutdown failed", e);
			if (delegate != null) {
				delegate.onControllerShutdownException(e);
			}
		}

	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public ServerConnector getServerConnector() {
		return serverConnector;
	}

	public void setServerConnector(ServerConnector serverConnector) {
		this.serverConnector = serverConnector;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public WebAppContext getWebAppContext() {
		return webAppContext;
	}

	public void setWebAppContext(WebAppContext webAppContext) {
		this.webAppContext = webAppContext;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getResourceBase() {
		return resourceBase;
	}

	public void setResourceBase(String resourceBase) {
		this.resourceBase = resourceBase;
	}

}