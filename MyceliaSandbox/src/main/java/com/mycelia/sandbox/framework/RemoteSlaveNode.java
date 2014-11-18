package com.mycelia.sandbox.framework;

import java.util.Set;

public class RemoteSlaveNode
{
	private String id;
	
	public RemoteSlaveNode()
	{
		//
	}
	
	/**
	 * Gets the different tasks this node can perform.
	 */
	public Set<Task> getTasks()
	{
		//TODO
		
		return null;
	}
	
	/**
	 * Start execution of a specific task on this node.
	 * 
	 * @param task
	 * 			The task to execute.
	 * 
	 * @param parameter
	 * 			The task's parameters.
	 * 
	 * @return
	 * 			The task instance ID.
	 */
	public int startTask(Task task, Object... parameter)
	{
		//TODO
		
		return 0;
	}
	
	/**
	 * Gets a task's instance result, once the task is done execution. 
	 * 
	 * @param taskInstanceId
	 * 			Task's instance ID to get the result of.
	 * 
	 * @return
	 * 			The result of this specific task instance.
	 */
	public Object getTaskResult(int taskInstanceId)
	{
		//TODO
		
		return null;
	}
	
	/**
	 * Get the running task instances.
	 */
	public Set<TaskInstance> getRunningTaskInstance()
	{
		//TODO
		
		return null;
	}
	
	/**
	 * Returns true if the specified task instance has finished executing.
	 */
	public boolean isTaskInstanceDone(int taskInstanceId)
	{
		//TODO
		
		return false;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
