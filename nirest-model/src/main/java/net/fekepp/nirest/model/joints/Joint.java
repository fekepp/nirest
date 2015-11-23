package net.fekepp.nirest.model.joints;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlRootElement;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import net.fekepp.nirest.model.Coordinate3D;
import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class Joint {

	protected Resource jointResource = NIREST.Joint;
	private float orientationConfidence;
	private float positionConfidence;
	private Coordinate3D coordinate;

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

		Resource resource = model.createResource(jointResource)

				.addProperty(NIREST.orientationConfidence, model.createTypedLiteral(orientationConfidence))

				.addProperty(NIREST.positionConfidence, model.createTypedLiteral(positionConfidence))

				.addProperty(NIREST.coordinate, coordinate.createResource(model));

		return resource;

	}

	public float getOrientationConfidence() {
		return orientationConfidence;
	}

	public void setOrientationConfidence(float orientationConfidence) {
		this.orientationConfidence = orientationConfidence;
	}

	public float getPositionConfidence() {
		return positionConfidence;
	}

	public void setPositionConfidence(float positionConfidence) {
		this.positionConfidence = positionConfidence;
	}

	public Coordinate3D getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate3D coordinate) {
		this.coordinate = coordinate;
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

	public static Joint createJointFromName(String name) {

		Joint joint = null;

		switch (name) {

		case "HEAD":
			joint = new Head();
			break;

		case "NECK":
			joint = new Neck();
			break;

		case "LEFT_SHOULDER":
			joint = new LeftShoulder();
			break;

		case "RIGHT_SHOULDER":
			joint = new RightShoulder();
			break;

		case "LEFT_ELBOW":
			joint = new LeftElbow();
			break;

		case "RIGHT_ELBOW":
			joint = new RightElbow();
			break;

		case "LEFT_HAND":
			joint = new LeftHand();
			break;

		case "RIGHT_HAND":
			joint = new RightHand();
			break;

		case "TORSO":
			joint = new Torso();
			break;

		case "LEFT_HIP":
			joint = new LeftHip();
			break;

		case "RIGHT_HIP":
			joint = new RightHip();
			break;

		case "LEFT_KNEE":
			joint = new LeftKnee();
			break;

		case "RIGHT_KNEE":
			joint = new RightKnee();
			break;

		case "LEFT_FOOT":
			joint = new LeftFoot();
			break;

		case "RIGHT_FOOT":
			joint = new RightFoot();
			break;

		}

		return joint;

	}

}