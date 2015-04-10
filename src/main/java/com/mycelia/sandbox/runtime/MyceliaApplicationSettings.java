package com.mycelia.sandbox.runtime;

import com.mycelia.common.configuration.MyceliaSettings;
import com.mycelia.sandbox.constants.MyceliaModuleType;
import com.mycelia.sandbox.constants.MyceliaRuntimeType;

public class MyceliaApplicationSettings implements MyceliaSettings {
	
	private MyceliaModuleType moduleType = MyceliaModuleType.MASTER;
	private MyceliaRuntimeType runtimeType = MyceliaRuntimeType.LOCAL;
	
	public MyceliaApplicationSettings(){
		
	}

	public MyceliaModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(MyceliaModuleType moduleType) {
		this.moduleType = moduleType;
	}

	public MyceliaRuntimeType getRuntimeType() {
		return runtimeType;
	}

	public void setRuntimeType(MyceliaRuntimeType runtimeType) {
		this.runtimeType = runtimeType;
	}	
	
}
