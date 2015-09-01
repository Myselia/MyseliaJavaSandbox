package com.myselia.sandbox.runtime;

import com.myselia.javacommon.configuration.MyceliaSettings;
import com.myselia.sandbox.constants.MyseliaModuleType;
import com.myselia.sandbox.constants.MyseliaRuntimeType;

public class MyseliaApplicationSettings implements MyceliaSettings {
	
	private MyseliaModuleType moduleType = MyseliaModuleType.MASTER;
	private MyseliaRuntimeType runtimeType = MyseliaRuntimeType.LOCAL;
	private int slaveCount = 3;
	
	public MyseliaApplicationSettings(){
		
	}

	public MyseliaModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(MyseliaModuleType moduleType) {
		this.moduleType = moduleType;
	}

	public MyseliaRuntimeType getRuntimeType() {
		return runtimeType;
	}

	public void setRuntimeType(MyseliaRuntimeType runtimeType) {
		this.runtimeType = runtimeType;
	}	
	
	public int getSlaveCount(){
		return slaveCount;
	}
	
	public void setSlaveCount(int slaveCount){
		this.slaveCount = slaveCount;
	}

	
}
