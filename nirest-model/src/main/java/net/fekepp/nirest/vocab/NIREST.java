package net.fekepp.nirest.vocab;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class NIREST {

	private static final String uri = "http://vocab.arvida.de/2014/02/nirest/vocab#";
	private static final String nsXMLSchema = "http://www.w3.org/2001/XMLSchema#";

	public static String getURI() {
		return uri;
	}

	public static Model createDefaultModel() {
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("nirest", uri);
		model.setNsPrefix("xsd", nsXMLSchema);
		return model;
	}

	private static Model m = createDefaultModel();

	// Coordinate
	public static final Resource Coordinate = m.createResource(uri + "Coordinate");
	public static final Resource Coordinate2D = m.createResource(uri + "Coordinate2D");
	public static final Resource Coordinate3D = m.createResource(uri + "Coordinate3D");
	public static final Property x = m.createProperty(uri, "x");
	public static final Property y = m.createProperty(uri, "y");
	public static final Property z = m.createProperty(uri, "z");

	// Device
	public static final Resource Device = m.createResource(uri + "Device");
	public static final Resource DepthSensor = m.createResource(uri + "DepthSensor");
	public static final Property name = m.createProperty(uri, "name");
	public static final Property vendor = m.createProperty(uri, "vendor");
	public static final Property usbProductId = m.createProperty(uri, "usbProductId");
	public static final Property usbVendorId = m.createProperty(uri, "usbVendorId");

	// User
	public static final Resource User = m.createResource(uri + "User");
	public static final Property centerOfMass = m.createProperty(uri, "centerOfMass");
	public static final Property skeleton = m.createProperty(uri, "skeleton");

	// Skeleton
	public static final Resource Skeleton = m.createResource(uri + "Skeleton");
	public static final Property trackingState = m.createProperty(uri, "trackingState");
	public static final Property jointPoint = m.createProperty(uri, "jointPoint");

	// Joint
	public static final Resource Joint = m.createResource(uri + "Joint");
	public static final Property orientationConfidence = m.createProperty(uri, "orientationConfidence");
	public static final Property positionConfidence = m.createProperty(uri, "positionConfidence");
	public static final Property coordinate = m.createProperty(uri, "coordinate");

	// Joint subclasses
	public static final Resource HeadJointPoint = m.createResource(uri + "HeadJointPoint");
	public static final Resource NeckJointPoint = m.createResource(uri + "NeckJointPoint");
	public static final Resource LeftShoulderJointPoint = m.createResource(uri + "LeftShoulderJointPoint");
	public static final Resource RightShoulderJointPoint = m.createResource(uri + "RightShoulderJointPoint");
	public static final Resource LeftElbowJointPoint = m.createResource(uri + "LeftElbowJointPoint");
	public static final Resource RightElbowJointPoint = m.createResource(uri + "RightElbowJointPoint");
	public static final Resource LeftHandJointPoint = m.createResource(uri + "LeftHandJointPoint");
	public static final Resource RightHandJointPoint = m.createResource(uri + "RightHandJointPoint");
	public static final Resource TorsoJointPoint = m.createResource(uri + "TorsoJointPoint");
	public static final Resource LeftHipJointPoint = m.createResource(uri + "LeftHipJointPoint");
	public static final Resource RightHipJointPoint = m.createResource(uri + "RightHipJointPoint");
	public static final Resource LeftKneeJointPoint = m.createResource(uri + "LeftKneeJointPoint");
	public static final Resource RightKneeJointPoint = m.createResource(uri + "RightKneeJointPoint");
	public static final Resource LeftFootJointPoint = m.createResource(uri + "LeftFootJointPoint");
	public static final Resource RightFootJointPoint = m.createResource(uri + "RightFootJointPoint");

}