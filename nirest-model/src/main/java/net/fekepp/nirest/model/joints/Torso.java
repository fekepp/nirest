package net.fekepp.nirest.model.joints;

import javax.xml.bind.annotation.XmlRootElement;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class Torso extends Joint {

	public Torso() {
		jointResource = NIREST.TorsoJointPoint;
	}

}