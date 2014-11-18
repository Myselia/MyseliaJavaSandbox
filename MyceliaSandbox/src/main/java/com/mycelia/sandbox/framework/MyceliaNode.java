package com.mycelia.sandbox.framework;

import java.io.IOException;
import java.io.Serializable;

import com.mycelia.sandbox.communication.AtomConverter;
import com.mycelia.sandbox.communication.bean.Atom;
import com.mycelia.sandbox.communication.bean.Transmission;
import com.mycelia.sandbox.constants.Constants;
import com.mycelia.sandbox.exception.MyceliaRuntimeException;
import com.mycelia.sandbox.framework.communication.Message;
import com.mycelia.sandbox.runtime.CommunicationDevice;
import com.mycelia.sandbox.runtime.NodeContainer;

public abstract class MyceliaNode
{
	private String nodeId;
	private CommunicationDevice communicationDevice;
	private AtomConverter atomConverter;
	private NodeContainer nodeContainer;
	
	public MyceliaNode()
	{
		atomConverter=new AtomConverter();
	}
	
	/**
	 * Abstract method to propagate NodeContainer only to some children.
	 */
	public void setNodeContainer(NodeContainer nodeContainer)
	{
		this.nodeContainer=nodeContainer;
	}
	
	public final void setCommunicationDevice(CommunicationDevice communicationDevice)
	{
		this.communicationDevice=communicationDevice;
	}
	
	private Atom toAtom(Serializable serializable)
	{
		Atom atom;
		
		if(serializable instanceof Double)
		{
			atom=atomConverter.toAtom((Double)serializable);
		}
		else if(serializable instanceof Integer)
		{
			atom=atomConverter.toAtom((Integer)serializable);
		}
		else
		{
			try
			{
				atom=atomConverter.toAtom(serializable);
			}
			catch(IOException e)
			{
				throw new MyceliaRuntimeException(e);
			}
		}
		
		return atom;
	}
	
	private Serializable fromAtom(Atom atom)
	{
		if(atom.getAtomClass().equals(Integer.class.getCanonicalName()))
		{
			return atomConverter.getAsInt(atom); 
		}
		else if(atom.getAtomClass().equals(Double.class.getCanonicalName()))
		{
			return atomConverter.getAsDouble(atom); 
		}
		else
		{
			try
			{
				return atomConverter.getAsSerializable(atom);
			}
			catch(ClassNotFoundException | IOException e)
			{
				throw new MyceliaRuntimeException(e);
			}
		}
	}
	
	private Transmission toTransmission(Message message)
	{
		Transmission transmission=new Transmission();
		transmission.setFrom(nodeId);
		transmission.setOpcode(Constants.DEFAULT_USER_OPCODE_PREFIX);
		
		for(Serializable serializable: message.getElements())
			transmission.addAtom(toAtom(serializable));
		
		return transmission;
	}
	
	private Message toMessage(Transmission transmission)
	{
		Message message=new Message();
		message.setFrom(transmission.getFrom());
		message.setTo(transmission.getTo());
		
		for(Atom atom: transmission.getAtoms())
			message.addElement(fromAtom(atom));
		
		return message;
	}
	
	protected final void sendMessage(Message message)
	{
		communicationDevice.sendTransmission(toTransmission(message));
	}
	
	protected final Message receiveMessage(int timeout)
	{
		Transmission transmission=communicationDevice.receiveTransmission(timeout);
		
		if(transmission==null)
			return null;
		
		return toMessage(transmission);
	}
	
	protected final void sendMessageToMaster(Message message)
	{
		message.setTo(nodeContainer.getMasterNodeId());
		sendMessage(message);
	}
	
	protected final void sendMessageToAnySlave(Message message)
	{
		message.setTo(Constants.ANY_SLAVE_NODE);
		sendMessage(message);
	}
	
	public final void setNodeId(String nodeId)
	{
		this.nodeId=nodeId;
	}
	
	public final String getNodeId()
	{
		return nodeId;
	}
}
