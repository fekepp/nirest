package net.fekepp.nirest.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import net.fekepp.nirest.model.joints.Head;
import net.fekepp.nirest.model.joints.Joint;
import net.fekepp.nirest.model.joints.LeftElbow;
import net.fekepp.nirest.model.joints.LeftFoot;
import net.fekepp.nirest.model.joints.LeftHand;
import net.fekepp.nirest.model.joints.LeftHip;
import net.fekepp.nirest.model.joints.LeftKnee;
import net.fekepp.nirest.model.joints.LeftShoulder;
import net.fekepp.nirest.model.joints.Neck;
import net.fekepp.nirest.model.joints.RightElbow;
import net.fekepp.nirest.model.joints.RightFoot;
import net.fekepp.nirest.model.joints.RightHand;
import net.fekepp.nirest.model.joints.RightHip;
import net.fekepp.nirest.model.joints.RightKnee;
import net.fekepp.nirest.model.joints.RightShoulder;
import net.fekepp.nirest.model.joints.Torso;
import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
@XmlSeeAlso({ Head.class, LeftElbow.class, LeftFoot.class, LeftHand.class, LeftHip.class, LeftKnee.class,
		LeftShoulder.class, Neck.class, RightElbow.class, RightFoot.class, RightHand.class, RightHip.class,
		RightKnee.class, RightShoulder.class, Torso.class })
public class Skeleton {

	private String state;
	private List<Joint> joints = new ArrayList<Joint>();

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

		Resource resource = model.createResource(NIREST.Skeleton)

				.addProperty(NIREST.trackingState, model.createLiteral(state));

		// for (Joint joint : joints.values()) {
		for (Joint joint : joints) {
			resource.addProperty(NIREST.jointPoint, joint.createResource(model));
		}

		return resource;

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