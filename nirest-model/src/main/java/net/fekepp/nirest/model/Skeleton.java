package net.fekepp.nirest.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.semanticweb.yars.nx.BNode;
import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;

import net.fekepp.nirest.model.joints.Joint;
import net.fekepp.nirest.vocab.NIREST;

public class Skeleton {

	private String state;
	private List<Joint> joints = new ArrayList<Joint>();

	// public Model createDefaultModel() {
	//
	// final String nsXMLSchema = "http://www.w3.org/2001/XMLSchema#";
	// final String nsNIREST = NIREST.getURI();
	//
	// Model model = ModelFactory.createDefaultModel();
	//
	// model.setNsPrefix("nirest", nsNIREST);
	// model.setNsPrefix("xsd", nsXMLSchema);
	//
	// createResource(model);
	//
	// return model;
	//
	// }

	// public Resource createResource(Model model) {
	//
	// Resource resource = model.createResource(NIREST.Skeleton)
	//
	// .addProperty(NIREST.trackingState, model.createLiteral(state));
	//
	// // for (Joint joint : joints.values()) {
	// for (Joint joint : joints) {
	// resource.addProperty(NIREST.jointPoint, joint.createResource(model));
	// }
	//
	// return resource;
	//
	// }

	public Set<Node[]> getRepresentation(Node subject) {

		if (subject == null) {
			subject = new Resource("");
		}

		Set<Node[]> representation = new HashSet<Node[]>();

		representation.add(new Node[] { subject, RDF.TYPE, NIREST.Skeleton });
		representation.add(new Node[] { subject, NIREST.trackingState, new Literal(state) });

		for (Joint joint : joints) {
			BNode jointPointBlankNode = new BNode(UUID.randomUUID().toString());
			representation.add(new Node[] { subject, NIREST.jointPoint, jointPointBlankNode });
			representation.addAll(joint.getRepresentation(jointPointBlankNode));
		}

		return representation;

	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// public Map<String, Joint> getJoints() {
	// return joints;
	// }
	//
	// public void setJoints(Map<String, Joint> joints) {
	// this.joints = joints;
	// }

	public List<Joint> getJoints() {
		return joints;
	}

	public void setJoints(List<Joint> joints) {
		this.joints = joints;
	}

	public String toString() {

		try {

			Class<?> clazz = this.getClass();
			StringBuffer buffer = new StringBuffer();

			buffer.append(clazz.getSimpleName());
			buffer.append("[");

			Field[] fields = clazz.getDeclaredFields();

			for (int index = 0; index < fields.length; index++) {
				buffer.append(fields[index].getName());
				buffer.append("=");

				buffer.append(fields[index].get(this));

				if (index < fields.length - 1) {
					buffer.append("|");
				}
			}

			buffer.append("]");

			return buffer.toString();

		}

		// Catch any exception
		catch (Exception e) {
			return "FAILED";
		}

	}

}