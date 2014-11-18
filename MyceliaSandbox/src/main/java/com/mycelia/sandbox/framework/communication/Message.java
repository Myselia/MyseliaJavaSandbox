package com.mycelia.sandbox.framework.communication;

import java.io.Serializable;
import java.util.List;

public class Message
{
	private String from;
	private String to;
	private List<Serializable> elements;

	public Message()
	{
		//Do nothing
	}
	
	public void addElement(Serializable serializable)
	{
		elements.add(serializable);
	}
	
	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from=from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to=to;
	}

	public List<Serializable> getElements()
	{
		return elements;
	}

	public void setElements(List<Serializable> elements)
	{
		this.elements = elements;
	}
}
