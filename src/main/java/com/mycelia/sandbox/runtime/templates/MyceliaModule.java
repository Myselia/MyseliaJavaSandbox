package com.mycelia.sandbox.runtime.templates;

import com.mycelia.common.communication.Addressable;
import com.mycelia.common.communication.ComponentCommunicator;
import com.mycelia.common.communication.structures.MailBox;
import com.mycelia.common.communication.units.Transmission;
import com.mycelia.common.communication.units.TransmissionBuilder;
import com.mycelia.sandbox.constants.MyceliaModuleType;

/**
 * A Mycelia Module capable of processing part of a Mycelia Application
 * and communicate with other Mycelia Modules
 *
 */
public abstract class MyceliaModule implements Runnable, Addressable{
	
	private String nodeId;
	private MyceliaModuleType moduleType;
	protected MailBox<Transmission> mailbox = new MailBox<Transmission>();
	
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
				tick();
				Thread.sleep(1000);
			} catch (Exception e){
				System.err.println("tick error");
			}
		}
	}
	
	@Override
	public MailBox<?> getMailBox(){
		return mailbox;
	}
	
	
	
	public abstract void setup();
	protected abstract void tick();

}
