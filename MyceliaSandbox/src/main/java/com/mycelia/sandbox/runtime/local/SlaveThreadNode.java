package com.mycelia.sandbox.runtime.local;

import java.io.IOException;
import java.util.List;

import com.mycelia.sandbox.communication.AtomConverter;
import com.mycelia.sandbox.communication.bean.Atom;
import com.mycelia.sandbox.communication.bean.Transmission;
import com.mycelia.sandbox.constants.Constants;
import com.mycelia.sandbox.constants.SandboxOpcodes;
import com.mycelia.sandbox.framework.MyceliaSlaveNode;
import com.mycelia.sandbox.framework.Task;

public class SlaveThreadNode extends ThreadNode
{
	private MyceliaSlaveNode node;
	private AtomConverter atomConverter;

	public SlaveThreadNode(ThreadGroup threadGroup, LocalNodeContainer nodeContainer,  MyceliaSlaveNode node)
	{
		super(threadGroup, Constants.SLAVE_NODE_THREAD_NAME+" - "+node.getNodeId(), nodeContainer, node);
		
		this.node=node;
		
		atomConverter=new AtomConverter();
	}

	@Override
	protected void nodeStart()
	{
		node.nodeStart();
	}

	@Override
	protected void nodeStop()
	{
		node.nodeStop();
	}

	@Override
	protected void executeFrameworkAction(Transmission transmission)
	{
		if(transmission.getOpcode().equals(SandboxOpcodes.START_TASK_REQUEST_SLAVE))
			startTaskFrameworkAction(transmission);
	}
	
	private void sendGenericError(String toNodeId, String message)
	{
		sendTransmission(SandboxOpcodes.GENERIC_ERROR, toNodeId, atomConverter.toAtom(message));
	}
	
	private void startTaskFrameworkAction(Transmission transmission)
	{
		List<Atom> atoms=transmission.getAtoms();
		String taskName=atomConverter.getAsString(atoms.get(0));
		Object[] arguments=new Object[atoms.size()-1];
	
		//Deserialize transmission
		try
		{
			for(int i=1; i<atoms.size(); i++)
				arguments[i-1]=atomConverter.fromAtomInferType(atoms.get(i));
		}
		catch(ClassNotFoundException | IOException e)
		{
			sendGenericError(transmission.getFrom(), "Exception: "+e.getMessage());
			return;
		}
		
		//Find task to start from task name.
		Task taskToStart=null;
		
		for(Task task: node.getTasks())
		{
			if(task.getName().equals(taskName))
			{
				taskToStart=task;
				break;
			}
		}
		
		if(taskToStart==null)
		{
			sendGenericError(transmission.getFrom(), "No Task with name: "+taskName);
			return;
		}
		
		//Start task
		int taskInstanceId=node.startTask(taskToStart, arguments);
		
		//Answer back task instance ID
		Transmission answer=new Transmission();
		answer.setOpcode(SandboxOpcodes.START_TASK_ANSWER_SLAVE);
		answer.setTo(transmission.getFrom());
		answer.addAtom(atomConverter.toAtom(taskInstanceId));
		
		sendTransmission(transmission);
	}
}
