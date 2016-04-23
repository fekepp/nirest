package net.fekepp.nirest.model;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;
import org.semanticweb.yars.nx.namespace.XSD;

import net.fekepp.nirest.vocab.NIREST;

public class Coordinate3D {

	private float x;
	private float y;
	private float z;

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
	// Resource resource = model.createResource(NIREST.Coordinate)
	//
	// .addProperty(NIREST.x, model.createTypedLiteral(x))
	//
	// .addProperty(NIREST.y, model.createTypedLiteral(y))
	//
	// .addProperty(NIREST.z, model.createTypedLiteral(z));
	//
	// return resource;
	//
	// }

	public Set<Node[]> getRepresentation(Node subject) {

		if (subject == null) {
			subject = new Resource("");
		}

		Set<Node[]> representation = new HashSet<Node[]>();

		representation.add(new Node[] { subject, RDF.TYPE, NIREST.Coordinate });
		representation.add(new Node[] { subject, NIREST.x, new Literal(String.valueOf(x), XSD.FLOAT) });
		representation.add(new Node[] { subject, NIREST.y, new Literal(String.valueOf(y), XSD.FLOAT) });
		representation.add(new Node[] { subject, NIREST.z, new Literal(String.valueOf(z), XSD.FLOAT) });

		return representation;

	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

}