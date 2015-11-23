package net.fekepp.nirest.model.joints;

import javax.xml.bind.annotation.XmlRootElement;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class RightKnee extends Joint {

	public RightKnee() {
		jointResource = NIREST.RightKneeJointPoint;
	}

}