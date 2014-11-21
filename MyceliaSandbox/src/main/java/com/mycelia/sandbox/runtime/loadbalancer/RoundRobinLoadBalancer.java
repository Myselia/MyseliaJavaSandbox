package com.mycelia.sandbox.runtime.loadbalancer;

import java.util.HashSet;
import java.util.Set;

import com.mycelia.sandbox.exception.MyceliaRuntimeException;
import com.mycelia.sandbox.runtime.LoadBalancer;

public class RoundRobinLoadBalancer implements LoadBalancer
{
	private Set<String> usedNodes;
	private Set<String> newNodes;
	
	public RoundRobinLoadBalancer()
	{
		newNodes=new HashSet<String>();
		usedNodes=new HashSet<String>();
	}

	@Override
	public void addNodes(Set<String> nodeIds)
	{
		synchronized(newNodes)
		{
			newNodes.addAll(nodeIds);
		}
	}

	@Override
	public void removeNodes(Set<String> nodeIds)
	{
		synchronized(newNodes)
		{
			synchronized(usedNodes)
			{
				newNodes.removeAll(nodeIds);
				usedNodes.removeAll(nodeIds);
			}
		}
	}
	
	@Override
	public String selectSlaveNode()
	{
		synchronized(newNodes)
		{
			synchronized(usedNodes)
			{
				//Select slave node.
				if(newNodes.size()==0)
				{
					newNodes.addAll(usedNodes);
					usedNodes.clear();
				}
				
				if(newNodes.size()==0)
					throw new MyceliaRuntimeException("There is no slave node.");
				
				String nodeId=newNodes.iterator().next();
				newNodes.remove(nodeId);
				usedNodes.add(nodeId);
				
				return nodeId;
			}
		}
	}
}
