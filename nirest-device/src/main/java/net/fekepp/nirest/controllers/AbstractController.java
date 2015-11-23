package net.fekepp.nirest.controllers;

/**
 * @author "Felix Leif Keppmann"
 */
public abstract class AbstractController implements Controller {

	private final Object sync = new Object();

	private boolean startup = false;
	private boolean running = false;
	private boolean shutdown = false;

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

	protected abstract void shutdown() throws Exception;

}
