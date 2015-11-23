package net.fekepp.nirest.webapp;

import net.fekepp.nirest.webapp.controllers.DefaultJettyJerseyController;

/**
 * @author "Felix Leif Keppmann"
 */
public class Main {

	private static DefaultJettyJerseyController defaultJettyJerseyController;

	public static void main(String[] args) {
		defaultJettyJerseyController = new DefaultJettyJerseyController();
		defaultJettyJerseyController.setPort(8888);
		defaultJettyJerseyController.startBlocking();
	}

}