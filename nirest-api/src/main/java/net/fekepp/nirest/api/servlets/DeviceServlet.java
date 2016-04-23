package net.fekepp.nirest.api.servlets;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;

import com.github.benmanes.caffeine.cache.Cache;

import net.fekepp.nirest.model.DepthSensor;

/**
 * @author "Felix Leif Keppmann"
 */
@Path("/device")
@Produces("text/turtle")
public class DeviceServlet {

	private static Cache<String, DepthSensor> devices;

	@Context
	private ServletContext servletContext;

	@Context
	UriInfo uriInfo;

	@GET
	@Path("{identifier}")
	public Response getRepresentation(@PathParam("identifier") String identifier) {

		// Representation to be returned
		Set<Node[]> representation = new HashSet<Node[]>();

		// URI of the requested resource
		Resource identifierResource = new Resource(uriInfo.getRequestUri().toString());

		// Get requested device from cache
		DepthSensor device = devices.getIfPresent(identifier);

		// Return HTTP 404 if device was not found
		if (device == null) {
			throw new NotFoundException("Device not found");
		}

		// Add the representation of the device to the response
		representation.addAll(device.getRepresentation(identifierResource));

		// Return representation
		return Response.ok(new GenericEntity<Iterable<Node[]>>(representation) {
		}).build();

	}

	public static Cache<String, DepthSensor> getDevices() {
		return devices;
	}

	public static void setDevices(Cache<String, DepthSensor> devices) {
		DeviceServlet.devices = devices;
	}

}