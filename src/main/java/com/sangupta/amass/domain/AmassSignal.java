/**
 *
 * amass - web crawling made easy
 * Copyright (c) 2011-2013, Sandeep Gupta
 * 
 * http://www.sangupta/projects/amass
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.amass.domain;

import com.sangupta.amass.Amass;

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
	 * @return the current mode that this signal represents
	 * 
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
	 * Check if this signal represents an initializing mode.
	 * 
	 * @return <code>true</code> if this signal is set to
	 *         {@link SignalMode#Initializing}, <code>false</code> otherwise
	 */
	public boolean isInitializing() {
		return this.mode == SignalMode.Initializing;
	}
	
	/**
	 * Check if this signal represents a completely initialized state.
	 * 
	 * @return <code>true</code> if this signal has completed initialization,
	 *         <code>false</code> otherwise
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
