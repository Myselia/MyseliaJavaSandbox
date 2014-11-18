package com.mycelia.sandbox.framework;

import java.util.Set;

import com.mycelia.sandbox.runtime.NodeContainer;

public abstract class MyceliaMasterNode extends MyceliaNode
{
	private NodeContainer nodeContainer;
	
	//Methods provided by Mycelia framework.
	
	public MyceliaMasterNode()
	{
		//Do nothing.
	}
	
	@Override
	public void setNodeContainer(NodeContainer nodeContainer)
	{
		this.nodeContainer=nodeContainer;
	}
	
	/**
	 * Creates a new slave node.
	 */
	protected final RemoteSlaveNode createSlaveNode()
	{
		return nodeContainer.createSlaveNode();
	}
	
	/**
	 * Creates multiple new slave nodes.
	 * 
	 * @param numberOfNodes
	 * 			The number of slave nodes to create.
	 */
	protected final Set<RemoteSlaveNode> createSlaveNodes(int numberOfNodes)
	{
		return nodeContainer.createSlaveNodes(numberOfNodes);
	}
	
	/**
	 * Start the specified task on any salve node.
	 * 
	 * @param task
	 * 			The task to execute on a slave node.
	 * 
	 * @return
	 * 			The task instance created.
	 */
	protected final TaskInstance startTaskOnAnyNode(Task task)
	{
		return nodeContainer.startTaskOnAnyNode(task);
	}
	
	/**
	 * Gets the <code>RemoteSlaveNode</code> object associated with the specified node ID.  
	 */
	protected final RemoteSlaveNode getRemoteSlaveNode(String nodeId)
	{
		return nodeContainer.getRemoteSlaveNode(nodeId);
	}
	
	/**
	 * Deletes a slave node.
	 * This stop execution of all task instances on the node and releases all node resources.
	 * 
	 * @param nodeId
	 * 			The node ID of the node to delete.
	 */
	protected final void deleteSlaveNode(String nodeId)
	{
		nodeContainer.deleteSlaveNode(nodeId);
	}
	
	/**
	 * Deletes multiple slave nodes.
	 * This stop execution of all task instances on the nodes and releases all the nodes' resources.
	 * 
	 * @param numberOfNodes
	 * 			The number of slave nodes to delete.
	 */
	protected final void deleteSlaveNodes(int numberOfNodes)
	{
		nodeContainer.deleteSlaveNodes(numberOfNodes);
	}
	
	/**
	 * Deletes all slave nodes.
	 * This stop execution of all task instances on all the nodes and releases all the nodes' resources.
	 */
	protected final void deleteAllSlaveNodes()
	{
		nodeContainer.deleteAllSlaveNodes();
	}
	
	//Methods implemented by the end user.
	
	/**
	 * Life cycle <strong>start</strong> event: called when a Mycelia application starts.  
	 */
	public abstract void start();
	
	/**
	 * Life cycle <strong>stop</strong> event: called when a Mycelia application is requested to stop.  
	 */
	public abstract void stop();
}
