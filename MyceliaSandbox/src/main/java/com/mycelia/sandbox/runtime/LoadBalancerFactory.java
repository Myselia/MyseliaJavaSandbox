package com.mycelia.sandbox.runtime;

import com.mycelia.sandbox.exception.NotImplementedException;
import com.mycelia.sandbox.runtime.loadbalancer.RoundRobinLoadBalancer;

public class LoadBalancerFactory
{
	private static LoadBalancerFactory instance;
	
	public static LoadBalancerFactory getInstance()
	{
		if(instance==null)
			instance=new LoadBalancerFactory();
		
		return instance;
	}
	
	private LoadBalancerFactory()
	{
		//Do nothing
	}
	
	public LoadBalancer getLoadBalancer(LoadBalancerStrategy strategy)
	{
		if(strategy==null)
			throw new IllegalArgumentException("strategy cannot be null");
		
		if(strategy==LoadBalancerStrategy.ROUND_ROBIN)
			return new RoundRobinLoadBalancer();
		else
			throw new NotImplementedException();
	}
}
