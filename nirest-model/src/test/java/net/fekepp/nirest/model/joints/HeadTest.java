package net.fekepp.nirest.model.joints;

import java.io.StringWriter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;

import net.fekepp.nirest.model.Coordinate3D;

public class HeadTest extends Joint {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void serializeTest() {

		Coordinate3D coordinate = new Coordinate3D();
		coordinate.setX(3.1f);
		coordinate.setY(3.2f);
		coordinate.setZ(3.3f);

		Joint joint = new Head();
		joint.setOrientationConfidence(1.0f);
		joint.setPositionConfidence(1.0f);
		joint.setCoordinate(coordinate);

		Model model = joint.createDefaultModel();

		StringWriter writer = new StringWriter();
		model.write(writer, "TURTLE");

		logger.debug("\n{}", writer.toString());

	}

}