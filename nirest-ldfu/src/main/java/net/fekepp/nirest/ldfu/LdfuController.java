package net.fekepp.nirest.ldfu;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Nodes;
import org.semanticweb.yars.nx.Resource;

import com.github.benmanes.caffeine.cache.Cache;

import edu.kit.aifb.datafu.web.api.EvaluationControllerDelegate;
import edu.kit.aifb.datafu.web.api.WebApiController;
import net.fekepp.nirest.device.controllers.DeviceController;
import net.fekepp.nirest.model.User;

public class LdfuController extends WebApiController implements EvaluationControllerDelegate {

	private DeviceController controller;

	private Cache<String, User> users;

	private int counter;

	public LdfuController() {
		setEvaluationControllerDelegate(this);

		controller = new DeviceController();
		users = controller.getUsers();

		controller.start();
	}

	@Override
	public Set<Nodes> getNodes() {

		Set<Nodes> nodesSet = new HashSet<Nodes>();

		// FIXME Temporary
		nodesSet.add(new Nodes(new Resource("http://ldfu#test"), new Resource("http://ldfu#counter"),
				new Literal(String.valueOf(counter++))));

		for (Entry<String, User> user : users.asMap().entrySet()) {
			for (Node[] node : user.getValue().getRepresentation(new Resource("nirest://user/" + user.getKey()))) {
				nodesSet.add(new Nodes(node));
			}
		}

		return nodesSet;

	}

}