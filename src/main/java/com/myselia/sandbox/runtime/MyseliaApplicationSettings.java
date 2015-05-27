package com.myselia.sandbox.runtime;

import com.myselia.javacommon.configuration.MyseliaSettings;
import com.myselia.sandbox.constants.MyseliaModuleType;
import com.myselia.sandbox.constants.MyseliaRuntimeType;

public class MyseliaApplicationSettings implements MyseliaSettings {
	
	private MyseliaModuleType moduleType = MyseliaModuleType.MASTER;
	private MyseliaRuntimeType runtimeType = MyseliaRuntimeType.LOCAL;
	
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
	
}
