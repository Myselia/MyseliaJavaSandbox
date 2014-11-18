package com.mycelia.sandbox.runtime.local;

import java.util.LinkedList;
import java.util.Queue;

import com.mycelia.sandbox.communication.bean.Transmission;
import com.mycelia.sandbox.constants.Constants;
import com.mycelia.sandbox.runtime.CommunicationDevice;

/**
 * A node running on a Thread (as opposed to a network node).
 * 
 * This is the equivalent of the Daemon component on a network node but for the thread node. 
 */
public abstract class ThreadNode extends Thread implements CommunicationDevice
{
	/**
	 * How many milliseconds to sleep when waiting for an event to occur.
	 */
	private static final int SLEEP_DURATION=100;
	
	private Queue<Transmission> userTransmissions;
	private Queue<Transmission> frameworkTransmissions;
	private VirtualStem virtualStem;
	
	public ThreadNode(ThreadGroup threadGroup, String threadName)
	{
		super(threadGroup, threadName);
		
		userTransmissions=new LinkedList<Transmission>();
		frameworkTransmissions=new LinkedList<Transmission>();
		virtualStem=VirtualStem.getInstance();
	}
	
	@Override
	public final void run()
	{
		Transmission frameworkTrans;
		
		//This node has been created. Execute the nodeStart lifecycle event.
		nodeStart(); 
		
		while(true)
		{
			frameworkTrans=receiveFrameworkTransmission(0);
			
			if(frameworkTrans!=null)
			{
				if(frameworkTrans.getOpcode().equals(Constants.STOP_SANDBOX_OPCODE))
				{
					/* This node has been request to stop.
					 * Execute nodeStop lifecycle event and then stop execution.
					 */
					nodeStop();
					break;
				}
				else
				{
					//Execute Node type dependent framework action.
					executeFrameworkAction(frameworkTrans);
				}
			}
		}
	}
	
	/**
	 * Stores the Transaction in this node's memory buffer for later
	 * retrieval by the node's thread.
	 */
	public final void acceptTransmission(Transmission transmission)
	{
		if(transmission.getOpcode().startsWith(Constants.SANDBOX_OPCODE_PREFIX))
		{
			synchronized(frameworkTransmissions)
			{
				frameworkTransmissions.add(transmission);
			}
		}
		else
		{
			synchronized(userTransmissions)
			{
				userTransmissions.add(transmission);
			}
		}
	}
	
	/**
	 * Sends a Transmission.
	 * @see CommunicationDevice.sendTransmission(Transmission transmission)
	 */
	@Override
	public final void sendTransmission(Transmission transmission)
	{
		virtualStem.routeTransmission(transmission);
	}
	
	/**
	 * Return a Transmission received by this node.
	 * @see CommunicationDevice.receiveTransmission(int timeout)
	 */
	@Override
	public final Transmission receiveTransmission(int timeout)
	{
		synchronized(userTransmissions)
		{
			if(userTransmissions.size()>0)
			{
				return userTransmissions.remove();
			}
			else if(timeout==0)
			{
				return null;
			}
		}
		
		long startTimeNano=System.nanoTime();
		long elapsedTimeMilli;
		
		do
		{
			try
			{
				sleep(SLEEP_DURATION);
			}
			catch(InterruptedException e)
			{
				//Do nothing.
			}
			
			synchronized(userTransmissions)
			{
				if(userTransmissions.size()>0)
				{
					return userTransmissions.remove();
				}
			}
			
			elapsedTimeMilli=(System.nanoTime()-startTimeNano)/1000;
		}while(timeout==-1||elapsedTimeMilli>=timeout);
		
		return null;
	}
	
	/**
	 * Reads a received framework Transmission.
	 * 
	 * @see receiveTransmission(int timeout) for more details.
	 */
	protected final Transmission receiveFrameworkTransmission(int timeout)
	{
		synchronized(frameworkTransmissions)
		{
			if(frameworkTransmissions.size()>0)
			{
				return frameworkTransmissions.remove();
			}
			else if(timeout==0)
			{
				return null;
			}
		}
		
		long startTimeNano=System.nanoTime();
		long elapsedTimeMilli;
		
		do
		{
			try
			{
				sleep(SLEEP_DURATION);
			}
			catch(InterruptedException e)
			{
				//Do nothing.
			}
			
			synchronized(frameworkTransmissions)
			{
				if(frameworkTransmissions.size()>0)
				{
					return frameworkTransmissions.remove();
				}
			}
			
			elapsedTimeMilli=(System.nanoTime()-startTimeNano)/1000;
		}while(timeout==-1||elapsedTimeMilli>=timeout);
		
		return null;
	}
	
	//Abstract methods
	
	/**
	 * Life cycle <strong>nodeStart</strong> event: called just after this node has been created.  
	 */
	protected abstract void nodeStart();
	
	/**
	 * Life cycle <strong>nodeStop</strong> event: called just before this node is deleted.  
	 */
	protected abstract void nodeStop();
	
	protected abstract void executeFrameworkAction(Transmission transmission);
}
