package com.mycelia.sandbox.runtime;

import java.util.Set;

import com.mycelia.sandbox.exception.MyceliaRuntimeException;
import com.mycelia.sandbox.framework.RemoteSlaveNode;
import com.mycelia.sandbox.framework.Task;
import com.mycelia.sandbox.framework.TaskInstance;

public interface NodeContainer
{
	/**
	 * Creates and start the master node.
	 * 
	 * @return
	 * 			The master node's ID.
	 */
	public String createAndStartMasterNode() throws MyceliaRuntimeException;
	
	/**
	 * Stop the master node and deletes all slave nodes.
	 */
	public void stopMasterAndDeleteAllNodes();
	
	public String getMasterNodeId();
	
	/**
	 * Creates a new slave node.
	 */
	public RemoteSlaveNode createSlaveNode();
	
	/**
	 * Creates multiple new slave nodes.
	 * 
	 * @param numberOfNodes
	 * 			The number of slave nodes to create.
	 */
	public Set<RemoteSlaveNode> createSlaveNodes(int numberOfNodes);
	
	/**
	 * Start the specified task on any salve node.
	 * 
	 * @param task
	 * 			The task to execute on a slave node.
	 * 
	 * @return
	 * 			The task instance created.
	 */
	public TaskInstance startTaskOnAnyNode(Task task);
	
	/**
	 * Gets the <code>RemoteSlaveNode</code> object associated with the specified node ID.  
	 */
	public RemoteSlaveNode getRemoteSlaveNode(String nodeId);
	
	/**
	 * Deletes a slave node.
	 * This stop execution of all task instances on the node and releases all node resources.
	 * 
	 * @param nodeId
	 * 			The node ID of the node to delete.
	 */
	public void deleteSlaveNode(String nodeId);
	
	/**
	 * Deletes multiple slave nodes.
	 * This stop execution of all task instances on the nodes and releases all the nodes' resources.
	 * 
	 * @param numberOfNodes
	 * 			The number of slave nodes to delete.
	 */
	public void deleteSlaveNodes(int numberOfNodes);
	
	/**
	 * Deletes all slave nodes.
	 * This stop execution of all task instances on all the nodes and releases all the nodes' resources.
	 */
	public void deleteAllSlaveNodes();
}
