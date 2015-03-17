package com.mycelia.sandbox.runtime;

import com.mycelia.sandbox.runtime.templates.MyceliaMasterModule;
import com.mycelia.sandbox.runtime.templates.MyceliaSlaveModule;


/**
 * MyceliaRuntime
 * 
 * formerly MyceliaApplicationRuntime
 * 
 * responsible for setting up and running a Mycelia Application
 * there are multiple concrete types of MyceliaRuntime, enumerated in MyceliaRuntimeType
 *
 */
public abstract class MyceliaRuntime {

	protected Class<? extends MyceliaMasterModule> masterModuleClass;
	protected Class<? extends MyceliaSlaveModule> slaveModuleClass;

	public <M extends MyceliaMasterModule, S extends MyceliaSlaveModule> MyceliaRuntime (Class<M> masterModuleClass, Class<S> slaveModuleClass) {
		this.masterModuleClass = masterModuleClass;
		this.slaveModuleClass = slaveModuleClass;
	}
	
	/**
	 * initializes the runtime
	 * 
	 * this performs some setup operations which are needed once prior to running the application
	 * instantiates the appropriate classes for the runtime
	 */
	public abstract void initialize();
	
	/**
	 * begins execution of the thread(s) for the specific runtime
	 */
	public abstract void start();
	
	/**
	 * end finished all communication and releases resources at the end of runtime
	 */
	public abstract void end();

}
