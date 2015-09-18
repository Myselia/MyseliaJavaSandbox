package com.myselia.sandbox.runtime.templates;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.mail.Addressable;
import com.myselia.javacommon.communication.mail.MailBox;
import com.myselia.javacommon.communication.units.Atom;
import com.myselia.javacommon.communication.units.Message;
import com.myselia.javacommon.communication.units.Task;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.constants.opcode.OpcodeBroker;
import com.myselia.javacommon.constants.opcode.OpcodeSegment;
import com.myselia.javacommon.topology.MyseliaUUID;
import com.myselia.sandbox.constants.MyseliaModuleType;

/**
 * A Mycelia Module capable of processing part of a Mycelia Application
 * and communicate with other Mycelia Modules
 *
 */
public abstract class MyseliaModule implements Runnable, Addressable{
	
	protected Gson json = new Gson();
	
	private String nodeId;
	private MyseliaModuleType moduleType;
	
	protected MailBox<Transmission> mailbox = new MailBox<Transmission>();
	protected MailBox<Message> messagebox = new MailBox<Message>();
	protected MailBox<Task> taskbox = new MailBox<Task>();
	
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
	public void in(Transmission trans) {
		mailbox.enqueueIn(trans);
		
		Transmission newtrans = mailbox.dequeueIn();
		ArrayList<Atom> atomlist = newtrans.get_atoms();
		int newmessage = 0;
		int newtask = 0;
		
		String from = newtrans.get_header().get_from();
		OpcodeSegment[] opcodeSegments = OpcodeBroker.segregate(from);
		MyseliaUUID muuid = (MyseliaUUID) opcodeSegments[1];
		
		for(Atom atom : atomlist){
			
			if(atom.get_type().equals("Task")){
				taskbox.enqueueIn(json.fromJson(atom.get_value(), Task.class));
				newtask++;
			}else if(atom.get_type().equals("Message")){
				messagebox.enqueueIn(json.fromJson(atom.get_value(), Message.class));
				newmessage++;
			}else {
				System.err.println("Sandbox Application cannot handle non-task, non-message transmissions");
			}
		}
		
		for(int i = newtask; i > 0; i--){
			handleTask(muuid);
		}
		
		for(int i = newmessage; i > 0; i--){
			handleMessage(muuid);
		}
	}

	@Override
	public Transmission out() {
		return mailbox.dequeueOut();
	}
	
	public abstract void setup();
	protected abstract void tick();
	protected abstract void handleTask(MyseliaUUID muuid);
	protected abstract void handleMessage(MyseliaUUID muuid);

}
