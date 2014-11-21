package com.mycelia.sandbox.runtime.local;

import com.mycelia.sandbox.communication.bean.Transmission;
import com.mycelia.sandbox.constants.Constants;
import com.mycelia.sandbox.framework.MyceliaMasterNode;

public class MasterThreadNode extends ThreadNode
{
	private MyceliaMasterNode node;
	
	public MasterThreadNode(ThreadGroup threadGroup, LocalNodeContainer nodeContainer, MyceliaMasterNode node)
	{
		super(threadGroup, Constants.MASTER_NODE_THREAD_NAME+" - "+node.getNodeId(), nodeContainer, node);
		
		this.node=node;
	}
	
	public MyceliaMasterNode getMasterNode()
	{
		return node;
	}

	@Override
	protected void nodeStart()
	{
		node.start();
	}

	@Override
	protected void nodeStop()
	{
		node.stop();
	}

	@Override
	protected void executeFrameworkAction(Transmission transmission)
	{
		//This should not get executed since there is no master node framework action yet.
		
		throw new IllegalStateException("This should not get executed since there is no master node framework action yet.");
	}
}
