package net.fekepp.nirest.model.joints;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fekepp.nirest.model.Coordinate3D;

public class RightFootTest extends Joint {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void serializeTest() {

		Coordinate3D coordinate = new Coordinate3D();
		coordinate.setX(3.1f);
		coordinate.setY(3.2f);
		coordinate.setZ(3.3f);

		Joint joint = new RightFoot();
		joint.setOrientationConfidence(1.0f);
		joint.setPositionConfidence(1.0f);
		joint.setCoordinate(coordinate);

		logger.debug("\n{}", Arrays.deepToString(joint.getRepresentation(null).toArray()));

	}

}