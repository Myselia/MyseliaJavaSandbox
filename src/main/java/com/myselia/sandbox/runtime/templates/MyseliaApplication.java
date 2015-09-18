package com.myselia.sandbox.runtime.templates;

import com.myselia.sandbox.runtime.ArgumentsInterpreter;
import com.myselia.sandbox.runtime.MyseliaApplicationSettings;
import com.myselia.sandbox.runtime.MyseliaRuntime;
import com.myselia.sandbox.runtime.MyseliaRuntimeFactory;

/**
 * Myselia Application
 * 
 * Provided a MyseliaMasterModule class and a MyseliaSlaveModule class,
 * this class will construct an application that can be executed
 * by the MyseliaRuntime.
 * 
 * @param <M> Application's MyseliaMasterModule.
 * @param <S> Application's MyseliaSlaveModule.
 * 
 */

public class MyseliaApplication <M extends MyseliaMasterModule, S extends MyseliaSlaveModule>{
	private Class<M> masterModule;
	private Class<S> slaveModule;
	
	private MyseliaApplicationSettings settings;
	private MyseliaRuntime runtime;
	
	/**
	 * Creates an application given a MyseliaMasterModule class and a MyseliaSlaveModule class.
	 * 
	 * @param masterModule Application's MyseliaMasterModule class.
	 * @param slaveModule Application's MyseliaSlaveModule class.
	 * 
	 */
	public MyseliaApplication(Class<M> masterModule, Class<S> slaveModule, String[] args)
	{
		this.settings = ArgumentsInterpreter.interpret(args);
		this.masterModule = masterModule;
		this.slaveModule = slaveModule;
	}

	
	/**
	 * Starts the application.
	 * 
	 * This creates and starts the runtime environment
	 */
	public void start(){
		runtime = MyseliaRuntimeFactory.buildRuntime(masterModule, slaveModule, settings);
		runtime.initialize();
		runtime.start();
	}
	
	/**
	 * Stops the application.
	 * 
	 * This politely ends all communication and releases all resources
	 * 
	 */
	public void stop(){
		runtime.end();
	}
	
}
