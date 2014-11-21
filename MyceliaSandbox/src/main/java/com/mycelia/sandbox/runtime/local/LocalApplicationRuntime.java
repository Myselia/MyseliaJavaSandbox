package com.mycelia.sandbox.runtime.local;

import com.mycelia.sandbox.framework.MyceliaMasterNode;
import com.mycelia.sandbox.framework.MyceliaSlaveNode;
import com.mycelia.sandbox.runtime.ApplicationRuntime;
import com.mycelia.sandbox.runtime.LoadBalancer;
import com.mycelia.sandbox.runtime.LoadBalancerFactory;
import com.mycelia.sandbox.runtime.LoadBalancerStrategy;
import com.mycelia.sandbox.runtime.NodeContainer;

public class LocalApplicationRuntime implements ApplicationRuntime
{
	private Class<? extends MyceliaMasterNode> masterClass;
	private Class<? extends MyceliaSlaveNode> slaveClass;
	private LoadBalancerStrategy strategy;
	private NodeContainer nodeContainer;
	private LoadBalancer loadBalancer;
	
	public LocalApplicationRuntime()
	{
		//Do nothing
	}
	
	@Override
	public <M extends MyceliaMasterNode, S extends MyceliaSlaveNode>
		void setNodeClasses(Class<M> masterClass, Class<S> slaveClass)
	{
		this.masterClass=masterClass;
		this.slaveClass=slaveClass;
	}
	
	@Override
	public void setLoadBalancerStrategy(LoadBalancerStrategy strategy)
	{
		loadBalancer=LoadBalancerFactory.getInstance().getLoadBalancer(strategy);
	}
	
	@Override
	public void initialize()
	{
		loadBalancer=LoadBalancerFactory.getInstance().getLoadBalancer(strategy);
		nodeContainer=new LocalNodeContainer();
		
		nodeContainer.setLoadBalancer(loadBalancer);
		nodeContainer.setNodeClasses(masterClass, slaveClass);
	}
	
	@Override
	public NodeContainer getNodeContainer()
	{
		return nodeContainer;
	}

	@Override
	public LoadBalancer getLoadBalancer()
	{
		return loadBalancer;
	}
}
