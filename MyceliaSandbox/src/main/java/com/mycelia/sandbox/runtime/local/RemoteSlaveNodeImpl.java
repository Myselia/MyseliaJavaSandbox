package com.mycelia.sandbox.runtime.local;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mycelia.sandbox.communication.AtomConverter;
import com.mycelia.sandbox.communication.bean.Atom;
import com.mycelia.sandbox.communication.bean.Transmission;
import com.mycelia.sandbox.constants.SandboxOpcodes;
import com.mycelia.sandbox.exception.MyceliaRuntimeException;
import com.mycelia.sandbox.exception.MyceliaTimeoutException;
import com.mycelia.sandbox.framework.RemoteSlaveNode;
import com.mycelia.sandbox.framework.Task;
import com.mycelia.sandbox.framework.TaskInstance;

public class RemoteSlaveNodeImpl implements RemoteSlaveNode
{
	/**
	 * Default timeout when waiting for a remote node's answer (in milliseconds).
	 */
	private static final int DEFAULT_TIMOUT=1000;
	
	private ThreadNode localThreadNode;
	private String remoteNodeId;
	private AtomConverter atomConverter;
	
	public RemoteSlaveNodeImpl(ThreadNode localThreadNode, String remoteNodeId)
	{
		this.localThreadNode=localThreadNode;
		this.remoteNodeId=remoteNodeId;
		
		atomConverter=new AtomConverter();
	}

	@Override
	public Set<Task> getTasks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int startTask(Task task, Serializable... parameters) throws MyceliaRuntimeException
	{
		String opcode=SandboxOpcodes.START_TASK_REQUEST_SLAVE;
		List<Atom> atoms=new ArrayList<Atom>(parameters.length);
		
		try
		{
			atoms.add(atomConverter.toAtom(task.getName()));
			
			for(Serializable parameter: parameters)
			{
				atoms.add(atomConverter.toAtomInferType(parameter));
			}
			
			localThreadNode.sendTransmission(opcode, remoteNodeId, atoms);
			
			Transmission answer=localThreadNode.receiveFrameworkAnswerTransmission(
					SandboxOpcodes.START_TASK_ANSWER_SLAVE, DEFAULT_TIMOUT);
			
			if(answer==null)
				throw new MyceliaTimeoutException("Timeout while waiting for slave node to answer.");
			
			return atomConverter.getAsInt(answer.getAtoms().get(0));
		}
		catch(IOException e)
		{
			throw new MyceliaRuntimeException(e);
		}
	}

	@Override
	public Object getTaskResult(int taskInstanceId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<TaskInstance> getRunningTaskInstance()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTaskInstanceDone(int taskInstanceId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNodeId()
	{
		return remoteNodeId;
	}

	@Override
	public TaskInstance getTaskInstance(int taskInstanceId)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
