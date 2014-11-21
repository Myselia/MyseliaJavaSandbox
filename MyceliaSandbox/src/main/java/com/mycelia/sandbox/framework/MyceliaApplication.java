package com.mycelia.sandbox.framework;

import com.mycelia.sandbox.runtime.ApplicationRuntime;
import com.mycelia.sandbox.runtime.ApplicationRuntimeFactory;
import com.mycelia.sandbox.runtime.ApplicationRuntimeType;
import com.mycelia.sandbox.runtime.LoadBalancerStrategy;

public class MyceliaApplication<M extends MyceliaMasterNode, S extends MyceliaSlaveNode>
{
	private Class<M> masterModule;
	private Class<S> slaveModule;
	private ApplicationRuntimeType runtimeType;
	private LoadBalancerStrategy strategy;
	private ApplicationRuntime applicationRuntime;
	
	public MyceliaApplication(Class<M> masterModule, Class<S> slaveModule)
	{
		this.masterModule=masterModule;
		this.slaveModule=slaveModule;
		
		runtimeType=ApplicationRuntimeType.LOCAL;
		strategy=LoadBalancerStrategy.ROUND_ROBIN;
	}
	
	public void setRuntimeType(ApplicationRuntimeType runtimeType)
	{
		if(runtimeType==null)
			throw new IllegalArgumentException("runtimeType cannot be null");
		
		this.runtimeType=runtimeType;
	}
	
	public void setLoadBalancerStrategy(LoadBalancerStrategy strategy)
	{
		if(strategy==null)
			throw new IllegalArgumentException("strategy cannot be null");
		
		this.strategy=strategy;
	}
	
	/**
	 * Starts the Mycelia application.
	 * This will create and start the master node.
	 */
	public void start()
	{
		if(applicationRuntime==null)
		{
			applicationRuntime=ApplicationRuntimeFactory.getInstance().getApplicationRuntime(
					runtimeType, strategy, masterModule, slaveModule);
		}
		
		applicationRuntime.getNodeContainer().createAndStartMasterNode();
	}
	
	/**
	 * Stops the Mycelia application.
	 * This will delete the master node and all slave nodes;
	 * stopping all nodes and releasing all resources. 
	 */
	public void stop()
	{
		applicationRuntime.getNodeContainer().stopMasterAndDeleteAllNodes(null);
	}
}
