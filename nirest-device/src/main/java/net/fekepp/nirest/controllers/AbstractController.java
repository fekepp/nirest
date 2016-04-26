package net.fekepp.nirest.controllers;

/**
 * @author "Felix Leif Keppmann"
 */
public abstract class AbstractController implements Controller {

	protected final Object sync = new Object();

	protected boolean startup = false;
	protected boolean running = false;
	protected boolean shutdown = false;

	protected ControllerDelegate delegate;

	@Override
	public synchronized void start() {

		if (startup || running) {
			return;
		}

		startup = true;

		Thread thread = new Thread(this);
		thread.setName(getClass().getSimpleName());
		thread.start();

	}

	@Override
	public synchronized void startBlocking() {

		start();

		try {
			synchronized (sync) {
				while (startup) {
					sync.wait();
				}
			}
		}

		catch (InterruptedException e) {
			if (delegate != null) {
				delegate.onControllerStartupException(e);
			}
		}

	}

	@Override
	public synchronized void stop() {

		if (shutdown || !running) {
			return;
		}

		shutdown = true;

		synchronized (sync) {
			sync.notify();
		}

	}

	@Override
	public synchronized void stopBlocking() {

		stop();

		try {
			synchronized (sync) {
				while (shutdown) {
					sync.wait();
				}
			}
		}

		catch (InterruptedException e) {
			if (delegate != null) {
				delegate.onControllerShutdownException(e);
			}
		}

	}

	@Override
	public void run() {

		try {
			startup();
		} catch (Exception e) {
			if (delegate != null) {
				delegate.onControllerStartupException(e);
			}
		}

		startup = false;
		running = true;

		synchronized (sync) {
			sync.notify();
		}

		if (delegate != null) {
			delegate.onControllerStarted();
		}

		execute();

		try {
			shutdown();
		} catch (Exception e) {
			if (delegate != null) {
				delegate.onControllerShutdownException(e);
			}
		}

		shutdown = false;
		running = false;

		synchronized (sync) {
			sync.notify();
		}

		if (delegate != null) {
			delegate.onControllerStopped();
		}

	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public ControllerDelegate getDelegate() {
		return delegate;
	}

	@Override
	public void setDelegate(ControllerDelegate delegate) {
		this.delegate = delegate;
	}

	protected abstract void startup() throws Exception;

	protected void execute() {

		try {
			synchronized (sync) {
				while (running && !shutdown) {
					sync.wait();
				}
			}
		}

		catch (InterruptedException e) {
			if (delegate != null) {
				delegate.onControllerRunException(e);
			}
		}

	}

	protected abstract void shutdown() throws Exception;

}
