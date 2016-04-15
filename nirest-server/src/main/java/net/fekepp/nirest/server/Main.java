package net.fekepp.nirest.server;

/**
 * @author "Felix Leif Keppmann"
 */
public class Main {

	private static ServerController serverController;

	public static void main(String[] args) {
		serverController = new ServerController();
		serverController.setPort(8888);
		serverController.startBlocking();
	}

}