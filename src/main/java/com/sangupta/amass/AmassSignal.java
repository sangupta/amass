package com.sangupta.amass;

/**
 * Class that signifies the state in which {@link Amass} is.
 * 
 * @author sangupta
 *
 */
public class AmassSignal {
	
	/**
	 * The actual signal that we keep.
	 */
	private volatile SignalMode mode = SignalMode.UnInitialized;
	
	/**
	 * Create a new instance of this object. Default mode is set
	 * to {@link SignalMode#UnInitialized}.
	 */
	public AmassSignal() {
		
	}
	
	/**
	 * Return the current mode of this signal object.
	 * 
	 * @return
	 */
	public SignalMode getMode() {
		return this.mode;
	}
	
	public synchronized void setInitializing() {
		if(this.mode != SignalMode.UnInitialized) {
			throw new IllegalStateException("Amass is either initializing/initialized");
		}
		
		this.mode = SignalMode.Initializing;
	}
	
	public synchronized void setInitialized() {
		if(this.mode != SignalMode.Initializing) {
			throw new IllegalStateException("Amass is either uninitialzed/already initialized");
		}
		
		this.mode = SignalMode.Running;
	}
	
	/**
	 * Set the signalling state to STOP.
	 * 
	 */
	public synchronized void stop() {
		this.mode = SignalMode.Stopped;
	}
	
	/**
	 * Set the signalling state to PAUSE.
	 */
	public synchronized void pause() {
		switch(this.mode) {
			case UnInitialized:
			case Initializing:
			case Stopped:
				throw new IllegalStateException("Amass is either not initialized or is stopped");
				
			case Running:
				this.mode = SignalMode.Paused;
				return;
				
			case Paused:
				return;
		}
	}
	
	/**
	 * Set the signalling state to RESUME.
	 * 
	 */
	public synchronized void resume() {
		switch(this.mode) {
			case UnInitialized:
			case Initializing:
			case Stopped:
				throw new IllegalStateException("Amass is either not initialized or is stopped");
				
			case Running:
				return;
				
			case Paused:
				this.mode = SignalMode.Running;
				return;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isInitializing() {
		return this.mode == SignalMode.Initializing;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isInitialized() {
		switch(this.mode) {
			case UnInitialized:
			case Initializing:
				return false;
				
			default:
				return true;
		}
	}
	
	public boolean isRunning() {
		return this.mode == SignalMode.Running;
	}
	
	public boolean isPaused() {
		return this.mode == SignalMode.Paused;
	}
	
	public boolean isStopping() {
		return this.mode == SignalMode.Stopped;
	}

	// Enumerations around the signal
	
	/**
	 * 
	 * @author sangupta
	 *
	 */
	public static enum SignalMode {
		
		UnInitialized,
		
		Initializing,
		
		Running,
		
		Paused,
		
		Stopped;
	}
}
