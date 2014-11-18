package com.mycelia.sandbox.runtime;

import com.mycelia.sandbox.exception.NotImplementedException;
import com.mycelia.sandbox.framework.MyceliaMasterNode;
import com.mycelia.sandbox.framework.MyceliaSlaveNode;
import com.mycelia.sandbox.runtime.local.LocalNodeContainer;

public class NodeContainerFactory
{
	private static NodeContainerFactory instance;
	
	public static NodeContainerFactory getInstance()
	{
		if(instance==null)
		{
			instance=new NodeContainerFactory();
		}
		
		return instance;
	}
	
	private NodeContainerFactory()
	{
		//Do nothing
	}
	
	public <M extends MyceliaMasterNode, S extends MyceliaSlaveNode>
		NodeContainer getNodeContainer(ApplicationRuntimeType runtimeType, Class<M> masterModule, Class<S> slaveModule)
	{
		if(runtimeType==ApplicationRuntimeType.LOCAL)
		{
			return new LocalNodeContainer<M, S>(masterModule, slaveModule);
		}
		else
		{
			throw new NotImplementedException();
		}
	}
}
