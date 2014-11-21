package com.mycelia.sandbox.runtime;

import com.mycelia.sandbox.framework.MyceliaMasterNode;
import com.mycelia.sandbox.framework.MyceliaSlaveNode;

public interface ApplicationRuntime
{
	public <M extends MyceliaMasterNode, S extends MyceliaSlaveNode>
		void setNodeClasses(Class<M> masterNodeClass, Class<S> slaveNodeClass);
	
	public void setLoadBalancerStrategy(LoadBalancerStrategy strategy);
	
	public void initialize();
	
	public NodeContainer getNodeContainer();
	public LoadBalancer getLoadBalancer();
}
