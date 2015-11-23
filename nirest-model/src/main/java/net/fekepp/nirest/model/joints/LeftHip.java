package net.fekepp.nirest.model.joints;

import javax.xml.bind.annotation.XmlRootElement;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class LeftHip extends Joint {

	public LeftHip() {
		jointResource = NIREST.LeftHipJointPoint;
	}

}