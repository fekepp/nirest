package net.fekepp.nirest.vocab;

import org.semanticweb.yars.nx.Resource;

/**
 * @author "Felix Leif Keppmann"
 */
public class NIREST {

	private static final String NS = "http://vocab.arvida.de/2014/02/nirest/vocab#";
	// private static final String nsXMLSchema =
	// "http://www.w3.org/2001/XMLSchema#";

	// public static String getURI() {
	// return NS;
	// }

	// public static Model createDefaultModel() {
	// Model model = ModelFactory.createDefaultModel();
	// model.setNsPrefix("nirest", NS);
	// model.setNsPrefix("xsd", nsXMLSchema);
	// return model;
	// }

	// private static Model m = createDefaultModel();

	// Coordinate
	public static final Resource Coordinate = new Resource(NS + "Coordinate");
	public static final Resource Coordinate2D = new Resource(NS + "Coordinate2D");
	public static final Resource Coordinate3D = new Resource(NS + "Coordinate3D");
	public static final Resource x = new Resource(NS + "x");
	public static final Resource y = new Resource(NS + "y");
	public static final Resource z = new Resource(NS + "z");

	// Device
	public static final Resource Device = new Resource(NS + "Device");
	public static final Resource DepthSensor = new Resource(NS + "DepthSensor");
	public static final Resource name = new Resource(NS + "name");
	public static final Resource vendor = new Resource(NS + "vendor");
	public static final Resource usbProductId = new Resource(NS + "usbProductId");
	public static final Resource usbVendorId = new Resource(NS + "usbVendorId");

	// User
	public static final Resource User = new Resource(NS + "User");
	public static final Resource centerOfMass = new Resource(NS + "centerOfMass");
	public static final Resource skeleton = new Resource(NS + "skeleton");

	// Skeleton
	public static final Resource Skeleton = new Resource(NS + "Skeleton");
	public static final Resource trackingState = new Resource(NS + "trackingState");
	public static final Resource jointPoint = new Resource(NS + "jointPoint");

	// Joint
	public static final Resource Joint = new Resource(NS + "Joint");
	public static final Resource orientationConfidence = new Resource(NS + "orientationConfidence");
	public static final Resource positionConfidence = new Resource(NS + "positionConfidence");
	public static final Resource coordinate = new Resource(NS + "coordinate");

	// Joint subclasses
	public static final Resource HeadJointPoint = new Resource(NS + "HeadJointPoint");
	public static final Resource NeckJointPoint = new Resource(NS + "NeckJointPoint");
	public static final Resource LeftShoulderJointPoint = new Resource(NS + "LeftShoulderJointPoint");
	public static final Resource RightShoulderJointPoint = new Resource(NS + "RightShoulderJointPoint");
	public static final Resource LeftElbowJointPoint = new Resource(NS + "LeftElbowJointPoint");
	public static final Resource RightElbowJointPoint = new Resource(NS + "RightElbowJointPoint");
	public static final Resource LeftHandJointPoint = new Resource(NS + "LeftHandJointPoint");
	public static final Resource RightHandJointPoint = new Resource(NS + "RightHandJointPoint");
	public static final Resource TorsoJointPoint = new Resource(NS + "TorsoJointPoint");
	public static final Resource LeftHipJointPoint = new Resource(NS + "LeftHipJointPoint");
	public static final Resource RightHipJointPoint = new Resource(NS + "RightHipJointPoint");
	public static final Resource LeftKneeJointPoint = new Resource(NS + "LeftKneeJointPoint");
	public static final Resource RightKneeJointPoint = new Resource(NS + "RightKneeJointPoint");
	public static final Resource LeftFootJointPoint = new Resource(NS + "LeftFootJointPoint");
	public static final Resource RightFootJointPoint = new Resource(NS + "RightFootJointPoint");

}