package net.fekepp.nirest.ldfu;

/**
 * @author "Felix Leif Keppmann"
 */
public class Main {

	private static LdfuController ldfuController = new LdfuController();

	public static void main(String[] args) {
		ldfuController.setHost("127.0.0.1");
		ldfuController.setPort(8888);
		ldfuController.startBlocking();
	}

}