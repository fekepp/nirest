package net.fekepp.nirest.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class ApiResourceConfig extends ResourceConfig {

	public ApiResourceConfig() {
		packages(getClass().getPackage().getName() + ".servlets");
	}

}