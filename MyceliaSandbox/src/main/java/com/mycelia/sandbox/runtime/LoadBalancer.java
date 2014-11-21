package com.mycelia.sandbox.runtime;

import java.util.Set;

public interface LoadBalancer
{
	/**
	 * Selects the best slave node to start a task on according to load balancer strategy.
	 * 
	 * @return the slave's node ID.
	 */
	public String selectSlaveNode();
	
	public void addNodes(Set<String> nodeIds);
	public void removeNodes(Set<String> nodeIds);
}
