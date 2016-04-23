package net.fekepp.nirest.api.servlets;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.LDP;
import org.semanticweb.yars.nx.namespace.RDF;

/**
 * @author "Felix Leif Keppmann"
 */
@Path("/")
@Produces("text/turtle")
public class IndexServlet {

	@Context
	UriInfo uriInfo;

	@GET
	public Response getRepresentation() {

		// Get request URI which always has a trailing slash
		String uri = uriInfo.getRequestUri().toString();
		if (!uri.endsWith("/")) {
			uri += "/";
		}

		// Representation to be returned
		Set<Node[]> representation = new HashSet<Node[]>();

		// URI of the requested resource
		Resource identifierResource = new Resource(uri);

		// Resource is a LDP Container
		representation.add(new Node[] { identifierResource, RDF.TYPE, LDP.CONTAINER });

		// Resource is a LDP Basic Container
		representation.add(new Node[] { identifierResource, RDF.TYPE, LDP.BASIC_CONTAINER });

		// Resource contains a device container resource
		representation.add(new Node[] { identifierResource, LDP.CONTAINS, new Resource(uri + "device") });

		// Resource contains a user container resource
		representation.add(new Node[] { identifierResource, LDP.CONTAINS, new Resource(uri + "user") });

		// Return representation
		return Response.ok(new GenericEntity<Iterable<Node[]>>(representation) {
		}).build();

	}

}