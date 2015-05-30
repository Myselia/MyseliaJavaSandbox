package com.myselia.sandbox.runtime.templates;

import com.myselia.javacommon.communication.mail.Addressable;
import com.myselia.javacommon.communication.mail.MailBox;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.sandbox.constants.MyseliaModuleType;

/**
 * A Mycelia Module capable of processing part of a Mycelia Application
 * and communicate with other Mycelia Modules
 *
 */
public abstract class MyseliaModule implements Runnable, Addressable{
	
	private String nodeId;
	private MyseliaModuleType moduleType;
	protected MailBox<Transmission> mailbox = new MailBox<Transmission>();
	
	protected boolean RUNNING = true;
	
	public MyseliaModule(MyseliaModuleType moduleType){
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
				Thread.sleep(100);
			} catch (Exception e){
				e.printStackTrace();
				System.err.println("tick error");
			}
		}
	}
	
	@Override
	public MailBox<?> getMailBox(){
		return mailbox;
	}
	
	public void notifyIncomingMail(){
		
	}
	
	public abstract void setup();
	protected abstract void tick();

}
