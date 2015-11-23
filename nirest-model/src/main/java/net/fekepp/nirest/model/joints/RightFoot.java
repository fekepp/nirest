package net.fekepp.nirest.model.joints;

import javax.xml.bind.annotation.XmlRootElement;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class RightFoot extends Joint {

	public RightFoot() {
		jointResource = NIREST.RightFootJointPoint;
	}

}