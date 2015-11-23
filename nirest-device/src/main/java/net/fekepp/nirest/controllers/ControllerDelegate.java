package net.fekepp.nirest.controllers;

/**
 * @author "Felix Leif Keppmann"
 */
public interface ControllerDelegate {

	public void onControllerStarted();

	public void onControllerStopped();

	public void onControllerStartupException(Exception e);

	public void onControllerRunException(Exception e);

	public void onControllerShutdownException(Exception e);

}