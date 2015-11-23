package net.fekepp.nirest.model;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlRootElement;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class User {

	private Coordinate3D centerOfMass;
	private Skeleton skeleton;

	public Resource createResource(Model model) {
		return createResource(model, null);
	}

	public Resource createResource(Model model, String uri) {
		Resource resource = null;
		if (uri != null) {
			resource = model.createResource(uri, NIREST.User);
		} else {
			resource = model.createResource(NIREST.User);
		}
		createProperties(model, resource);
		return resource;

	}

	public Resource createProperties(Model model, Resource resource) {
		resource

				.addProperty(NIREST.centerOfMass, centerOfMass.createResource(model))

				.addProperty(NIREST.skeleton, skeleton.createResource(model));

		return resource;

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