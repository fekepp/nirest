package net.fekepp.nirest.webapp;

import javax.ws.rs.core.MediaType;

/**
 * @author "Felix Leif Keppmann"
 */
public class MediaTypeLocal {
	public final static String APPLICATION_RDF_XML = "application/rdf+xml";
	public final static MediaType APPLICATION_RDF_XML_TYPE = new MediaType("application", "rdf+xml");
	public final static String TEXT_TURTLE = "text/turtle";
	public final static MediaType TEXT_TURTLE_TYPE = new MediaType("text", "turtle");
}