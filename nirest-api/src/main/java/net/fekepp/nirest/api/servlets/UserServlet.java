package net.fekepp.nirest.api.servlets;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.LDP;
import org.semanticweb.yars.nx.namespace.RDF;

import com.github.benmanes.caffeine.cache.Cache;

import net.fekepp.nirest.model.User;

/**
 * @author "Felix Leif Keppmann"
 */
@Path("/user/")
public class UserServlet {

	private static Cache<String, User> users;

	@Context
	private ServletContext servletContext;

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

		// Resource contains user sub-resources
		for (String user : users.asMap().keySet()) {
			representation.add(new Node[] { identifierResource, LDP.CONTAINS, new Resource(uri + user) });
		}

		// Return representation
		return Response.ok(new GenericEntity<Iterable<Node[]>>(representation) {
		}).build();

	}

	@GET
	@Path("{identifier}")
	public Response getRepresentation(@PathParam("identifier") String identifier) {

		// Representation to be returned
		Set<Node[]> representation = new HashSet<Node[]>();

		// URI of the requested resource
		Resource identifierResource = new Resource(uriInfo.getRequestUri().toString());

		// Get requested user from cache
		User user = users.getIfPresent(identifier);

		// Return HTTP 404 if user was not found
		if (user == null) {
			throw new NotFoundException("User not found");
		}

		// Add the representation of the user to the response
		representation.addAll(user.getRepresentation(identifierResource));

		// Return representation
		return Response.ok(new GenericEntity<Iterable<Node[]>>(representation) {
		}).build();

	}

	public static Cache<String, User> getUsers() {
		return users;
	}

	public static void setUsers(Cache<String, User> users) {
		UserServlet.users = users;
	}

}