package com.mycelia.sandbox.communication.bean;

public class Atom
{
	private String atomType;
	private String atomClass;
	private String content;

	public Atom(String atomType, String atomClass)
	{
		this.atomType=atomType;
		this.atomClass=atomClass;
	}

	public void addContent(String content)
	{
		this.content=content;
	}

	public String getContent()
	{
		return content;
	}

	public String getAtomType()
	{
		return atomType;
	}

	public String getAtomClass()
	{
		return atomClass;
	}
}
