package com.myselia.sandbox.runtime.network;

import com.myselia.javacommon.communication.ComponentCommunicator;
import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.constants.opcode.ActionType;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeBroker;
import com.myselia.javacommon.constants.opcode.operations.LensOperation;
import com.myselia.javacommon.constants.opcode.operations.SandboxMasterOperation;
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
	private Thread componentCommunicatorThread;
	
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
			
			//IMPORTANT
			//--------------------------------------------------
			//I CHANGED THE ORDER OF STARTING THE FOLLOWING 2 THREADS. IT USED TO BE MS THEN CC.
			//BUT BECAUSE MS NEEDS MUUID WHICH IS LOCATED IN CC, CC IS CREATED FIRST
			
			componentcommunicator = new ComponentCommunicator(componenttranslation(moduleType));
			mailServiceThread = new Thread(new MailService(componenttranslation(moduleType), ComponentCommunicator.componentCertificate.getUUID()));
			
			mailServiceThread.start();
			componentcommunicator.start();
			
			try{
				if(moduleType.equals(MyseliaModuleType.MASTER)){
					System.out.println("MASTER TYPE");
					module = masterModuleClass.newInstance();
					String master_reg_1 = OpcodeBroker.makeMailCheckingOpcode(ActionType.DATA, LensOperation.TESTDATA);
					MailService.register(master_reg_1, componentcommunicator);
				} else if(moduleType.equals(MyseliaModuleType.SLAVE)){
					module = slaveModuleClass.newInstance();
					String slave_reg_1 = OpcodeBroker.makeMailCheckingOpcode(ActionType.DATA, SandboxMasterOperation.RESULTCONTAINER);
					MailService.register(slave_reg_1, componentcommunicator);
				} 
				myceliaModuleThread = new Thread(module);
			} catch (Exception e){
				System.err.println("module instantiation error");
			}
		}
	}

	@Override
	public void start() {
		System.out.println("Started Network Runtime, preparing to create module.");
		module.setup();
		myceliaModuleThread.start();
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
