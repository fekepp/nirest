package net.fekepp.nirest.ldfu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Nodes;
import org.semanticweb.yars.nx.Resource;

import com.github.benmanes.caffeine.cache.Cache;

import edu.kit.aifb.datafu.web.api.EvaluationControllerDelegate;
import edu.kit.aifb.datafu.web.api.WebApiController;
import net.fekepp.nirest.device.controllers.DeviceController;
import net.fekepp.nirest.model.User;

public class LdfuStatisticsController extends WebApiController implements EvaluationControllerDelegate {

	private DeviceController controller;

	private Cache<String, User> users;

	private static int offset = 100;

	private static int size = 100;

	private int counter = 0;

	// private long[] startTimes = new long[size];
	// private long[] stopTimes = new long[size];
	private long[] runTimes = new long[size];

	private long startTime = 0L;

	private long stopTime = 0L;

	public LdfuStatisticsController() {
		setEvaluationControllerDelegate(this);

		controller = new DeviceController();
		users = controller.getUsers();

		controller.start();
	}

	@Override
	public Set<Nodes> getNodes() {

		// Measure end time
		stopTime = System.nanoTime();

		// Limit the measurement
		if (counter >= offset && counter < (offset + size)) {

			if (counter == offset) {
				System.out.println("START MEASUREING");
			}

			System.out.println("MEASURE > " + (counter - offset));

			// Remember times
			// startTimes[counter - offset] = startTime;
			// stopTimes[counter - offset] = stopTime;
			runTimes[counter - offset] = stopTime - startTime;

		}

		else if (counter == offset + size) {

			System.out.println("STOP MEASUREING");

			BufferedWriter bufferedWriter = null;

			try {

				bufferedWriter = new BufferedWriter(new FileWriter("runtimes.csv"));
				StringBuilder stringBuilder = new StringBuilder();
				for (int index = 0; index < size; index++) {
					// stringBuilder.append(startTimes[index]);
					// stringBuilder.append(",");
					// stringBuilder.append(stopTimes[index]);
					// stringBuilder.append(",");
					stringBuilder.append(runTimes[index]);
					stringBuilder.append(System.getProperty("line.separator"));
				}

				bufferedWriter.write(stringBuilder.toString());
				bufferedWriter.close();

			}

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Do stuff
		Set<Nodes> nodesSet = new HashSet<Nodes>();
		for (Entry<String, User> user : users.asMap().entrySet()) {
			for (Node[] node : user.getValue().getRepresentation(new Resource("nirest://user/" + user.getKey()))) {
				nodesSet.add(new Nodes(node));
			}
		}

		counter++;

		// Measure start time
		startTime = System.nanoTime();

		return nodesSet;

	}

}