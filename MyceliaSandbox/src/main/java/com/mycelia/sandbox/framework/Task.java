package com.mycelia.sandbox.framework;

public class Task
{
	private String name;
	
	public Task()
	{
		//Do nothing
	}
	
	public Task(String name)
	{
		this.name=name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
