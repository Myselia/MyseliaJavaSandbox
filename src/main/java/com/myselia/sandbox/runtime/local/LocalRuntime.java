package com.myselia.sandbox.runtime.local;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.sandbox.runtime.MyseliaRuntime;
import com.myselia.sandbox.runtime.templates.MyseliaMasterModule;
import com.myselia.sandbox.runtime.templates.MyseliaModule;
import com.myselia.sandbox.runtime.templates.MyseliaSlaveModule;

public class LocalRuntime extends MyseliaRuntime {
	
	//Master Module Execution
	private MyseliaModule masterModule;
	private Thread masterModuleThread;
	
	//Slave Modules Execution
	private int slaveCount = 0;
	private MyseliaModule[] slaveModuleArray;
	private Thread[] slaveModuleThreadArray;
	private ExecutorService threadPool;
	
	
	private Thread mailServiceThread;
	
	public <M extends MyseliaMasterModule, S extends MyseliaSlaveModule> LocalRuntime(Class<M> masterModule, Class<S> slaveModule){
		super(masterModule, slaveModule);
		
		threadPool = Executors.newFixedThreadPool(10);
	}
	
	/**
	 * sets the number of virtual slave modules that will be instantiated
	 * @param slaveCount
	 */
	public void setSlaveCount(int slaveCount){
		this.slaveCount = slaveCount;
	}

	@Override
	public void initialize() {
		if(slaveCount == 0){
			System.err.println("illegal slave count for local runtime");
			return;
		} else {
			try{
				
				mailServiceThread = new Thread(new MailService(ComponentType.SANDBOXMASTER));
				mailServiceThread.start();
				
				masterModule = masterModuleClass.newInstance();
				masterModuleThread = new Thread(masterModule); // MASTER MODULE THREAD
				
				slaveModuleThreadArray = new Thread[slaveCount];
				slaveModuleArray = new MyseliaModule[slaveCount];
				for(int i = 0; i < slaveCount; i++){
					slaveModuleArray[i] = slaveModuleClass.newInstance();
					slaveModuleThreadArray[i] = new Thread(slaveModuleArray[i]); // SLAVE MODULE THREAD
				}
			} catch (Exception e){
				e.printStackTrace();
				System.err.println("module instantiation error");
			}
		}
	}
	
	@Override
	public void start() {
		masterModuleThread.start();
		masterModule.setup();
		
		for(int i = 0; i < slaveCount; i++){
			this.threadPool.execute(slaveModuleThreadArray[i]);
			this.slaveModuleArray[i].setup();
		}
	}
	

	@Override
	public void end() {
		System.out.println("LOCAL RUNTIME END");
		
	}


}
