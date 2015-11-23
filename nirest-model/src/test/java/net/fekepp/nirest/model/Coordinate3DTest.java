package net.fekepp.nirest.model;

import java.io.StringWriter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;

public class Coordinate3DTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void serializeTest() {

		Coordinate3D coordinate = new Coordinate3D();
		coordinate.setX(3.1f);
		coordinate.setY(3.2f);
		coordinate.setZ(3.3f);

		Model model = coordinate.createDefaultModel();

		StringWriter writer = new StringWriter();
		model.write(writer, "TURTLE");

		logger.debug("\n{}", writer.toString());

	}

}