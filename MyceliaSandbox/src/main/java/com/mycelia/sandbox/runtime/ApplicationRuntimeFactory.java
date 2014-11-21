package com.mycelia.sandbox.runtime;

import com.mycelia.sandbox.exception.NotImplementedException;
import com.mycelia.sandbox.framework.MyceliaMasterNode;
import com.mycelia.sandbox.framework.MyceliaSlaveNode;
import com.mycelia.sandbox.runtime.local.LocalApplicationRuntime;

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
