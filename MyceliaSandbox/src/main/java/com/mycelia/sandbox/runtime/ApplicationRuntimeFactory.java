package com.mycelia.sandbox.runtime;

import com.mycelia.sandbox.exception.NotImplementedException;
import com.mycelia.sandbox.framework.MyceliaMasterNode;
import com.mycelia.sandbox.framework.MyceliaSlaveNode;
import com.mycelia.sandbox.runtime.local.LocalApplicationRuntime;

/**
 * This class is used by the Mycelia Runtime to find, instantiate and setup
 * a specific Runtime for a given RuntimeType.
 * 
 * Each Runtime needs to be provided the user defined (subclassed) MyceliaNode classes
 * and a load balancer strategy. 
 */
public class ApplicationRuntimeFactory
{
	private static ApplicationRuntimeFactory instance;
	
	public static ApplicationRuntimeFactory getInstance()
	{
		if(instance==null)
			instance=new ApplicationRuntimeFactory();
		
		return instance;
	}
	
	private ApplicationRuntimeFactory()
	{
		//Do nothing
	}
	
	/**
	 * Find, instantiate and setup a specific Runtime for a given RuntimeType.
	 * 
	 * @param runtimeType
	 * 			The wanted ApplicationRuntimeType.
	 * 
	 * @param strategy
	 * 			The LoadBalancerStrategy to use in this Mycelia Application.
	 * 
	 * @param masterNodeClass
	 * 			The user defined MyceliaMasterNode sublcass.
	 * 
	 * @param slaveNodeClass
	 * 			The user defined MyceliaSlaveNode subclass.
	 * 
	 * @return
	 * 			An instantiated MyceliaRuntime that was already setup and is
	 * 			ready to be used by the Mycelia Application.
	 */
	public <M extends MyceliaMasterNode, S extends MyceliaSlaveNode>
		ApplicationRuntime getApplicationRuntime(ApplicationRuntimeType runtimeType, LoadBalancerStrategy strategy,
				Class<M> masterNodeClass, Class<S> slaveNodeClass)
	{
		if(strategy==null)
			throw new IllegalArgumentException("strategy cannot be null");
		
		if(runtimeType==ApplicationRuntimeType.LOCAL)
		{
			LocalApplicationRuntime runtime=new LocalApplicationRuntime();
			runtime.setLoadBalancerStrategy(strategy);
			runtime.setNodeClasses(masterNodeClass, slaveNodeClass);
			
			runtime.initialize();
			
			return runtime;
		}
		else
		{
			throw new NotImplementedException();
		}
	}
}
