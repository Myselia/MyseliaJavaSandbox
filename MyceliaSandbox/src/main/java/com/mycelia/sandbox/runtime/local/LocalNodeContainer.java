package com.mycelia.sandbox.runtime.local;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mycelia.sandbox.exception.MyceliaRuntimeException;
import com.mycelia.sandbox.framework.MyceliaMasterNode;
import com.mycelia.sandbox.framework.MyceliaNode;
import com.mycelia.sandbox.framework.MyceliaSlaveNode;
import com.mycelia.sandbox.framework.RemoteSlaveNode;
import com.mycelia.sandbox.framework.Task;
import com.mycelia.sandbox.framework.TaskInstance;
import com.mycelia.sandbox.runtime.NodeContainer;

public class LocalNodeContainer<M extends MyceliaMasterNode, S extends MyceliaSlaveNode> implements NodeContainer
{
	private Class<M> masterNodeClass;
	private Class<S> slaveNodeClass;
	private ThreadGroup nodeThreadGroup;
	private Map<String, ThreadNode> nodeMap;
	private int lastNodeId;
	private String masterNodeId;
	
	public LocalNodeContainer(Class<M> masterNodeClass, Class<S> slaveNodeClass)
	{
		nodeThreadGroup=new ThreadGroup("Mycelia Nodes");
		nodeMap=new HashMap<String, ThreadNode>();
		lastNodeId=0;
	}
	
	private String getNewNodeId()
	{
		lastNodeId++;
		
		return Integer.toString(lastNodeId);
	}
	
	private <N extends MyceliaNode> MyceliaNode createNode(Class<N> nodeClass) throws InstantiationException, IllegalAccessException
	{
		MyceliaNode node=nodeClass.newInstance();
		node.setNodeId(getNewNodeId());
		
		return node;
	}
	
	@Override
	public String createAndStartMasterNode() throws MyceliaRuntimeException
	{
		try
		{
			MyceliaMasterNode masterNode=(MyceliaMasterNode)createNode(masterNodeClass);
			
			MasterThreadNode masterThread=new MasterThreadNode(masterNode, nodeThreadGroup);
			masterNodeId=masterNode.getNodeId();
			nodeMap.put(masterNodeId, masterThread);
			masterThread.start();
			
			return masterNode.getNodeId();
		}
		catch(Exception e)
		{
			throw new MyceliaRuntimeException(e);
		}
	}

	@Override
	public void stopMasterAndDeleteAllNodes()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public RemoteSlaveNode createSlaveNode()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<RemoteSlaveNode> createSlaveNodes(int numberOfNodes)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskInstance startTaskOnAnyNode(Task task)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteSlaveNode getRemoteSlaveNode(String nodeId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSlaveNode(String nodeId)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSlaveNodes(int numberOfNodes)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllSlaveNodes()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMasterNodeId()
	{
		return masterNodeId;
	}
}
