package net.fekepp.nirest.ldfu;

/**
 * @author "Felix Leif Keppmann"
 */
public class StatisticsMain {

	private static LdfuStatisticsController ldfuController = new LdfuStatisticsController();

	public static void main(String[] args) {
		ldfuController.setPort(8888);
		ldfuController.startBlocking();
	}

}