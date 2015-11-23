package net.fekepp.nirest.model.joints;

import javax.xml.bind.annotation.XmlRootElement;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class LeftKnee extends Joint {

	public LeftKnee() {
		jointResource = NIREST.LeftKneeJointPoint;
	}

}