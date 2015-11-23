package net.fekepp.nirest.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import net.fekepp.nirest.vocab.NIREST;

@XmlRootElement
public class DepthSensor {

	private String id;

	private String name;
	private String vendor;
	private int usbProductID;
	private int usbVendorID;

	public Resource createURI(Model model) {
		return model.createResource(id);
	}

	public Resource createResource(Model model) {
		return createResource(model, null);
	}

	public Resource createResource(Model model, String uri) {
		Resource resource = null;
		if (uri != null) {
			resource = model.createResource(uri, NIREST.DepthSensor);
		} else {
			resource = model.createResource(NIREST.DepthSensor);
		}
		createProperties(model, resource);
		return resource;

	}

	public Resource createProperties(Model model, Resource resource) {
		resource.addProperty(NIREST.name, model.createLiteral(name))

				.addProperty(NIREST.vendor, model.createLiteral(vendor))

				.addProperty(NIREST.usbProductId, model.createTypedLiteral(usbProductID))

				.addProperty(NIREST.usbVendorId, model.createTypedLiteral(usbVendorID));
		return resource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public int getUsbProductID() {
		return usbProductID;
	}

	public void setUsbProductID(int usbProductID) {
		this.usbProductID = usbProductID;
	}

	public int getUsbVendorID() {
		return usbVendorID;
	}

	public void setUsbVendorID(int usbVendorID) {
		this.usbVendorID = usbVendorID;
	}

}