package net.fekepp.nirest.model.joints;

import javax.xml.bind.annotation.XmlRootElement;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class RightHip extends Joint {

	public RightHip() {
		jointResource = NIREST.RightHipJointPoint;
	}

}