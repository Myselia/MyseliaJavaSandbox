package com.myselia.sandbox.runtime.templates;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.myselia.javacommon.communication.ComponentCommunicator;
import com.myselia.javacommon.communication.mail.Addressable;
import com.myselia.javacommon.communication.mail.MailBox;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Atom;
import com.myselia.javacommon.communication.units.Message;
import com.myselia.javacommon.communication.units.Task;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.communication.units.TransmissionBuilder;
import com.myselia.javacommon.constants.opcode.ActionType;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeBroker;
import com.myselia.javacommon.constants.opcode.OpcodeSegment;
import com.myselia.javacommon.constants.opcode.operations.SandboxMasterOperation;
import com.myselia.javacommon.constants.opcode.operations.SandboxSlaveOperation;
import com.myselia.javacommon.topology.ComponentCertificate;
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
	
	protected TransmissionBuilder tb = new TransmissionBuilder();
	
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

	public void sendMessage(String field, String value){
		ComponentCertificate cc = null;
		MyseliaUUID muuid = null;
		try{
			cc = ComponentCommunicator.componentCertificate;
			muuid = cc.getUUID();
		}catch (Exception e){
			return;
		}
		String from_opcode = OpcodeBroker.make(ComponentType.SANDBOXSLAVE, muuid, ActionType.DATA, SandboxSlaveOperation.RESULT);
		String to_opcode = OpcodeBroker.make(ComponentType.SANDBOXMASTER, null, ActionType.DATA, SandboxMasterOperation.RESULTCONTAINER);
		
		tb.newTransmission(from_opcode, to_opcode);
		
		tb.addAtom(field, "Message", value);
		
		Transmission trans_out = tb.getTransmission();
		System.out.println("Tranmission to be sent out : ||" + json.toJson(trans_out) + "||");
		mailbox.enqueueOut(trans_out);
		MailService.notify(this);
	}
}
