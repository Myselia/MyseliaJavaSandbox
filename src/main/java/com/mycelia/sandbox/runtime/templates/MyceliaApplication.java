package com.mycelia.sandbox.runtime.templates;

import com.mycelia.sandbox.constants.MyceliaModuleType;
import com.mycelia.sandbox.constants.MyceliaRuntimeType;
import com.mycelia.sandbox.runtime.ArgumentsInterpreter;
import com.mycelia.sandbox.runtime.MyceliaApplicationSettings;
import com.mycelia.sandbox.runtime.MyceliaRuntime;
import com.mycelia.sandbox.runtime.MyceliaRuntimeFactory;
import com.mycelia.sandbox.runtime.local.LocalRuntime;
import com.mycelia.sandbox.runtime.network.NetworkRuntime;
import com.mycelia.sandbox.runtime.templates.*;

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

public class MyceliaApplication <M extends MyceliaMasterModule, S extends MyceliaSlaveModule>{
	private Class<M> masterModule;
	private Class<S> slaveModule;
	
	private MyceliaRuntimeType runtimeType;
	private MyceliaRuntime runtime;

	private MyceliaModuleType moduleType = null;
	private int slaveCount = 4;
	
	private MyceliaApplicationSettings settings;
	
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
	public MyceliaApplication(Class<M> masterModule, Class<S> slaveModule, String[] args)
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
	public MyceliaRuntime getRuntime(){
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
		runtime = MyceliaRuntimeFactory.buildRuntime(masterModule, slaveModule, runtimeType);
		runtimeParameters(runtime);
		runtime.initialize();
		runtime.start();
	}
	
	
	public void runtimeParameters(MyceliaRuntime runtime){
		if(runtimeType == MyceliaRuntimeType.LOCAL){
			((LocalRuntime) runtime).setSlaveCount(slaveCount);
		} else if (runtimeType == MyceliaRuntimeType.NETWORK){
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
