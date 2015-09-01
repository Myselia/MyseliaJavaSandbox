package com.myselia.sandbox.runtime.templates;

import com.myselia.sandbox.constants.MyseliaModuleType;

public abstract class MyseliaSlaveModule extends MyseliaModule{
	
	protected int slaveID = 0;
	
	public MyseliaSlaveModule(){
		super(MyseliaModuleType.SLAVE);
	}
	
	public void setSlaveID(int slaveID){
		this.slaveID = slaveID;
	}

}
