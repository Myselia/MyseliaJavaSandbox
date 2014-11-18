package com.mycelia.sandbox.framework;

import com.mycelia.sandbox.runtime.ApplicationRuntimeType;
import com.mycelia.sandbox.runtime.NodeContainer;
import com.mycelia.sandbox.runtime.NodeContainerFactory;

public class MyceliaApplication<M extends MyceliaMasterNode, S extends MyceliaSlaveNode>
{
	private Class<M> masterModule;
	private Class<S> slaveModule;
	private ApplicationRuntimeType runtimeType;
	private NodeContainer nodeContainer;
	
	public MyceliaApplication(Class<M> masterModule, Class<S> slaveModule)
	{
		this.masterModule=masterModule;
		this.slaveModule=slaveModule;
		
		runtimeType=ApplicationRuntimeType.LOCAL;
	}
	
	public void setRuntimeType(ApplicationRuntimeType runtimeType)
	{
		this.runtimeType=runtimeType;
	}
	
	/**
	 * Starts the Mycelia application.
	 * This will create and start the master node.
	 */
	public void start()
	{
		if(nodeContainer==null)
		{
			nodeContainer=NodeContainerFactory.getInstance().getNodeContainer(runtimeType, masterModule, slaveModule);
		}
		
		nodeContainer.createAndStartMasterNode();
	}
	
	/**
	 * Stops the Mycelia application.
	 * This will delete the master node and all slave nodes;
	 * stopping all nodes and releasing all resources. 
	 */
	public void stop()
	{
		nodeContainer.stopMasterAndDeleteAllNodes();
	}
}
