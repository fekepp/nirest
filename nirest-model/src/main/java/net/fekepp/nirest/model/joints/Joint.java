package net.fekepp.nirest.model.joints;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.semanticweb.yars.nx.BNode;
import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;
import org.semanticweb.yars.nx.namespace.XSD;

import net.fekepp.nirest.model.Coordinate3D;
import net.fekepp.nirest.vocab.NIREST;

public class Joint {

	protected Resource jointResource = NIREST.Joint;
	private float orientationConfidence;
	private float positionConfidence;
	private Coordinate3D coordinate;

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
	// Resource resource = model.createResource(jointResource)
	//
	// .addProperty(NIREST.orientationConfidence,
	// model.createTypedLiteral(orientationConfidence))
	//
	// .addProperty(NIREST.positionConfidence,
	// model.createTypedLiteral(positionConfidence))
	//
	// .addProperty(NIREST.coordinate, coordinate.createResource(model));
	//
	// return resource;
	//
	// }

	public Set<Node[]> getRepresentation(Node subject) {

		if (subject == null) {
			subject = new Resource("");
		}

		BNode coordinateBlankNode = new BNode(UUID.randomUUID().toString());

		Set<Node[]> representation = new HashSet<Node[]>();

		representation.add(new Node[] { subject, RDF.TYPE, jointResource });
		representation.add(new Node[] { subject, NIREST.orientationConfidence,
				new Literal(String.valueOf(orientationConfidence), XSD.FLOAT) });
		representation.add(new Node[] { subject, NIREST.positionConfidence,
				new Literal(String.valueOf(positionConfidence), XSD.FLOAT) });
		representation.add(new Node[] { subject, NIREST.coordinate, coordinateBlankNode });

		representation.addAll(coordinate.getRepresentation(coordinateBlankNode));

		return representation;

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