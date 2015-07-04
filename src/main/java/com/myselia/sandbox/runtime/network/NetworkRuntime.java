package com.myselia.sandbox.runtime.network;

import com.myselia.javacommon.communication.ComponentCommunicator;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.sandbox.constants.MyseliaModuleType;
import com.myselia.sandbox.runtime.MyseliaRuntime;
import com.myselia.sandbox.runtime.templates.MyseliaMasterModule;
import com.myselia.sandbox.runtime.templates.MyseliaModule;
import com.myselia.sandbox.runtime.templates.MyseliaSlaveModule;

public class NetworkRuntime extends MyseliaRuntime {
	
	private Thread myceliaModuleThread;
	private MyseliaModule module;
	private MyseliaModuleType moduleType;
	
	private ComponentCommunicator componentcommunicator;
	private Thread communicatorThread;
	
	private Thread mailServiceThread;
	
	
	public <M extends MyseliaMasterModule, S extends MyseliaSlaveModule> NetworkRuntime(Class<M> masterModule, Class<S> slaveModule){
		super(masterModule, slaveModule);
	}
	
	public void setModuleType(MyseliaModuleType moduleType){
		this.moduleType = moduleType;
	}

	@Override
	public void initialize() {
		if(moduleType == null){
			System.err.println("no master/slave type invoked");
			return;
		} else {
			
			mailServiceThread = new Thread(new MailService(componenttranslation(moduleType)));
			mailServiceThread.start();
			
			componentcommunicator = new ComponentCommunicator(componenttranslation(moduleType));
			communicatorThread = new Thread(componentcommunicator);
			communicatorThread.start();
			
			MailService.register("RUNTIME_DATA", componentcommunicator);
			MailService.register("RUNTIME_TRANSFER", componentcommunicator);
			MailService.register("RUNTIME_RESULTCONTAINER", componentcommunicator);
			MailService.register("DATA_TESTDATA", componentcommunicator);
			MailService.register("DATA_RESULT", componentcommunicator);
			MailService.register("DATA_RESULTCONTAINER", componentcommunicator);
			
			try{
				if(moduleType.equals(MyseliaModuleType.MASTER)){
					module = masterModuleClass.newInstance();
				} else if(moduleType.equals(MyseliaModuleType.SLAVE)){
					module = slaveModuleClass.newInstance();
				} 
				myceliaModuleThread = new Thread(module);
			} catch (Exception e){
				System.err.println("module instantiation error");
			}
		}
	}

	@Override
	public void start() {
		myceliaModuleThread.start();
		module.setup();
		MailService.registerAddressable(module);
	}

	@Override
	public void end() {
		System.out.println("NETWORK RUNTIME END");	
	}
	
	private ComponentType componenttranslation(MyseliaModuleType moduleType){
		if(moduleType.equals(MyseliaModuleType.MASTER)){
			return ComponentType.SANDBOXMASTER;
		} else if(moduleType.equals(MyseliaModuleType.SLAVE)){
			return ComponentType.SANDBOXSLAVE;
		} else {
			System.err.println("no such component available");
			return null;
		}
	}


}
