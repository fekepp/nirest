package net.fekepp.nirest.api.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.hp.hpl.jena.rdf.model.Model;

import net.fekepp.nirest.api.MediaTypeLocal;
import net.fekepp.nirest.model.DepthSensor;
import net.fekepp.nirest.vocab.NIREST;

/**
 * @author "Felix Leif Keppmann"
 */
@Path("/device")
public class DeviceServlet {

	@Context
	private ServletContext servletContext;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response html() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body><form action=\"\"><textarea cols=\"200\" rows=\"40\">");
		Model model = generateDevicesModel();
		StringWriter writer = new StringWriter();
		model.write(writer, "TURTLE");
		buffer.append(writer.toString());
		buffer.append("</textarea></form></body></html>");
		return Response.status(200).entity(buffer.toString()).build();
	}

	@GET
	@Produces(MediaTypeLocal.APPLICATION_RDF_XML)
	public Response rdfxml() {
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				generateDevicesModel().write(os, "RDF/XML-ABBREV");
			}
		};
		return Response.status(200).entity(stream).build();
	}

	@GET
	@Produces(MediaTypeLocal.TEXT_TURTLE)
	public Response turtle() {
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				generateDevicesModel().write(os, "TURTLE");
			}
		};
		return Response.status(200).entity(stream).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_HTML)
	public Response getDeviceById(@PathParam("id") String id) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body><form action=\"\"><textarea cols=\"200\" rows=\"40\">");
		Model model = generateDeepDeviceModel(id);

		if (model != null) {
			StringWriter writer = new StringWriter();
			model.write(writer, "TURTLE");
			buffer.append(writer.toString());
			buffer.append("</textarea></form></body></html>");
			return Response.status(200).entity(buffer.toString()).build();
		}

		return Response.status(404).entity("Device not found").build();

	}

	@GET
	@Path("{id}")
	@Produces(MediaTypeLocal.APPLICATION_RDF_XML)
	public Response rdfxmlByID(@PathParam("id") String id) {

		final Model model = generateDeepDeviceModel(id);

		if (model != null) {
			StreamingOutput stream = new StreamingOutput() {
				@Override
				public void write(OutputStream os) throws IOException, WebApplicationException {
					model.write(os, "RDF/XML-ABBREV");
				}
			};
			return Response.status(200).entity(stream).build();
		}

		return Response.status(404).entity("Device not found").build();

	}

	@GET
	@Path("{id}")
	@Produces(MediaTypeLocal.TEXT_TURTLE)
	public Response turtleByID(@PathParam("id") String id) {

		final Model model = generateDeepDeviceModel(id);

		if (model != null) {
			StreamingOutput stream = new StreamingOutput() {
				@Override
				public void write(OutputStream os) throws IOException, WebApplicationException {
					model.write(os, "TURTLE");
				}
			};
			return Response.status(200).entity(stream).build();
		}

		return Response.status(404).entity("Device not found").build();

	}

	private Model generateDevicesModel() {

		@SuppressWarnings("unchecked")
		Map<String, DepthSensor> devicesMap = (Map<String, DepthSensor>) servletContext.getAttribute("devices");

		Model model = NIREST.createDefaultModel();

		for (DepthSensor device : devicesMap.values()) {
			model.createResource("http://localhost:8888/device/" + device.getId(), NIREST.DepthSensor);
		}

		return model;
	}

	private Model generateDeepDeviceModel(String id) {

		@SuppressWarnings("unchecked")
		Map<String, DepthSensor> devicesMap = (Map<String, DepthSensor>) servletContext.getAttribute("devices");

		Model model = null;

		if (devicesMap.containsKey(id)) {
			model = NIREST.createDefaultModel();

			devicesMap.get(id).createResource(model, "http://localhost:8888/device/" + id);
		}

		return model;
	}

}