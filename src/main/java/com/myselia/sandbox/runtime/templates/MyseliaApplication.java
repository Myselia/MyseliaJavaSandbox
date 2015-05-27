package com.myselia.sandbox.runtime.templates;

import com.myselia.sandbox.constants.MyseliaModuleType;
import com.myselia.sandbox.constants.MyseliaRuntimeType;
import com.myselia.sandbox.runtime.ArgumentsInterpreter;
import com.myselia.sandbox.runtime.MyseliaApplicationSettings;
import com.myselia.sandbox.runtime.MyseliaRuntime;
import com.myselia.sandbox.runtime.MyseliaRuntimeFactory;
import com.myselia.sandbox.runtime.local.LocalRuntime;
import com.myselia.sandbox.runtime.network.NetworkRuntime;
import com.myselia.sandbox.runtime.templates.*;

/**
 * Mycelia Application
 * 
 * Provided a MyceliaMasterModule class and a MyceliaSlaveModule class,
 * this class will construct a Mycelia application that can be executed
 * by the Mycelia Runtime.
 * 
 * @param <M> Application's MyceliaMasterNode type.
 * @param <S> Application's MyceliaSlaveNode type.
 * 
 */

public class MyseliaApplication <M extends MyseliaMasterModule, S extends MyseliaSlaveModule>{
	private Class<M> masterModule;
	private Class<S> slaveModule;
	
	private MyseliaRuntimeType runtimeType;
	private MyseliaRuntime runtime;

	private MyseliaModuleType moduleType = null;
	private int slaveCount = 1;
	
	private MyseliaApplicationSettings settings;
	
	/**
	 * Creates a Mycelia application given a MyceliaMasterNode class and a MyceliaSlaveNode class.
	 * 
	 * This will create a Mycelia application with runtime type Local and
	 * load balancer strategy Round robin.
	 * 
	 * @param masterModule Application's MyceliaMasterModule class.
	 * @param slaveModule Application's MyceliaSlaveModule class.
	 * 
	 */
	public MyseliaApplication(Class<M> masterModule, Class<S> slaveModule, String[] args)
	{
		this.settings = ArgumentsInterpreter.interpret(args);
		this.masterModule = masterModule;
		this.slaveModule = slaveModule;
		
		init();
	}
	
	private void init(){
		setRuntimeType();
		setModuleType();
	}
	
	/**
	 * Sets the runtime type that must be used to run this application.
	 */
	public void setRuntimeType(){		
		System.out.println(this.settings.getRuntimeType());
		this.runtimeType = this.settings.getRuntimeType();
	}
	
	/**
	 * gets the current runtime
	 * @return runtime
	 */
	public MyseliaRuntime getRuntime(){
		return runtime;
	}
	
	/**
	 * sets the number of slaves the application requires
	 * it is only used if the application then starts in local mode
	 * @param slaveCount
	 */
	public void setSlaveCount(int slaveCount){
		this.slaveCount = slaveCount;
	}
	
	/**
	 * sets the module type which the application will run
	 * it is only used if the application then starts in network mode
	 */
	public void setModuleType(){
		System.out.println(this.settings.getModuleType());
		this.moduleType = this.settings.getModuleType();
	}
	
	
	/**
	 * Starts the Mycelia application.
	 * 
	 * This creates and starts the runtime environment
	 */
	public void start(){
		runtime = MyseliaRuntimeFactory.buildRuntime(masterModule, slaveModule, runtimeType);
		runtimeParameters(runtime);
		runtime.initialize();
		runtime.start();
	}
	
	
	public void runtimeParameters(MyseliaRuntime runtime){
		if(runtimeType == MyseliaRuntimeType.LOCAL){
			((LocalRuntime) runtime).setSlaveCount(slaveCount);
		} else if (runtimeType == MyseliaRuntimeType.NETWORK){
			((NetworkRuntime) runtime).setModuleType(settings.getModuleType());
		}
	}
	
	/**
	 * Stops the Mycelia application.
	 * 
	 * This politely ends all communication and releases all resources
	 * 
	 */
	public void stop(){
		runtime.end();
	}
	
}
