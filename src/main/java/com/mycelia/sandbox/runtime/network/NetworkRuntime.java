package com.mycelia.sandbox.runtime.network;

import com.mycelia.sandbox.constants.MyceliaModuleType;
import com.mycelia.sandbox.runtime.MyceliaMasterModule;
import com.mycelia.sandbox.runtime.MyceliaSlaveModule;
import com.mycelia.sandbox.runtime.templates.MyceliaModule;
import com.mycelia.sandbox.runtime.templates.MyceliaRuntime;

public class NetworkRuntime extends MyceliaRuntime {
	
	private MyceliaModule module;
	private MyceliaModuleType moduleType;
	private CommunicationEndpoint communicationendpoint;
	
	public <M extends MyceliaMasterModule, S extends MyceliaSlaveModule> NetworkRuntime(Class<M> masterModule, Class<S> slaveModule){
		super(masterModule, slaveModule);
	}
	
	public void setModuleType(MyceliaModuleType moduleType){
		this.moduleType = moduleType;
	}

	@Override
	public void initialize() {
		if(moduleType == null){
			System.err.println("no master/slave type invoked");
			return;
		} else {
			communicationendpoint = new CommunicationEndpoint();
			try{
				if(moduleType.equals(MyceliaModuleType.MASTER)){
					module = masterModuleClass.newInstance();
				} else if(moduleType.equals(MyceliaModuleType.SLAVE)){
					module = slaveModuleClass.newInstance();
				} 
			} catch (Exception e){
				System.err.println("module instantiation error");
			}
		}
	}
	

	@Override
	public void end() {
		System.out.println("NETWORK RUNTIME END");	
	}

}
