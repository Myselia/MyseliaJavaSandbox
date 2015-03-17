package com.mycelia.sandbox.runtime.local;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mycelia.sandbox.runtime.MyceliaMasterModule;
import com.mycelia.sandbox.runtime.MyceliaSlaveModule;
import com.mycelia.sandbox.runtime.templates.MyceliaModule;
import com.mycelia.sandbox.runtime.templates.MyceliaRuntime;

public class LocalRuntime extends MyceliaRuntime {
	
	//Communication tool 
	private TransmissionForwarder transmissionforwarder;
	
	//Master Module Execution
	private MyceliaModule masterModule;
	private Thread masterModuleThread;
	
	//Slave Modules Execution
	private int slaveCount = 0;
	private MyceliaModule[] slaveModuleArray;
	private ExecutorService threadPool;
	
	public <M extends MyceliaMasterModule, S extends MyceliaSlaveModule> LocalRuntime(Class<M> masterModule, Class<S> slaveModule){
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
				masterModule = masterModuleClass.newInstance();
				masterModuleThread = new Thread(masterModule); // MASTER MODULE THREAD
				
				slaveModuleArray = new MyceliaModule[slaveCount];
				for(int i = 0; i < slaveCount; i++){
					slaveModuleArray[i] = slaveModuleClass.newInstance();
					this.threadPool.execute(slaveModuleArray[i]); // SLAVE MODULE THREAD
				}
			} catch (Exception e){
				e.printStackTrace();
				System.err.println("module instantiation error");
			}
		}
	}

	@Override
	public void end() {
		System.out.println("LOCAL RUNTIME END");
		
	}

}
