package net.fekepp.nirest.webapp.controllers;

/**
 * @author "Felix Leif Keppmann"
 */
public class DefaultJettyJerseyController extends BaseJettyJerseyController {

	@Override
	public void startup() {
		String packageName = getClass().getPackage().getName();
		String parentPackageName = packageName.substring(0, packageName.lastIndexOf("."));
		getResourceConfig().packages(parentPackageName + ".providers");
		getResourceConfig().packages(parentPackageName + ".servlets");
		super.startup();
	}

}