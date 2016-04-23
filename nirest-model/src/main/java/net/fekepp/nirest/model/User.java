package net.fekepp.nirest.model;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.semanticweb.yars.nx.BNode;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;

import net.fekepp.nirest.vocab.NIREST;

public class User {

	private Coordinate3D centerOfMass;
	private Skeleton skeleton;

	// public Resource createResource(Model model) {
	// return createResource(model, null);
	// }

	// public Resource createResource(Model model, String uri) {
	// Resource resource = null;
	// if (uri != null) {
	// resource = model.createResource(uri, NIREST.User);
	// } else {
	// resource = model.createResource(NIREST.User);
	// }
	// createProperties(model, resource);
	// return resource;
	//
	// }

	// public Resource createProperties(Model model, Resource resource) {
	// resource
	//
	// .addProperty(NIREST.centerOfMass, centerOfMass.createResource(model))
	//
	// .addProperty(NIREST.skeleton, skeleton.createResource(model));
	//
	// return resource;
	//
	// }

	public Set<Node[]> getRepresentation(Node subject) {

		if (subject == null) {
			subject = new Resource("");
		}

		Set<Node[]> representation = new HashSet<Node[]>();

		representation.add(new Node[] { subject, RDF.TYPE, NIREST.User });

		BNode centerOfMassBlankNode = new BNode(UUID.randomUUID().toString());
		representation.add(new Node[] { subject, NIREST.centerOfMass, centerOfMassBlankNode });
		representation.addAll(centerOfMass.getRepresentation(centerOfMassBlankNode));

		BNode skeletonBlankNode = new BNode(UUID.randomUUID().toString());
		representation.add(new Node[] { subject, NIREST.skeleton, skeletonBlankNode });
		representation.addAll(skeleton.getRepresentation(skeletonBlankNode));

		return representation;

	}

	public Coordinate3D getCenterOfMass() {
		return centerOfMass;
	}

	public void setCenterOfMass(Coordinate3D centerOfMass) {
		this.centerOfMass = centerOfMass;
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}

	public void setSkeleton(Skeleton skeleton) {
		this.skeleton = skeleton;
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