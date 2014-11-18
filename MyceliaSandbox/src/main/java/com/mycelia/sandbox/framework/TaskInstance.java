package com.mycelia.sandbox.framework;

public class TaskInstance
{
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
}
