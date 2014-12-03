package com.mycelia.sandbox.framework;

import java.io.Serializable;

public class TaskInstance implements Serializable
{
	private static final long serialVersionUID=-6183684795324760980L;
	
	private String nodeId;
	private int instanceId;
	private Task task;
	
	public TaskInstance()
	{
		//Do nothing
	}

	public int getInstanceId()
	{
		return instanceId;
	}

	public void setInstanceId(int instanceId)
	{
		this.instanceId=instanceId;
	}

	public Task getTask()
	{
		return task;
	}

	public void setTask(Task task)
	{
		this.task=task;
	}

	public String getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}
	
	@Override
	public String toString()
	{
		return "[nodeId: \""+nodeId+"\", instance ID: "+instanceId+", task: "+task+" ]";
	}
}
