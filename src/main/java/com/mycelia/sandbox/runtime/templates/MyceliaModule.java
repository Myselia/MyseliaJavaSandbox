package com.mycelia.sandbox.runtime.templates;

import com.mycelia.common.communication.ComponentCommunicator;
import com.mycelia.common.communication.structures.MailBox;
import com.mycelia.common.communication.tools.TransmissionBuilder;
import com.mycelia.common.communication.units.Message;
import com.mycelia.sandbox.constants.MyceliaModuleType;

/**
 * A Mycelia Module capable of processing part of a Mycelia Application
 * and communicate with other Mycelia Modules
 *
 */
public abstract class MyceliaModule implements Runnable{
	
	private String nodeId;
	private MyceliaModuleType moduleType;
	protected MailBox<Message> messagemailbox = new MailBox<Message>();
	
	protected boolean RUNNING = true;
	
	public MyceliaModule(MyceliaModuleType moduleType){
		this.moduleType = moduleType;
	}
	
	public void end(){
		RUNNING = false;
	}
	
	
	@Override
	public void run(){
		while(RUNNING){
			try{
				forward_message();
				tick();
				Thread.sleep(1000);
			} catch (Exception e){
				System.err.println("tick error");
			}
		}
	}
	
	private void forward_message(){
		TransmissionBuilder bob = new TransmissionBuilder();
		if(messagemailbox.getOutQueueSize() != 0){
			bob.newTransmission(1000, "MASTER", "STEM");
			bob.addMessage(messagemailbox.getNextToSend());
			ComponentCommunicator.send(bob.getTransmission(), false);
		}
	}
	
	public abstract void setup();
	protected abstract void tick();

}
