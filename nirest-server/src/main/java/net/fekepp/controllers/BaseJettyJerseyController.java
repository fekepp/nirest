package net.fekepp.controllers;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.Configuration.ClassList;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * @author "Felix Leif Keppmann"
 */
public class BaseJettyJerseyController extends BaseJettyController {

	/**
	 * Jersey resource config
	 */
	private ResourceConfig resourceConfig = new ResourceConfig();

	/**
	 * Jersey servlet container
	 */
	private ServletContainer container = new ServletContainer(resourceConfig);

	/**
	 * Jetty servlet holder
	 */
	private ServletHolder servletHolder = new ServletHolder(container);

	/**
	 * Containter include JAR pattern
	 */
	private String containerIncludeJarPattern = classPathBaseAbsolute(getClass());

	/**
	 * Jetty servlet holder path spec
	 */
	private String pathSpec = "/*";

	@Override
	protected void startup() {

		// Add annotation configuration
		ClassList clist = ClassList.setServerDefault(getServer());
		clist.addBefore(JettyWebXmlConfiguration.class.getName(), AnnotationConfiguration.class.getName());

		// Configure web app context
		getWebAppContext().setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				containerIncludeJarPattern);
		getWebAppContext().addServlet(servletHolder, pathSpec);

		// Start the Jetty server
		super.startup();

	}

	public ResourceConfig getResourceConfig() {
		return resourceConfig;
	}

	public void setResourceConfig(ResourceConfig resourceConfig) {
		this.resourceConfig = resourceConfig;
	}

	public ServletContainer getContainer() {
		return container;
	}

	public void setContainer(ServletContainer container) {
		this.container = container;
	}

	public ServletHolder getServletHolder() {
		return servletHolder;
	}

	public void setServletHolder(ServletHolder servletHolder) {
		this.servletHolder = servletHolder;
	}

	public String getContainerIncludeJarPattern() {
		return containerIncludeJarPattern;
	}

	public void setContainerIncludeJarPattern(String containerIncludeJarPattern) {
		this.containerIncludeJarPattern = containerIncludeJarPattern;
	}

	public String getPathSpec() {
		return pathSpec;
	}

	public void setPathSpec(String pathSpec) {
		this.pathSpec = pathSpec;
	}

	/**
	 * Calculates the absolute class path base of a class, i.e., the absolute
	 * path of the directory containing the directory hierarchy with packages
	 * and classes.
	 * 
	 * @param clazz
	 *            The class used to calculate the required class path base
	 * @return The class path base
	 */
	private String classPathBaseAbsolute(Class<?> clazz) {
		String classFileName = clazz.getSimpleName() + ".class";
		String packageNameTest = clazz.getPackage().getName();
		String classPathAbsolute = clazz.getResource(classFileName).toString();
		String classPathRelative = packageNameTest.replace(".", "/") + "/" + classFileName;
		String classPathBaseAbsolute = classPathAbsolute.substring(0, classPathAbsolute.lastIndexOf(classPathRelative));
		return classPathBaseAbsolute;
	}

}