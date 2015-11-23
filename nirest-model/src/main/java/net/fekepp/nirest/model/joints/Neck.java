package net.fekepp.nirest.model.joints;

import javax.xml.bind.annotation.XmlRootElement;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class Neck extends Joint {

	public Neck() {
		jointResource = NIREST.NeckJointPoint;
	}

}