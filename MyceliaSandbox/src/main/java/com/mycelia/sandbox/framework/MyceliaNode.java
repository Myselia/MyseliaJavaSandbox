package com.mycelia.sandbox.framework;

import java.io.IOException;
import java.io.Serializable;

import com.mycelia.sandbox.communication.AtomConverter;
import com.mycelia.sandbox.communication.bean.Atom;
import com.mycelia.sandbox.communication.bean.Transmission;
import com.mycelia.sandbox.constants.Constants;
import com.mycelia.sandbox.framework.communication.Message;
import com.mycelia.sandbox.runtime.CommunicationDevice;
import com.mycelia.sandbox.runtime.LoadBalancer;
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
	
	/**
	 * Abstract method to propagate LoadBalancer only to some children.
	 */
	public abstract void setLoadBalancer(LoadBalancer loadBalancer);
	
	public final void setCommunicationDevice(CommunicationDevice communicationDevice)
	{
		this.communicationDevice=communicationDevice;
	}
	
	private Transmission toTransmission(Message message) throws IOException
	{
		Transmission transmission=new Transmission();
		transmission.setFrom(nodeId);
		transmission.setOpcode(Constants.DEFAULT_USER_OPCODE_PREFIX);
		
		for(Serializable serializable: message.getElements())
			transmission.addAtom(atomConverter.toAtomInferType(serializable));
		
		return transmission;
	}
	
	private Message toMessage(Transmission transmission) throws ClassNotFoundException, IOException
	{
		Message message=new Message();
		message.setFrom(transmission.getFrom());
		message.setTo(transmission.getTo());
		
		for(Atom atom: transmission.getAtoms())
			message.addElement(atomConverter.fromAtomInferType(atom));
		
		return message;
	}
	
	protected final void sendMessage(Message message) throws IOException
	{ 
		communicationDevice.sendTransmission(toTransmission(message));
	}
	
	protected final Message receiveMessage(int timeout) throws ClassNotFoundException, IOException
	{
		Transmission transmission=communicationDevice.receiveTransmission(timeout);
		
		if(transmission==null)
			return null;
		
		return toMessage(transmission);
	}
	
	protected final void sendMessageToMaster(Message message) throws IOException
	{
		message.setTo(nodeContainer.getMasterNodeId());
		sendMessage(message);
	}
	
	protected final void sendMessageToAnySlave(Message message) throws IOException
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
