package net.fekepp.nirest.controllers;

/**
 * @author "Felix Leif Keppmann"
 */
public interface Controller extends Runnable {

	public void start();

	public void startBlocking();

	public void stop();

	public void stopBlocking();

	public boolean isRunning();

	public abstract ControllerDelegate getDelegate();

	public abstract void setDelegate(ControllerDelegate delegate);

}