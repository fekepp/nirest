package net.fekepp.nirest.api.servlets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author "Felix Leif Keppmann"
 */
@Path("/")
public class IndexServlet {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response html() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(""

				+ "<html>"

				+ "\n\t<head>\n\t\t<title>Natural Interaction via REST (NIREST)</title>\n\t</head>"

				+ "\n\t<body>"

				+ "\n\t\t<h1>Natural Interaction via REST (NIREST)</h1>"

				+ "\n\t\t<h2>Resources:</h2>"
				
				+ "\n\t\t<a href=\"device\">device</a>"

				+ "\n\t\t<br/>"

				+ "\n\t\t<a href=\"user\">user</a>"

				+ "\n\t</body>"

				+ "\n</html>");

		return Response.status(200).entity(buffer.toString()).build();
	}

}