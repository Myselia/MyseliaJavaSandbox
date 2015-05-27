package com.myselia.sandbox.runtime;

import com.myselia.sandbox.runtime.templates.MyseliaMasterModule;
import com.myselia.sandbox.runtime.templates.MyseliaSlaveModule;


/**
 * MyceliaRuntime
 * 
 * formerly MyceliaApplicationRuntime
 * 
 * responsible for setting up and running a Mycelia Application
 * there are multiple concrete types of MyceliaRuntime, enumerated in MyceliaRuntimeType
 *
 */
public abstract class MyseliaRuntime {

	protected Class<? extends MyseliaMasterModule> masterModuleClass;
	protected Class<? extends MyseliaSlaveModule> slaveModuleClass;

	public <M extends MyseliaMasterModule, S extends MyseliaSlaveModule> MyseliaRuntime (Class<M> masterModuleClass, Class<S> slaveModuleClass) {
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
