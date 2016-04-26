package net.fekepp.nirest.ldfu;

/**
 * @author "Felix Leif Keppmann"
 */
public class Main {

	private static LdfuController ldfuController = new LdfuController();

	public static void main(String[] args) {
		ldfuController.setPort(8888);
		ldfuController.startBlocking();
	}

}