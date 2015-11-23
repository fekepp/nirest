package net.fekepp.nirest.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import net.fekepp.nirest.vocab.NIREST;

public class Coordinate3D {

	private float x;
	private float y;
	private float z;

	public Model createDefaultModel() {

		final String nsXMLSchema = "http://www.w3.org/2001/XMLSchema#";
		final String nsNIREST = NIREST.getURI();

		Model model = ModelFactory.createDefaultModel();

		model.setNsPrefix("nirest", nsNIREST);
		model.setNsPrefix("xsd", nsXMLSchema);

		createResource(model);

		return model;

	}

	public Resource createResource(Model model) {

		Resource resource = model.createResource(NIREST.Coordinate)

				.addProperty(NIREST.x, model.createTypedLiteral(x))

				.addProperty(NIREST.y, model.createTypedLiteral(y))

				.addProperty(NIREST.z, model.createTypedLiteral(z));

		return resource;

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