package com.mycelia.sandbox.communication.bean;

import java.util.List;

public class Transmission
{
	private String id;
	private String from;
	private String to;
	private String opcode;
	private List<Atom> atoms;

	public Transmission()
	{
		//Do nothing
	}
	
	public Transmission(String id, String from, String to)
	{
		this.id=id;
		this.from=from;
		this.to=to;
	}

	public Transmission(Transmission transmission)
	{
		this.id=transmission.id;
		this.from=transmission.from;
		this.to=transmission.to;
		this.opcode=transmission.opcode;
		this.atoms=transmission.atoms;
	}

	public void setAttributes(String id, String from, String to)
	{
		this.id=id;
		this.from=from;
		this.to=to;
	}
	
	public void setOpcode(String opcode)
	{
		this.opcode=opcode;
	}

	public void setAtoms(List<Atom> list)
	{
		atoms=list;
	}
	
	public void addAtom(Atom atom)
	{
		atoms.add(atom);
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getOpcode()
	{
		return opcode;
	}

	public List<Atom> getAtoms()
	{
		return atoms;
	}

	@Override
	public String toString()
	{
		return "[id: "+id+", to: "+to+", from: "+from+", opcode: "+opcode+", length: "+atoms.size()+"]";
	}
}
